package com.folaroid.portfolio.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pjt_no")
    private Long pjtNo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pf_no")
    private Portfolio portfolio;
    @Column(name = "pjt_title", length = 100)
    private String pjtTitle;

    @Column(name = "pjt_subtitle", length = 500)
    private String pjtSubtitle;

    @Column(name = "pjt_url", length = 2083)
    private String pjtUrl;

    @Column(name = "pjt_github_url", length = 2083)
    private String pjtGithubUrl;

    @Column(name = "pjt_star")
    private Integer pjtStar;

    @Column(name = "pjt_one_image_location", length = 2083)
    private String pjtOneImageLocation; // 프로젝트 대표이미지

    @Column(name = "pjt_json", columnDefinition = "longtext")
    private String pjtJson;

    private String pjtId;

    public void updateProjectTitle(String pjtTitle, String pjtSubtitle){
        this.pjtTitle = pjtTitle;
        this.pjtSubtitle = pjtSubtitle;
    }

    public void updateProject(String pjtTitle, String pjtSubtitle, String pjtUrl, String pjtGithubUrl, Integer pjtStar, String pjtOneImageLocation, String pjtJson, String pjtId){
        this.pjtTitle = pjtTitle;
        this.pjtSubtitle = pjtSubtitle;
        this.pjtUrl = pjtUrl;
        this.pjtGithubUrl = pjtGithubUrl;
        this.pjtStar = pjtStar;
        this.pjtOneImageLocation = pjtOneImageLocation;
        this.pjtJson = pjtJson;
        this.pjtId = pjtId;
    }

    public void updateImage(String url) {
        this.pjtOneImageLocation = url;
    }

    public Project(Project project) {
        this.pjtTitle = project.pjtTitle;
        this.pjtSubtitle = project.pjtSubtitle;
        this.pjtUrl = project.pjtUrl;
        this.pjtGithubUrl = project.pjtGithubUrl;
        this.pjtStar = project.pjtStar;
        this.pjtOneImageLocation = project.pjtOneImageLocation;
        this.pjtJson = project.pjtJson;
        this.pjtId = project.pjtId;
    }

    public void updatePortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
