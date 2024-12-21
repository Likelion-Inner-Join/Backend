package com.likelion.innerjoin.user.controller;

import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.request.UnivCertCodeRequestDto;
import com.likelion.innerjoin.user.model.dto.request.UnivCertRequestDto;
import com.likelion.innerjoin.user.model.dto.response.UnivCertCodeResponseDto;
import com.likelion.innerjoin.user.service.UnivCertService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class UnivCertController {
    private final UnivCertService univCertService;

    @Operation(summary = "인증 코드 발송 API", description = "이메일로 인증 코드를 발송합니다.")
    @PostMapping("/send")
    public ResponseEntity<CommonResponse<Object>> certifyEmail(@RequestBody UnivCertRequestDto requestDto) {
        univCertService.certifyEmail(requestDto);
        return ResponseEntity.ok(new CommonResponse<>(ErrorCode.SUCCESS, null));
    }

    @Operation(summary = "인증 코드 검증 API", description = "이메일로 발송된 인증 코드를 검증합니다.")
    @PostMapping("/request")
    public ResponseEntity<CommonResponse<UnivCertCodeResponseDto>> verifyCode(@RequestBody UnivCertCodeRequestDto requestDto) {
        UnivCertCodeResponseDto responseDto = univCertService.verifyCode(requestDto);
        return ResponseEntity.ok(new CommonResponse<>(responseDto));
    }
}
