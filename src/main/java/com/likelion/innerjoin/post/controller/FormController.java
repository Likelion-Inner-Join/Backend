package com.likelion.innerjoin.post.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.post.model.dto.request.FormRequestDto;
import com.likelion.innerjoin.post.model.dto.response.FormResponseDto;
import com.likelion.innerjoin.post.service.FormService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/form")
@RequiredArgsConstructor
@Slf4j
public class FormController {
    private final FormService formService;

    @PostMapping("/")
    public CommonResponse<FormResponseDto> createPost(@RequestBody FormRequestDto formRequestDto, HttpSession session) {
        return new CommonResponse<>(formService.createForm(formRequestDto, session));
    }
}
