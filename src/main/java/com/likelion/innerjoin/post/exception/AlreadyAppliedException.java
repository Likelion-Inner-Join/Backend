package com.likelion.innerjoin.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlreadyAppliedException extends RuntimeException {
    private String message;
}