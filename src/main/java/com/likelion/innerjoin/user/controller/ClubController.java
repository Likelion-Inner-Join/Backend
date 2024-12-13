package com.likelion.innerjoin.user.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.response.ClubCategoryResponseDto;
import com.likelion.innerjoin.user.service.ClubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @Operation(summary = "카테고리 조회 API", description = "동아리 카테고리를 조회.")
    @GetMapping("/category")
    public ResponseEntity<CommonResponse<List<ClubCategoryResponseDto>>> getClubCategories() {
        // 서비스 계층 호출
        List<ClubCategoryResponseDto> categories = clubService.getClubCategories();
        // 성공 응답 반환
        return ResponseEntity.ok(new CommonResponse<>(categories));
    }
}
