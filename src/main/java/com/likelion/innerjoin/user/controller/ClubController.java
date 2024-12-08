package com.likelion.innerjoin.user.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.user.dto.EmailRequestDto;
import com.likelion.innerjoin.user.dto.EmailResponseDto;
import com.likelion.innerjoin.user.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/club/email")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    /**
     * 이메일 중복 확인 API
     *
     * @param emailRequestDto 이메일 요청 DTO
     * @return CommonResponse 형식의 결과
     */
    @PostMapping
    public CommonResponse<EmailResponseDto> checkEmailExists(@RequestBody EmailRequestDto emailRequestDto) {
        try {
            // 이메일 존재 여부 확인
            boolean exists = clubService.isEmailExists(emailRequestDto.getEmail());

            // 응답 DTO 생성
            EmailResponseDto responseDto = new EmailResponseDto(exists);

            // 성공 응답 반환
            return new CommonResponse<>(responseDto);
        } catch (Exception e) {
            // 예외 발생 시 오류 응답 반환
            return new CommonResponse<>(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
