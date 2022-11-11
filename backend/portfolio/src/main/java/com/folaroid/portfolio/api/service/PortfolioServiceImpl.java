package com.folaroid.portfolio.api.service;

import com.folaroid.portfolio.api.dto.PortfolioDto;
import com.folaroid.portfolio.db.entity.*;
import com.folaroid.portfolio.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service("portfolioService")
public class PortfolioServiceImpl implements PortfolioService{


    private final PortfolioRepository portfolioRepository;
    private final IntroRepository introRepository;
    private final IntroPersonalDataRepository introPersonalDataRepository;
    private final IntroStackRepository introStackRepository;
    private final IntroSloganRepository introSloganRepository;
    private final IntroLanguageRepository introLanguageRepository;
    private final IntroActivityRepository introActivityRepository;
    private final IntroArchivingRepository introArchivingRepository;
    private final IntroAwardsRepository introAwardsRepository;
    private final IntroCareerRepository introCareerRepository;
    private final IntroCertificationRepository introCertificationRepository;
    private final IntroImageRepository introImageRepository;
    private final IntroSchoolRepository introSchoolRepository;

    @Transactional
    @Override
    public PortfolioDto.SavePortfolioDto createPortfolio(PortfolioDto.portfolioRequest request) {
        Portfolio portfolio = portfolioRepository.save(request.toEntity());
        Intro intro = new Intro();
        intro.SavePortfolioInfo(portfolio.getPfNo(), request.getUserNo());
        // 새로 만든 introNo
        Long portfolioIntroNo = introRepository.save(intro).getIntroNo();
        //기존 개인정보 데이터 introNo
        Long userInfoIntroNo = introRepository.findUserDefaultData(request.getUserNo());
        // 기존의 개인정보 데이터들을 포트폴리오의 자기소개 정보로 저장할 것.

        //포트폴리오 자기소개 이미지 테이블 저장 1:1
        IntroImage userInfoImage = introImageRepository.findByIntroNo(userInfoIntroNo);
        IntroImage portfolioInfoImage = new IntroImage(portfolioIntroNo);
        portfolioInfoImage.IntroImageLocationSave(userInfoImage.getIntroImageLocation());
        introImageRepository.save(portfolioInfoImage);

        //포트폴리오 자기소개 개인정보 테이블 저장 1:1
        IntroPersonalData userInfoPersonalData = introPersonalDataRepository.findByIntroNo(userInfoIntroNo);
        IntroPersonalData portfolioInfoPersonalData = new IntroPersonalData(portfolioIntroNo);
        portfolioInfoPersonalData.updateIntroPersonalData(userInfoPersonalData.getPersonalDataName(), userInfoPersonalData.getPersonalDataBirth(), userInfoPersonalData.getPersonalDataPhone());
        introPersonalDataRepository.save(portfolioInfoPersonalData);

        //포트폴리오 자기소개 기술스택 테이블 저장 1:N
        List<IntroStack> userInfoStack = introStackRepository.findAllByIntroNo(userInfoIntroNo);
        userInfoStack.forEach(userInfo -> {
            IntroStack portfolioIntroStack = new IntroStack(portfolioIntroNo);
            portfolioIntroStack.saveOtherData(userInfo.getHashNo());
            introStackRepository.save(portfolioIntroStack);
        });
        //포트폴리오 자기소개 어학성적 테이블 저장 1:N
        List<IntroLanguage> userInfoLanguage = introLanguageRepository.findAllByIntroNo(userInfoIntroNo);
        userInfoLanguage.forEach(userInfo -> {
            IntroLanguage portfolioIntroLanguage = new IntroLanguage(portfolioIntroNo);
            portfolioIntroLanguage.saveOtherData(userInfo.getLanguageName(), userInfo.getLanguageTestName(), userInfo.getLanguageGrade(), userInfo.getLanguageDate());
            introLanguageRepository.save(portfolioIntroLanguage);
        });
        //포트폴리오 자기소개 링크 테이블 저장 1:N
        List<IntroArchiving> userInfoArchiving = introArchivingRepository.findAllByIntro(intro);
        userInfoArchiving.forEach(userInfo -> {
            IntroArchiving portfolioIntroArchiving = new IntroArchiving(intro);
            portfolioIntroArchiving.saveOtherData(userInfo.getArchivingName(), userInfo.getArchivingLink());
            introArchivingRepository.save(portfolioIntroArchiving);
        });
        //포트폴리오 자기소개 수상내역 테이블 저장 1:N
        List<IntroAwards> userInfoAwards = introAwardsRepository.findAllByIntro(intro);
        userInfoAwards.forEach(userInfo -> {
            IntroAwards portfolioIntroAwards = new IntroAwards(intro);
            portfolioIntroAwards.saveOtherData(userInfo.getAwardsName(),userInfo.getAwardsDate(),userInfo.getAwardsIssuer(),userInfo.getAwardsDetail());
            introAwardsRepository.save(portfolioIntroAwards);
        });
        //포트폴리오 자기소개 활동 테이블 저장 1:N
        List<IntroActivity> userInfoActivity = introActivityRepository.findAllByIntro(intro);
        userInfoActivity.forEach(userInfo -> {
            IntroActivity portfolioIntroActivity = new IntroActivity(intro);
            portfolioIntroActivity.saveOtherData(userInfo.getActivityName(),userInfo.getActivityDate(),userInfo.getActivityUrl(),userInfo.getActivityDetail());
            introActivityRepository.save(portfolioIntroActivity);
        });
        //포트폴리오 자기소개 경력 테이블 저장 1:N
        List<IntroCareer> userInfoCareer = introCareerRepository.findAllByIntro(intro);
        userInfoCareer.forEach(userInfo -> {
            IntroCareer portfolioIntroCareer = new IntroCareer(intro);
            portfolioIntroCareer.saveOtherData(userInfo.getCareerComName(),userInfo.getCareerJob(),userInfo.getCareerDate(),userInfo.getCareerResult(),userInfo.getCareerDetail());
            introCareerRepository.save(portfolioIntroCareer);
        });
        //포트폴리오 자기소개 학력 테이블 저장 1:N
        List<IntroSchool> userInfoSchool = introSchoolRepository.findAllByIntroNo(userInfoIntroNo);
        userInfoSchool.forEach(userInfo -> {
            IntroSchool portfolioIntroSchool = new IntroSchool(userInfoIntroNo);
            portfolioIntroSchool.saveOtherData(userInfo.getSchoolName(),userInfo.getSchoolMajor(),userInfo.getSchoolDegree(),userInfo.getSchoolAdmissionDate(),userInfo.getSchoolGraduationDate(),userInfo.getSchoolCredit(),userInfo.getSchoolMaxCredit());
            introSchoolRepository.save(portfolioIntroSchool);
        });
        //포트폴리오 자기소개 슬로건 테이블 저장 1:1
        IntroSlogan userInfoSlogan = introSloganRepository.findByIntroNo(userInfoIntroNo);
        IntroSlogan portfolioInfoSlogan = new IntroSlogan(portfolioIntroNo);
        portfolioInfoSlogan.updateIntroSlogan(userInfoSlogan.getSloganContent());
        introSloganRepository.save(portfolioInfoSlogan);

        return new PortfolioDto.SavePortfolioDto(portfolio, portfolioIntroNo);
    }
    @Transactional
    @Override
    public Long getPortfolioIntroNo(Long pfNo) {
        Long userNo = portfolioRepository.findById(pfNo).get().getUserNo();
        return introRepository.findByPfNoAndUserNo(pfNo, userNo);
    }


    @Transactional
    @Override
    public void deletePortfolio(Long pfNo) {
        Portfolio portfolio = portfolioRepository.findById(pfNo).orElseThrow(()->
                new IllegalArgumentException("해당 포트폴리오가 존재하지 않습니다."));
        // 포트폴리오에 존재하는 프로젝트 먼저 삭제하는 코드 작성
        // ??
        portfolioRepository.delete(portfolio);
    }

    @Transactional
    @Override
    public void patchPortfolioTemplate(Long pfNo, PortfolioDto.portfolioRequest portfolioRequest) {
        Portfolio portfolio = portfolioRepository.findById(pfNo).orElseThrow(()->
                new IllegalArgumentException("해당하는 포트폴리오 프로젝트가 존재하지 않습니다."));
        portfolio.updatePortfolioTemplate(portfolioRequest.getPortfolioTemplatesNo());
    }

    @Override
    @Transactional
    public List<PortfolioDto.PortfolioSimpleDto> readSimplePortfolio(Long userNo) {
        List<Portfolio> portfolios = portfolioRepository.findAllByUserNo(userNo);
        List<PortfolioDto.PortfolioSimpleDto> result = portfolios.stream()
                .map(i -> new PortfolioDto.PortfolioSimpleDto(i))
                .collect(Collectors.toList());
        return result;
    }
}