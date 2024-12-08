package com.likelion.innerjoin.post.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.post.model.dto.request.FormRequestDto;
import com.likelion.innerjoin.post.model.dto.response.FormListResponseDto;
import com.likelion.innerjoin.post.model.dto.response.FormResponseDto;
import com.likelion.innerjoin.post.service.FormService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/form")
@RequiredArgsConstructor
@Slf4j
public class FormController {
    private final FormService formService;

    @PostMapping("")
    @Operation(summary = "지원폼 생성 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적인 응답"),
            @ApiResponse(responseCode = "401", description = "세션값이 잘못되었습니다.")
    })
    public CommonResponse<FormResponseDto> createForm(@RequestBody FormRequestDto formRequestDto, HttpSession session) {
        return new CommonResponse<>(formService.createForm(formRequestDto, session));
    }

    @GetMapping("")
    @Operation(summary = "지원폼 목록 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적인 응답"),
            @ApiResponse(responseCode = "401", description = "세션값이 잘못되었습니다.")
    })
    public CommonResponse<List<FormListResponseDto>> getFormList(HttpSession session) {
        return new CommonResponse<>(formService.getFormList(session));
    }

    @GetMapping("/{formId}")
    @Operation(summary = "지원폼 상세 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적인 응답"),
            @ApiResponse(responseCode = "401", description = "세션값이 잘못되었습니다."),
            @ApiResponse(responseCode = "404", description = "해당 id의 지원폼이 존재하지 않습니다.")
    })
    public CommonResponse<FormResponseDto> getForm(@PathVariable Long formId, HttpSession session) {
        return new CommonResponse<>(formService.getForm(session, formId));
    }

    @PutMapping("/{formId}")
    @Operation(summary = "지원폼 수정 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적인 응답"),
            @ApiResponse(responseCode = "401", description = "세션값이 잘못되었습니다."),
            @ApiResponse(responseCode = "404", description = "해당 id의 지원폼이 존재하지 않습니다.")
    })
    public CommonResponse<FormResponseDto> updateForm(@PathVariable Long formId, @RequestBody FormRequestDto formRequestDto, HttpSession session) {
        return new CommonResponse<>(formService.updateForm(session, formRequestDto, formId));
    }
}
