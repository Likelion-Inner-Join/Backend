package com.likelion.innerjoin.post.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MeetingTimeListResponseDTO {
    private Long recruitingId;
    private String jobTitle;
    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private List<MeetingTimeResponseDTO> meetingTimes;
}
