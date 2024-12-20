package com.likelion.innerjoin.user.exception;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.common.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SignUpExceptionHandler {

    /**
     * SignUpIDException 처리
     *
     * @param ex SignUpIDException
     * @return ResponseEntity<CommonResponse<Object>>
     */
    @ExceptionHandler(SignUpIDException.class)
    public ResponseEntity<CommonResponse<Object>> handleSignUpIDException(SignUpIDException ex) {
        CommonResponse<Object> response = new CommonResponse<>(ErrorCode.DUPLICATE_LOGIN_ID, ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
