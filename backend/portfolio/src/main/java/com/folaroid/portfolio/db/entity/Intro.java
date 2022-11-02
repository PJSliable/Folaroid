package com.folaroid.portfolio.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Intro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long introNo;

    @Column(length = 1000)
    private String introContent;

    private Long userNo;

    private Long pfNo;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "intro_image_no")
    private IntroImage introImage;

    private Long introPersonalDataNo;

//    @OneToMany(mappedBy = "intro")
//    private List<IntroStack> introStacks = new ArrayList<>();
//
//    @OneToMany(mappedBy = "intro")
//    private List<IntroLanguage> introLanguages = new ArrayList<>();

    public void SaveDefaultUserInfo(long userNo) {
        this.userNo = userNo;
    }

    @Builder
    public Intro(String introContent, Long userNo, Long pfNo, IntroImage introImage, Long introPersonalDataNo) {
        this.introContent = introContent;
        this.userNo = userNo;
        this.pfNo = pfNo;
        this.introImage = introImage;
        this.introPersonalDataNo = introPersonalDataNo;
    }

}
