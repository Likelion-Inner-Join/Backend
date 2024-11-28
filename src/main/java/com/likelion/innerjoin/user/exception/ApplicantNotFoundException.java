package com.likelion.innerjoin.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicantNotFoundException extends RuntimeException {
    private String message;
}
