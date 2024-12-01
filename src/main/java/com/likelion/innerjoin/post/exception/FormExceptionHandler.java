package com.likelion.innerjoin.post.exception;

import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.common.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class FormExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResponse<?> wrongSession(UnauthorizedException e, HttpServletRequest request) {
        log.warn("FORM-001> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.WRONG_SESSION_ERROR);
    }
}
