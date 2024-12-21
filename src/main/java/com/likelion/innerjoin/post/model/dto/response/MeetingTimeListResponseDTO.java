package com.likelion.innerjoin.post.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MeetingTimeListResponseDTO {
    private Long recruitingId;
    private List<MeetingTimeDTO> meetingTimes;
}
