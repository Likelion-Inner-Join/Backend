package com.likelion.innerjoin.user.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.request.UnivCertRequestDto;
import com.likelion.innerjoin.user.service.UnivCertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UnivCertController {

    private final UnivCertService univCertService;

    @PostMapping("/certify")
    public ResponseEntity<CommonResponse<Object>> certifyEmail(@RequestBody UnivCertRequestDto requestDto) {
        return ResponseEntity.ok(univCertService.certifyEmail(requestDto));
    }
}
