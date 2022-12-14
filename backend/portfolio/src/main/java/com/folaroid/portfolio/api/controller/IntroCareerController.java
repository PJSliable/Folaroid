package com.folaroid.portfolio.api.controller;

import com.folaroid.portfolio.api.dto.IntroCareerDto;
import com.folaroid.portfolio.api.service.IntroCareerService;
import com.folaroid.portfolio.db.entity.IntroCareer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "자기소개 경력내역", tags={"IntroCareer"})
@RequestMapping("/intro-career")
@RequiredArgsConstructor
@RestController
public class IntroCareerController {
    private final IntroCareerService introCareerService;

    /**
     * 마이페이지 - 경력내역 등록
     */
    @PostMapping
    @ApiOperation(value = "마이페이지 - 경력 등록", notes = "마이페이지 - 경력내역을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Long> saveIntroCareer(@RequestBody IntroCareerDto.introCareerRequest introCareerRequest){
        Long introCareerNo = introCareerService.saveIntroCareer(introCareerRequest);
        return new ResponseEntity<>(introCareerNo, HttpStatus.OK);
    }

    /**
     * 마이페이지 - 경력내역 삭제
     */
    @DeleteMapping("{introCareerNo}")
    @ApiOperation(value = "마이페이지 - 경력내역 삭제", notes = "마이페이지 - 경력내역을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> deleteIntroCareer(@PathVariable Long introCareerNo){
        introCareerService.deleteIntroCareer(introCareerNo);
        return ResponseEntity.status(200).body(introCareerNo);
    }

    /**
     * 마이페이지 - 경력내역 조회
     */
    @GetMapping("{introNo}")
    @ApiOperation(value = "마이페이지 - 경력내역 조회", notes = "마이페이지 - 경력내역을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<IntroCareer>> findIntroCareer(@PathVariable("introNo") Long introNo){
        List<IntroCareer> introCareers = introCareerService.findIntroCareer(introNo);
        return new ResponseEntity<>(introCareers, HttpStatus.OK);
    }
}

