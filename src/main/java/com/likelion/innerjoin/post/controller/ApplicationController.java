package com.likelion.innerjoin.post.controller;


import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.post.model.dto.request.*;
import com.likelion.innerjoin.post.model.dto.response.ApplicationDto;
import com.likelion.innerjoin.post.model.dto.response.MeetingTimeResponseDTO;
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
@RequestMapping("/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    @Operation(summary = "지원하기 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적인 응답"),
            @ApiResponse(responseCode = "400", description = "이미 지원했습니다."),
            @ApiResponse(responseCode = "401", description = "세션값이 잘못되었습니다."),
            @ApiResponse(responseCode = "404", description = "모집중 직무가 존재하지 않습니다")
    })
    public CommonResponse<Long> postApplication(@RequestBody ApplicationRequestDto applicationRequestDto, HttpSession session) {
        Application application = applicationService.postApplication(applicationRequestDto, session);
        return new CommonResponse<>(ErrorCode.CREATED, application.getId());
    }

    @GetMapping("/{application_id}")
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

    @GetMapping("/list")
    @Operation(summary = "로그인된 지원자의 지원서 목록 조회 (지원자용)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적인 응답"),
            @ApiResponse(responseCode = "401", description = "권한이 없습니다.")
    })
    public CommonResponse<List<ApplicationDto>> getApplicationList(HttpSession session) {
        return new CommonResponse<>(applicationService.getApplicationList(session));
    }

    @PutMapping("/{application_id}")
    @Operation(summary = "지원서 정보 수정용 api (동아리용)")
    public CommonResponse<ApplicationDto> updateApplication(
            @RequestBody ApplicationPutRequestDto applicationPutRequestDto,
            @PathVariable("application_id") Long applicationId,
            HttpSession session) {
        return new CommonResponse<>(
                applicationService.updateApplication(applicationPutRequestDto, applicationId, session));
    }

    @PostMapping("/formscore")
    @Operation(summary = "지원서 점수 수정용 api (동아리용)")
    public CommonResponse<ApplicationDto> updateFormScore(
            @RequestBody FormScoreDto formScoreDto,
            HttpSession session) {
        return new CommonResponse<>(applicationService.updateFormScore(formScoreDto, session));
    }

    @PostMapping("/meetingscore")
    @Operation(summary = "면접 점수 수정용 api (동아리용)")
    public CommonResponse<ApplicationDto> updateMeetingScore(
            @RequestBody MeetingScoreDto meetingScoreDto,
            HttpSession session) {
        return new CommonResponse<>(applicationService.updateMeetingScore(meetingScoreDto, session));
    }

    @PostMapping("/email")
    @Operation(summary = "이메일 전송 api (동아리용)")
    public CommonResponse<ApplicationDto> sendEmail(@RequestBody EmailDto emailDto, HttpSession session) {
        return new CommonResponse<>(applicationService.sendEmail(emailDto, session));
    }

    @PostMapping("/interview-time")
    @Operation(summary = "면접 시간 선택 api(지원자용)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적인 응답"),
            @ApiResponse(responseCode = "401", description = "세션값이 잘못되었습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "면접 시간을 찾을 수 없습니다.."),
            @ApiResponse(responseCode = "404", description = "지원 내역이 존재하지 않습니다."),
            @ApiResponse(responseCode = "400", description = "면접 허용 인원을 초과했습니다."),
    })
    public CommonResponse<MeetingTimeResponseDTO> selectMeetingTime(
            @RequestBody MeetingTimeSelectionDto meetingTimeDto,
            HttpSession session) {
        return new CommonResponse<>(applicationService.selectMeetingTime(meetingTimeDto, session));
    }

}
