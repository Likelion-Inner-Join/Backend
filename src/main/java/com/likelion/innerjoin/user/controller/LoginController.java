package com.likelion.innerjoin.user.controller;


import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.request.ApplicantLoginRequestDto;
import com.likelion.innerjoin.user.model.dto.request.ClubLoginRequestDto;
import com.likelion.innerjoin.user.model.dto.response.ApplicantLoginResponseDto;
import com.likelion.innerjoin.user.model.dto.response.ClubLoginResponseDto;
import com.likelion.innerjoin.user.service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/user/login")
    public CommonResponse<ApplicantLoginResponseDto> applicantLogin(@RequestBody ApplicantLoginRequestDto applicantLoginRequestDto, HttpSession session) {
        return new CommonResponse<>(loginService.applicantLogin(applicantLoginRequestDto, session));
    }

    @PostMapping("/club/login")
    public CommonResponse<ClubLoginResponseDto> clubLogin(@RequestBody ClubLoginRequestDto clubLoginRequestDto, HttpSession session) {
        return new CommonResponse<>(loginService.clubLogin(clubLoginRequestDto, session));
    }

    @GetMapping("/logout")
    public CommonResponse<String> logout(HttpSession session) {
        session.invalidate();
        return new CommonResponse<>("로그아웃되었습니다.");
    }
}
