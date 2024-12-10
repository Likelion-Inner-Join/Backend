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
    private Long clubId;
    private Long postId;
    private Long applicantId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime meetingStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime meetingEndTime;

    private List<AnswerResponseDto> answers;
}
