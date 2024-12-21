package com.likelion.innerjoin.post.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class MeetingTimeResponseDTO {
    private Long meetingTimeId;
    private int allowedNum;
    private int reservedNum; // 예약된 사람 수
    private List<ApplicantDTO> applicantList; // 예약한 사람 리스트
    private LocalDateTime meetingStartTime;
    private LocalDateTime meetingEndTime;

    @Data
    @AllArgsConstructor
    public static class ApplicantDTO { // static으로 선언
        private Long applicantId;
        private String name;
        private String studentNumber;
    }
}
