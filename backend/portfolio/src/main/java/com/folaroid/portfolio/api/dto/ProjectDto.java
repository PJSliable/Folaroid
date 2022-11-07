package com.folaroid.portfolio.api.dto;

import com.folaroid.portfolio.db.entity.Portfolio;
import com.folaroid.portfolio.db.entity.Project;
import lombok.*;

public class ProjectDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class projectRequest{
        private Long pjtNo;
        private Long pfNo;
//        private Long pfNo;
        private String pjtTitle;
        private String pjtSubtitle;
        private String pjtUrl;
        private String pjtGithubUrl;
        private Integer pjtStar;
        private String pjtImageLocation;
        private String pjtJson;

        public Project toEntity(Portfolio portfolio){
            Project project = Project.builder()
                    .pjtNo(pjtNo)
                    .portfolio(portfolio)
                    .pjtTitle(pjtTitle)
                    .pjtSubtitle(pjtSubtitle)
                    .pjtUrl(pjtUrl)
                    .pjtGithubUrl(pjtGithubUrl)
                    .pjtStar(pjtStar)
                    .pjtImageLocation(pjtImageLocation)
                    .pjtJson(pjtJson)
                    .build();
            return project;
        }
        @Builder
        public projectRequest(String pjtTitle, String pjtSubtitle, String pjtUrl, String pjtGithubUrl, Integer pjtStar, String pjtImageLocation, String pjtJson){
            this.pjtTitle = pjtTitle;
            this.pjtSubtitle = pjtSubtitle;
            this.pjtUrl = pjtUrl;
            this.pjtGithubUrl = pjtGithubUrl;
            this.pjtStar = pjtStar;
            this.pjtImageLocation = pjtImageLocation;
            this.pjtJson = pjtJson;
        }

    }
    @Getter
    public static class projectResponse{
        private Long pjtNo;
        private Long pfNo;
        private String pjtTitle;
        private String pjtSubtitle;
        private String pjtUrl;
        private String pjtGithubUrl;
        private Integer pjtStar;
        private String pjtImageLocation;
        private String pjtJson;


        public projectResponse(Project project){
            this.pjtNo = project.getPjtNo();
            this.pfNo = project.getPortfolio().getPfNo();
            this.pjtTitle = project.getPjtTitle();
            this.pjtSubtitle = project.getPjtSubtitle();
            this.pjtUrl = project.getPjtUrl();
            this.pjtGithubUrl = project.getPjtGithubUrl();
            this.pjtStar = project.getPjtStar();
            this.pjtImageLocation = project.getPjtImageLocation();
            this.pjtJson = project.getPjtJson();
        }
    }
}