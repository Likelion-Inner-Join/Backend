package com.likelion.innerjoin.user.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicantResponseDto {
    private Long applicantId;
    private String email;
    private String name;
    private String school;
    private String major;
    private String studentNumber;
}
