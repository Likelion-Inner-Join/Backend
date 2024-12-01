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
public class JsonExceptionHandler {
    @ExceptionHandler(JsonConvertException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<?> jsonConvertError(JsonConvertException e, HttpServletRequest request) {
        log.warn("JSON-001> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.JSON_CONVERT_ERROR);
    }
}
