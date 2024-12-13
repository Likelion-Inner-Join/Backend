package com.likelion.innerjoin.user.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.request.EmailRequestDto;
import com.likelion.innerjoin.user.model.dto.response.EmailResponseDto;
import com.likelion.innerjoin.user.service.ClubService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;
    @Operation(summary = "동아리 이메일 중복 확인 API", description = "동아리 이메일의 중복 여부를 확인.")
    @PostMapping("/email")
    public ResponseEntity<CommonResponse<EmailResponseDto>> checkEmailExists(@RequestBody EmailRequestDto requestDto) {
        // 서비스 계층 호출
        EmailResponseDto responseDto = clubService.checkEmailExists(requestDto);
        return ResponseEntity.ok(new CommonResponse<>(responseDto));
    }
}
