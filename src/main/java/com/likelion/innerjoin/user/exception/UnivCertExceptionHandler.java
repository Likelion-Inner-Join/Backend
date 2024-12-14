package com.likelion.innerjoin.user.exception;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnivCertExceptionHandler {

    @ExceptionHandler(UnivCertException.class)
    public ResponseEntity<CommonResponse<Object>> handleUnivCertException(UnivCertException ex) {
        CommonResponse<Object> response = new CommonResponse<>(ErrorCode.UNIV_CERT_API_ERROR);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnivNameException.class)
    public ResponseEntity<CommonResponse<Object>> handleUnivNameException(UnivNameException ex) {
        CommonResponse<Object> response = new CommonResponse<>(ErrorCode.INVALID_UNIV_NAME);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnivEmailDomainException.class)
    public ResponseEntity<CommonResponse<Object>> handleUnivEmailDomainException(UnivEmailDomainException ex) {
        CommonResponse<Object> response = new CommonResponse<>(ErrorCode.MISMATCHED_EMAIL_DOMAIN);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
