package com.likelion.innerjoin.user.controller;


import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.request.LoginRequestDto;
import com.likelion.innerjoin.user.model.dto.response.LoginResponseDto;
import com.likelion.innerjoin.user.service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public CommonResponse<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        return new CommonResponse<>(loginService.login(loginRequestDto, session));
    }
}
