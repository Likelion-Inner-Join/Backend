package com.likelion.innerjoin.user.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.response.ClubCategoryResponseDto;
import com.likelion.innerjoin.user.model.dto.request.EmailRequestDto;
import com.likelion.innerjoin.user.model.dto.response.ClubResponseDto;
import com.likelion.innerjoin.user.model.dto.response.EmailResponseDto;
import com.likelion.innerjoin.user.service.ClubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //동아리 정보 조회 API
    @Operation(summary = "동아리 정보 조회 API", description = "동아리 정보를 조회.")
    @GetMapping("/{clubId}")
    public CommonResponse<ClubResponseDto> getClubInfo(@PathVariable Long clubId, HttpSession session) {
        ClubResponseDto clubResponse = clubService.getClubInfo(clubId, session);
        return new CommonResponse<>(clubResponse);
    }

    @Operation(summary = "동아리 회원가입 API", description = "동아리 회원가입")
    @PostMapping("/club/signup")
    public ResponseEntity<CommonResponse<String>> signupClub(
            @RequestPart("data") ClubSignupRequestDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        clubService.signupClub(requestDto, image);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(null, "회원가입이 완료되었습니다."));
    }
}
