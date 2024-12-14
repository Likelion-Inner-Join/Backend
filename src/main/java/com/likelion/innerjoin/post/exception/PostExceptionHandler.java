package com.likelion.innerjoin.post.exception;

import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostExceptionHandler {

    // PostNotFoundException 처리
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<CommonResponse<Object>> handlePostNotFoundException(PostNotFoundException ex) {
        CommonResponse<Object> response = new CommonResponse<>(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // ClubNotFoundException 처리
    @ExceptionHandler(ClubNotFoundException.class)
    public ResponseEntity<CommonResponse<Object>> handleClubNotFoundException(ClubNotFoundException ex) {
        CommonResponse<Object> response = new CommonResponse<>(ErrorCode.CLUB_NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // FormNotFoundException 처리
    @ExceptionHandler(FormNotFoundException.class)
    public ResponseEntity<CommonResponse<Object>> handleFormNotFoundException(FormNotFoundException ex) {
        CommonResponse<Object> response = new CommonResponse<>(ErrorCode.FORM_NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // ImageProcessingException 처리
    @ExceptionHandler(ImageProcessingException.class)
    public ResponseEntity<CommonResponse<Object>> handleImageProcessingException(ImageProcessingException ex) {
        CommonResponse<Object> response = new CommonResponse<>(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Object>> handleGeneralException(Exception ex) {
        CommonResponse<Object> response = new CommonResponse<>(ErrorCode.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
