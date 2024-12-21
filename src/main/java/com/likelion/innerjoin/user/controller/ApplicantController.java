package com.likelion.innerjoin.user.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.request.ApplicantSignUpRequestDto;
import com.likelion.innerjoin.user.model.dto.response.ApplicantResponseDto;
import com.likelion.innerjoin.user.model.dto.response.ClubResponseDto;
import com.likelion.innerjoin.user.service.ApplicantService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    /**
     * 지원자 회원가입
     *
     * @param requestDto 지원자 회원가입 요청 DTO
     * @return CommonResponse
     */
    @Operation(summary = "지원자 회원가입 API", description = "지원자 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<String>> signUpApplicant(@RequestBody ApplicantSignUpRequestDto requestDto) {
        applicantService.signUpApplicant(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>("회원가입이 완료되었습니다."));
    }

    // 지원자 정보 조회
    @Operation(summary = "동아리 정보 조회 API", description = "동아리 정보를 조회.")
    @GetMapping("/{applicantId}")
    public CommonResponse<ApplicantResponseDto> getApplicantInfo(@PathVariable Long applicantId, HttpSession session) {
        ApplicantResponseDto applicantResponse = applicantService.getApplicantInfo(applicantId, session);
        return new CommonResponse<>(applicantResponse);
    }
}
