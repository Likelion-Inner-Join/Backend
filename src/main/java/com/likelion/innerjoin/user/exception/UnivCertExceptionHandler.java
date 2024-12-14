package com.likelion.innerjoin.user.exception;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnivCertExceptionHandler {

    @ExceptionHandler(EmailValidationException.class)
    public ResponseEntity<CommonResponse<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new CommonResponse<>(ErrorCode.EMAIL_FORMAT_INVALID, ex.getMessage()));
    }

    @ExceptionHandler(UnivNameException.class)
    public ResponseEntity<CommonResponse<Object>> handleUnivNameException(UnivNameException ex) {
        return ResponseEntity.badRequest().body(new CommonResponse<>(ErrorCode.INVALID_UNIV_NAME, ex.getMessage()));
    }

    @ExceptionHandler(UnivCertException.class)
    public ResponseEntity<CommonResponse<Object>> handleUnivCertException(UnivCertException ex) {
        return ResponseEntity.status(500).body(new CommonResponse<>(ErrorCode.UNIV_CERT_API_ERROR, ex.getMessage()));
    }
}
