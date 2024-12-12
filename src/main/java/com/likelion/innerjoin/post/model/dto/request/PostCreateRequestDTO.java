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

    private String content;

    private String recruitmentStatus;

    private Integer recruitmentCount;

    private List<RecruitingRequestDTO> recruiting;

}
