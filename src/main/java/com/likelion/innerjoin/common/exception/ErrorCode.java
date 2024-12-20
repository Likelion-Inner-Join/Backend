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
    CREATED(true, HttpStatus.CREATED.value(), "생성에 성공했습니다."),

    //valid
    VALID_ERROR(false, HttpStatus.BAD_REQUEST.value(), "형식이 잘못되었습니다."),
    APPLICANT_NOT_FOUNT(false, HttpStatus.UNAUTHORIZED.value(), "이메일 또는 비밀번호가 잘못되었습니다"),
    EMAIL_FORMAT_INVALID(false, HttpStatus.BAD_REQUEST.value(), "이메일 형식이 올바르지 않습니다."),
    INVALID_UNIV_NAME(false, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 학교 이름입니다."),
    MISMATCHED_EMAIL_DOMAIN(false, HttpStatus.BAD_REQUEST.value(), "작성한 학교의 이메일 도메인 주소가 아닙니다."),

    //error
    UNIV_CERT_API_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "학교 인증 API 호출 중 오류가 발생했습니다."),
    INTERNAL_SERVER_ERROR(false,HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부에서 문제가 발생했습니다."),
    RECRUITING_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "모집항목이 존재하지 않습니다."),
    APPLICATION_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "지원 내역이 존재하지 않습니다."),
    QUESTION_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "질문이 존재하지 않습니다."),
    POST_NOT_FOUND(false, 404, "홍보글을 찾을 수 없습니다."),
    WRONG_SESSION_ERROR(false, HttpStatus.UNAUTHORIZED.value(), "세션값이 잘못되었습니다."),
    JSON_CONVERT_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "JSON 변환중 오류가 발생하였습니다."),
    CLUB_NOT_FOUND(false, 404, "동아리를 찾을 수 없습니다."),
    FORM_NOT_FOUND(false, 404, "지원폼을 찾을 수 없습니다."),
    DUPLICATE_LOGIN_ID(false, HttpStatus.BAD_REQUEST.value(), "이미 존재하는 아이디입니다."),
    ALREADY_APPLIED(false, HttpStatus.BAD_REQUEST.value(), "이미 지원했습니다.");



    private final Boolean isSuccess;
    private final int code;
    private final String message;

    ErrorCode(Boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
