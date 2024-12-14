package com.likelion.innerjoin.post.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class PostModifyRequestDTO {

    private String title;

    private String content;

    private String startTime;  // 수정된 시작 시간

    private String endTime;    // 수정된 종료 시간

    private String recruitmentStatus;  // 수정된 모집 상태

    private Integer recruitmentCount;  // 수정된 모집 인원 수

    private List<MultipartFile> images;  // 이미지 목록

}
