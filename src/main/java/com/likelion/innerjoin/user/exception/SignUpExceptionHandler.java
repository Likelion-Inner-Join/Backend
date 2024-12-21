package com.likelion.innerjoin.user.exception;

import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SignUpExceptionHandler {

    @ExceptionHandler(SignUpIDException.class)
    public ResponseEntity<CommonResponse<Object>> handleSignUpIDException(SignUpIDException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CommonResponse<>(ErrorCode.DUPLICATE_LOGIN_ID, null)); // ErrorCode의 메시지를 설정
    }

    @ExceptionHandler(EmailValidationException.class)
    public ResponseEntity<CommonResponse<Object>> handleSignUpEmailException(SignUpEmailException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CommonResponse<>(ErrorCode.EMAIL_FORMAT_INVALID, null)); // ErrorCode의 메시지를 설정
    }

}
