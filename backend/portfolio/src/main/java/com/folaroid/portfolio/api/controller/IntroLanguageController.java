package com.folaroid.portfolio.api.controller;

import com.folaroid.portfolio.api.dto.IntroLanguageDto;
import com.folaroid.portfolio.api.service.IntroLanguageService;
import com.folaroid.portfolio.db.entity.IntroLanguage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "IntroLanguageAPI", tags={"IntroLanguage"})
@RequestMapping("/intro-language")
@RequiredArgsConstructor
@RestController
public class IntroLanguageController {


    private final IntroLanguageService introLanguageService;

    @ApiOperation(value = "마이페이지 - 공인 어학성적",
            notes="등록",
            httpMethod = "POST")
    @PostMapping
    public ResponseEntity<Long> save(@RequestBody IntroLanguageDto.introLanguageRequest introLanguageRequest){
        Long introLanguageNo = introLanguageService.save(introLanguageRequest);
        return new ResponseEntity<>(introLanguageNo, HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지 - 공인 어학성적",
            notes="조회",
            httpMethod = "GET")
    @GetMapping("/{introNo}") // /{intro_no}
    public ResponseEntity<List<IntroLanguage>> find(@PathVariable("introNo") Long introNo){  // @PathVariable("intro_no") Long introNo
        List<IntroLanguage> introLanguage = introLanguageService.find(introNo);
        return new ResponseEntity<>(introLanguage, HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지 - 공인 어학성적",
            notes="삭제",
            httpMethod = "DELETE")
    @DeleteMapping("/{intro_language_no}")
    public ResponseEntity<Long> delete(@PathVariable("intro_language_no") Long introLanguageNo){
        introLanguageService.delete(introLanguageNo);
        return new ResponseEntity<>(introLanguageNo, HttpStatus.OK);
    }
}
