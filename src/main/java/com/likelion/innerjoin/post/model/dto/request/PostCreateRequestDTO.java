package com.likelion.innerjoin.post.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PostCreateRequestDTO {

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private String content;

    private String recruitmentStatus; //모집 상태 (예: 서류 평가 완료 등)

    private Integer recruitmentCount; //모집 인원

    private String recruitmentType; //모집 유형 (예: 서류만, 서류와 면접)

    private List<RecruitingRequestDTO> recruiting;

}
