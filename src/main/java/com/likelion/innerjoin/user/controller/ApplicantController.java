package com.likelion.innerjoin.user.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.request.ApplicantSignUpRequestDto;
import com.likelion.innerjoin.user.service.ApplicantService;
import io.swagger.v3.oas.annotations.Operation;
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
}
