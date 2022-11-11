package com.folaroid.portfolio.api.service;

import com.folaroid.portfolio.api.dto.IntroDto;
import com.folaroid.portfolio.db.entity.Intro;
import com.folaroid.portfolio.db.entity.IntroPersonalData;
import com.folaroid.portfolio.db.entity.User;
import com.folaroid.portfolio.db.repository.IntroPersonalDataRepository;
import com.folaroid.portfolio.db.repository.IntroRepository;
import com.folaroid.portfolio.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.folaroid.portfolio.api.dto.UserDto.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final IntroRepository introRepository;
    private final UserRepository userRepository;
    private final IntroPersonalDataRepository introPersonalDataRepository;

    /** 마이페이지 - 필수 정보 */
    @Transactional
    public Long MakeIntroAndIntroPersonalDataTable(UserNoReq request) {
        Intro intro = new Intro();
        intro.SaveDefaultUserInfo(request.getUserNo());
        Long introNo = introRepository.save(intro).getIntroNo();
        IntroPersonalData introPersonalData = new IntroPersonalData(introNo);
        introPersonalDataRepository.save(introPersonalData);
        return introNo;
    }


    @Transactional(readOnly = true)
    public UserDefaultDto find(Long introNo) {
        Intro intro = introRepository.findById(introNo).get();
        IntroPersonalData introPersonalData = introPersonalDataRepository.findByIntroNo(introNo);
        return new UserDefaultDto(userRepository.findById(intro.getUserNo()).get(), introPersonalData);
    }

    @Transactional
    public void put(UserDefaultForUpdateDto request) {
        IntroPersonalData introPersonalData = introPersonalDataRepository.findById(request.getIntroNo()).get();
        introPersonalData.updateIntroPersonalData(request.getUserName(), request.getUserBirth(), request.getUserPhone());
        User user = userRepository.findById(introRepository.findById(request.getIntroNo()).get().getUserNo()).get();
        user.saveEmail(request.getUserEmail());
    }
    @Transactional
    public Long save(UserSignupReq request) {
        User user = new User();
        user.save(request.getUserGithubId(), request.getUserEmail());
        return userRepository.save(user).getUserNo();
    }

    public Long getIntroNo(String userGithubId) {
        User user = userRepository.findByUserGithubId(userGithubId);
        return introRepository.findUserDefaultData(user.getUserNo());
    }
}
