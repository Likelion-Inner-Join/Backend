package com.likelion.innerjoin.user.controller;

import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.request.UnivCertRequestDto;
import com.likelion.innerjoin.user.service.UnivCertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class UnivCertController {
    private final UnivCertService univCertService;

    @PostMapping("/send")
    public ResponseEntity<CommonResponse<Object>> certifyEmail(@RequestBody UnivCertRequestDto requestDto) {
        univCertService.certifyEmail(requestDto);
        return ResponseEntity.ok(new CommonResponse<>(ErrorCode.SUCCESS, null));
    }
}
