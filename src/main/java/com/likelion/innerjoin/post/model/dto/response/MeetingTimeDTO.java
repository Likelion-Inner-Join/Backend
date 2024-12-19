package com.likelion.innerjoin.post.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MeetingTimeDTO {
    private Long meetingTimeId;
    private int allowedNum;
    private LocalDateTime meetingStartTime;
    private LocalDateTime meetingEndTime;
}

