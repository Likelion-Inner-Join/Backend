package com.likelion.innerjoin.post.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private Long formId;
    private String formTitle;
    private String formDescription;

    private Long clubId;
    private String clubName;

    private Long postId;
    private String postTitle;

    private Long applicantId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime meetingStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime meetingEndTime;

    private Integer meetingScore;

    private List<AnswerResponseDto> answers;
}
