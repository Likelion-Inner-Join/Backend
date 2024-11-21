package com.likelion.innerjoin.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.likelion.innerjoin.common.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * API 공통 응답을 위한 class
 * @param <T> result에 들어갈 dto class
 */
@Getter
@Setter
public class CommonResponse<T> {

    private Boolean isSuccess;
    private int code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    //성공시
    public CommonResponse(T result) {
        this.isSuccess = ErrorCode.SUCCESS.getIsSuccess();
        this.code = ErrorCode.SUCCESS.getCode();
        this.message = ErrorCode.SUCCESS.getMessage();
        this.result = result;
    }

    // 오류 발생 (result 없음)
    public CommonResponse(ErrorCode errorCode) {
        this.isSuccess = ErrorCode.SUCCESS.getIsSuccess();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    // 오류 발생
    public CommonResponse(ErrorCode errorCode, T result) {
        this.isSuccess = ErrorCode.SUCCESS.getIsSuccess();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.result = result;
    }
}
