package com.folaroid.portfolio.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IntroLanguageDetailDto {
    private Long introNo;
    private String languageName;
    private String languageTestName;
    private String languageGrade;
    private String languageDate;
}
