package com.likelion.innerjoin.post.exception;

import com.likelion.innerjoin.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.POST_NOT_FOUND;
    }

    public PostNotFoundException(String message) {
        super(message);
        this.errorCode = ErrorCode.POST_NOT_FOUND;
    }
}
