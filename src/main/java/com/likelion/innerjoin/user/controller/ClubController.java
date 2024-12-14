package com.likelion.innerjoin.user.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.response.ClubCategoryResponseDto;
import com.likelion.innerjoin.user.model.dto.request.EmailRequestDto;
import com.likelion.innerjoin.user.model.dto.response.EmailResponseDto;
import com.likelion.innerjoin.user.service.ClubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    // 이메일 중복 확인 API
    @Operation(summary = "동아리 이메일 중복 확인 API", description = "동아리 이메일의 중복 여부를 확인.")
    @PostMapping("/email")
    public ResponseEntity<CommonResponse<EmailResponseDto>> checkEmailExists(@RequestBody EmailRequestDto requestDto) {
        EmailResponseDto responseDto = clubService.checkEmailExists(requestDto);
        return ResponseEntity.ok(new CommonResponse<>(responseDto));
    }

    // 카테고리 조회 API
    @Operation(summary = "카테고리 조회 API", description = "동아리 카테고리를 조회.")
    @GetMapping("/category")
    public ResponseEntity<CommonResponse<List<ClubCategoryResponseDto>>> getClubCategories() {
        List<ClubCategoryResponseDto> categories = clubService.getClubCategories();
        return ResponseEntity.ok(new CommonResponse<>(categories));
    }
}
