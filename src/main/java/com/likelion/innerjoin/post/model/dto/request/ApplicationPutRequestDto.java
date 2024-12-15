package com.likelion.innerjoin.post.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.likelion.innerjoin.post.model.entity.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationPutRequestDto {
    private ResultType formResult;
    private ResultType meetingResult;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime meetingStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime meetingEndTime;
}
