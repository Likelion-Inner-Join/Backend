package com.likelion.innerjoin.user.exception;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmailExceptionHandler {

    // EmailValidationException 처리
    @ExceptionHandler(EmailValidationException.class)
    public ResponseEntity<CommonResponse<Object>> handleEmailValidationException(EmailValidationException ex) {
        CommonResponse<Object> response = new CommonResponse<>(ErrorCode.VALID_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
