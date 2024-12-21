package com.likelion.innerjoin.post.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PostListResponseDTO {

    private Long postId;
    private Long clubId;
    private String clubName;
    private String title;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    private Integer recruitmentCount;
    private String recruitmentStatus;
    private String recruitmentType;

    private Integer dDay; // 마감일(endTime)까지 남은 일수
    private String categoryName;

    private List<PostImageDTO> image;

    @Data
    @Builder
    @AllArgsConstructor
    public static class PostImageDTO {
        private Long imageId;
        private String imageUrl;
    }

}
