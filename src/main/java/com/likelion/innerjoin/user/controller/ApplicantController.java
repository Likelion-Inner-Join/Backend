package com.likelion.innerjoin.user.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.request.ApplicantSignUpRequestDto;
import com.likelion.innerjoin.user.model.dto.request.ApplicantUpdateRequestDto;
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
    @Operation(summary = "지원자 정보 조회 API", description = "지원자 정보를 조회.")
    @GetMapping()
    public CommonResponse<ApplicantResponseDto> getApplicantInfo(HttpSession session) {
        ApplicantResponseDto applicantResponse = applicantService.getApplicantInfo(session);
        return new CommonResponse<>(applicantResponse);
    }

    /**
     * 지원자 정보 수정
     *
     * @param updateRequestDto 지원자 수정 요청 DTO
     * @param session 사용자 세션
     * @return CommonResponse
     */
    @Operation(summary = "지원자 정보 수정 API", description = "지원자 정보를 수정.")
    @PutMapping()
    public CommonResponse<String> updateApplicantInfo(
                                                      @RequestBody ApplicantUpdateRequestDto updateRequestDto,
                                                      HttpSession session) {
        applicantService.updateApplicantInfo(updateRequestDto, session);
        return new CommonResponse<>("지원자 정보가 수정되었습니다.");
    }
}
