package com.likelion.innerjoin.post.exception;


import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.common.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(RecruitingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse<?> recruitingNotFound(RecruitingNotFoundException e, HttpServletRequest request) {
        log.warn("APPLICATION-001> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.RECRUITING_NOT_FOUND);
    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse<?> applicationNotFound(ApplicationNotFoundException e, HttpServletRequest request) {
        log.warn("APPLICATION-002> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.APPLICATION_NOT_FOUND);
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse<?> questionNotFound(QuestionNotFoundException e, HttpServletRequest request) {
        log.warn("APPLICATION-003> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.QUESTION_NOT_FOUND);
    }

//    @ExceptionHandler(PostNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public CommonResponse<?> postNotFound(QuestionNotFoundException e, HttpServletRequest request) {
//        log.warn("APPLICATION-004> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
//        return new CommonResponse<>(ErrorCode.POST_NOT_FOUND);
//    }

    @ExceptionHandler(AlreadyAppliedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> alreadyApplied(AlreadyAppliedException e, HttpServletRequest request) {
        log.warn("APPLICATION-005> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.ALREADY_APPLIED);
    }

    @ExceptionHandler(MeetingTimeNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse<?> meetingNotFound(MeetingTimeNotFound e, HttpServletRequest request) {
        log.warn("APPLICATION-006> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.MEETING_TIME_NOT_FOUND);
    }

    @ExceptionHandler(AllowedNumExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> allowedNumExceeded(AllowedNumExceededException e, HttpServletRequest request) {
        log.warn("APPLICATION-006> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.ALLOWED_NUM_EXCEEDED);
    }
}
