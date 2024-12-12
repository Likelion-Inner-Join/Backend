package com.likelion.innerjoin.post.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PostCreateRequestDTO {
    private Long clubId;
    private String title;
    private String startTime;
    private String endTime;
    private String body;
    private String status;
    private Integer recruitmentCount;
    private List<RecruitingRequestDTO> recruiting;  // Recruiting 리스트 추가

}
