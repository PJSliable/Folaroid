package com.folaroid.portfolio.api.controller;

import com.folaroid.portfolio.api.dto.ProjectDto;
import com.folaroid.portfolio.api.service.FileService;
import com.folaroid.portfolio.api.service.ProjectService;
import com.folaroid.portfolio.db.entity.Project;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(value = "프로젝트", tags={"Project"})
@RequestMapping("/project")
@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final FileService fileService;

    /**
     * 프로젝트 등록
     */
    @PostMapping
    @ApiOperation(value = "프로젝트 등록", notes = "프로젝트를 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Long> saveProject(@RequestBody ProjectDto.projectRequest projectRequest){
        Long projectNo = projectService.saveProject(projectRequest);
        return new ResponseEntity<>(projectNo, HttpStatus.OK);
    }

    /**
     * 프로젝트 삭제
     */
    @DeleteMapping("{pjtNo}")
    @ApiOperation(value = "프로젝트 삭제", notes = "프로젝트를 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> deleteProject(@PathVariable Long pjtNo){
        projectService.deleteProject(pjtNo);
        return ResponseEntity.status(200).body(pjtNo);
    }

    /**
     * 프로젝트 전체 조회
     */
    @GetMapping("{pfNo}")
    @ApiOperation(value = "프로젝트 전체 조회", notes = "프로젝트를 전체 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<Project>> findAllProject(@PathVariable("pfNo")Long pfNo){
        List<Project> projects = projectService.findALlProject(pfNo);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    /**
     * 프로젝트 상세 조회
     */
    @GetMapping("detail/{pjtNo}")
    @ApiOperation(value = "프로젝트 상세 조회", notes = "프로젝트를 상세 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<ProjectDto.projectResponse> findProject(@PathVariable Long pjtNo){
        return ResponseEntity.status(200).body(projectService.findProject(pjtNo));
    }

    /**
     * 프로젝트 수정
     */
    @PatchMapping("detail/{pjtNo}")
    @ApiOperation(value = "프로젝트 상세 수정", notes = "프로젝트를 상세 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> patchProject(@PathVariable Long pjtNo, @RequestBody ProjectDto.projectRequest projectRequest){
        projectService.patchProject(pjtNo, projectRequest);
        return ResponseEntity.status(200).body(pjtNo);
    }

    @PostMapping("image/{pjt_no}")
    @ApiOperation(value = "프로젝트 대표 이미지", notes = "등록")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<ProjectDto.ProjectOneImageDto> saveImage(@PathVariable("pjt_no") Long pjtNo, @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            String pjtOneImageLocation = fileService.uploadProjectOneImage(pjtNo, multipartFile);
            return new ResponseEntity<>(new ProjectDto.ProjectOneImageDto(pjtNo, pjtOneImageLocation), HttpStatus.OK);
        }
    }

    @GetMapping("image/{pjt_no}")
    @ApiOperation(value = "프로젝트 대표 이미지", notes = "조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<ProjectDto.ProjectOneImageDto> findImage(@PathVariable("pjt_no") Long pjtNo){
        return new ResponseEntity<>(projectService.findProjectOneImageLocation(pjtNo), HttpStatus.OK);
    }

    @DeleteMapping("image/{pjt_no}")
    @ApiOperation(value = "프로젝트 대표 이미지", notes = "삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Long> deleteImage(@PathVariable("pjt_no") Long pjtNo){
        fileService.deleteProjectOneImageLocation(pjtNo);
        return new ResponseEntity<>(pjtNo, HttpStatus.OK);
    }
}
