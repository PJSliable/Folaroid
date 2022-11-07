package com.folaroid.portfolio.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Intro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long introNo;

    @Column(length = 1000)
    private String introContent;

    private Long userNo;
    private Long pfNo;

//    private Long portfolioTemplatesNo;

//    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "intro_image_no")
//    private IntroImage introImage;
//
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "intro_personal_data_no")
    private IntroPersonalData introPersonalData;

//    @OneToMany(mappedBy = "intro")
//    private List<IntroStack> introStacks = new ArrayList<>();
//
//    @OneToMany(mappedBy = "intro")
//    private List<IntroLanguage> introLanguages = new ArrayList<>();

    public void SaveDefaultUserInfo(long userNo) {
        this.userNo = userNo;
    }

    public void SavePortfolioInfo(Long pfNo, Long userNo) {
        this.pfNo = pfNo;
        this.userNo = userNo;
    }
}
