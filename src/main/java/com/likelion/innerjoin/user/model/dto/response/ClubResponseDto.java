package com.likelion.innerjoin.user.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClubResponseDto {
    private Long clubId;
    private String id;
    private String name;
    private String school;
    private Long categoryId;
    private String categoryName;
    private String email;
    private String imageUrl;
}
