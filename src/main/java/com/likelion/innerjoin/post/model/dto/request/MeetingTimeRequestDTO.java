package com.likelion.innerjoin.post.model.dto.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MeetingTimeRequestDTO {
    private Long recruitingId;
    private List<MeetingTimeDto> meetingTimes;
    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;

    @Data
    public static class MeetingTimeDto {
        private int allowedNum;
        private LocalDateTime meetingStartTime;
        private LocalDateTime meetingEndTime;
    }
}
