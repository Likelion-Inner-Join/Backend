package com.likelion.innerjoin.post.controller;


import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.post.model.dto.request.ApplicationRequestDto;
import com.likelion.innerjoin.post.service.ApplicationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Any;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/application")
    public CommonResponse<?> postApplication(@RequestBody ApplicationRequestDto applicationRequestDto, HttpSession session) {
        return new CommonResponse<>(applicationService.postApplication(applicationRequestDto, session));
    }
}
