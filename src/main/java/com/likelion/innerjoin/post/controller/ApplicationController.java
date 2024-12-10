package com.likelion.innerjoin.post.controller;


import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.post.model.dto.request.ApplicationPutRequestDto;
import com.likelion.innerjoin.post.model.dto.request.ApplicationRequestDto;
import com.likelion.innerjoin.post.model.dto.response.ApplicationDto;
import com.likelion.innerjoin.post.model.entity.Application;
import com.likelion.innerjoin.post.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/application")
    @Operation(summary = "지원하기 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적인 응답"),
            @ApiResponse(responseCode = "401", description = "세션값이 잘못되었습니다."),
            @ApiResponse(responseCode = "404", description = "모집중 직무가 존재하지 않습니다")
    })
    public CommonResponse<Long> postApplication(@RequestBody ApplicationRequestDto applicationRequestDto, HttpSession session) {
        Application application = applicationService.postApplication(applicationRequestDto, session);
        return new CommonResponse<>(ErrorCode.CREATED, application.getId());
    }

    @GetMapping("/application/{application_id}")
    @Operation(summary = "지원 상세조회 api (통합)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적인 응답"),
            @ApiResponse(responseCode = "401", description = "세션값이 잘못되었습니다."),
            @ApiResponse(responseCode = "404", description = "질문이 존재하지 않습니다."),
            @ApiResponse(responseCode = "404", description = "지원 내역이 존재하지 않습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없습니다.")
    })
    public CommonResponse<ApplicationDto> getApplicationDetail(
            @PathVariable("application_id") Long applicationId,
            HttpSession session) {
        return new CommonResponse<>(applicationService.getApplicationDetail(applicationId, session));
    }

    @GetMapping("/application/list")
    @Operation(summary = "로그인된 지원자의 지원서 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적인 응답"),
            @ApiResponse(responseCode = "401", description = "권한이 없습니다.")
    })
    public CommonResponse<List<ApplicationDto>> getApplicationList(HttpSession session) {
        return new CommonResponse<>(applicationService.getApplicationList(session));
    }

    @PutMapping("/application/{application_id}")
    @Operation(summary = "지원서 정보 수정용 api (동아리용)")
    public CommonResponse<ApplicationDto> updateApplication(
            @RequestBody ApplicationPutRequestDto applicationPutRequestDto,
            @PathVariable("application_id") Long applicationId,
            HttpSession session) {
        return new CommonResponse<>(
                applicationService.updateApplication(applicationPutRequestDto, applicationId, session));
    }


}
