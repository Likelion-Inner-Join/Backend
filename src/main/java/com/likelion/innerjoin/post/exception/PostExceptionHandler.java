package com.likelion.innerjoin.post.exception;

import com.likelion.innerjoin.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostExceptionHandler {

    /**
     * Handles PostNotFoundException and returns a standardized error response.
     */
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<CommonResponse<Object>> handlePostNotFoundException(PostNotFoundException ex) {
        CommonResponse<Object> response = new CommonResponse<>(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
