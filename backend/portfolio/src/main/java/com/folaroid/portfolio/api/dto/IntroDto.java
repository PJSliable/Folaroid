package com.folaroid.portfolio.api.dto;

import com.folaroid.portfolio.db.entity.Intro;
import com.folaroid.portfolio.db.entity.IntroImage;
import com.folaroid.portfolio.db.entity.IntroPersonalData;
import lombok.*;

public class IntroDto {
    @Data
    @NoArgsConstructor
    public static class Request{
        private Long introNo;
        private String introContent;
        private Long userNo;
        private Long pfNo;
        private Long portfolioTemplatesNo;
        private IntroImage introImage;
        private Long introPersonalDataNo;

        public Intro toEntity(){
            Intro intro = Intro.builder()
                    .introContent(introContent)
                    .userNo(userNo)
                    .pfNo(pfNo)
                    .introImage(introImage)
                    .introPersonalDataNo(introPersonalDataNo)
                    .build();
            return intro;
        }
    }

    @Data
    @NoArgsConstructor
    public static class Response{
        private Long introNo;
        private String introContent;
        private Long userNo;
        private Long pfNo;
        private Long portfolioTemplatesNo;
        private IntroImage introImage;
        private Long introPersonalDataNo;

        public Response(Intro intro){
            this.introNo = intro.getIntroNo();
            this.introContent = intro.getIntroContent();
            this.userNo = intro.getUserNo();
            this.pfNo = intro.getPfNo();
            this.introImage = intro.getIntroImage();
            this.introPersonalDataNo = intro.getIntroPersonalDataNo();
        }
    }
}
