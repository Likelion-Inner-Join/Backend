package com.likelion.innerjoin.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * 모든 error code 통합 enum class
 * <p>
 * 생기는 모든 특수한 error code에 대하여 새로운 enum을 생성하여 사용합니다.
 * 예시)
 * INVALID_VERIFICATION_TOKEN_ERROR(false, HttpStatus.BAD_REQUEST.value(), "인증 정보가 잘못되었거나 인증 시간이 초과되었습니다."),
 */
@Getter
public enum ErrorCode {
    //success
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공했습니다."),

    //valid
    VALID_ERROR(false, HttpStatus.BAD_REQUEST.value(), "형식이 잘못되었습니다."),

    //error
    INTERNAL_SERVER_ERROR(false,HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부에서 문제가 발생했습니다.")
    ;

    private final Boolean isSuccess;
    private final int code;
    private final String message;

    ErrorCode(Boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
