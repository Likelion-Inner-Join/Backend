package com.likelion.innerjoin.post.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.likelion.innerjoin.post.model.entity.RecruitmentStatus;
import com.likelion.innerjoin.post.model.entity.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {
    private Long applicationId;

    private Long recruitingId;
    private String positionName;
    private RecruitmentStatus recruitmentStatus;

    private Long formId;
    private String formTitle;
    private String formDescription;

    private Long clubId;
    private String clubName;

    private Long postId;
    private String postTitle;

    private Long applicantId;
    private String name;
    private String email;
    private String phoneNum;
    private String school;
    private String major;
    private String studentNumber;

    private ResultType formResult;
    private Integer formScore;

    private ResultType meetingResult;
    private Integer meetingScore;


    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime meetingStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime meetingEndTime;



    private List<AnswerResponseDto> answers;
}
