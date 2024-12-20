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
public class PostResponseDTO {

    private Long postId;
    private Long clubId;
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

    private List<PostImageDTO> image;

    private List<RecruitingDTO> recruitingList;

    @Data
    @Builder
    @AllArgsConstructor
    public static class PostImageDTO {
        private Long imageId;
        private String imageUrl;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class RecruitingDTO {
        private Long recruitingId;
        private Long formId;
        private String jobTitle;
    }

}
