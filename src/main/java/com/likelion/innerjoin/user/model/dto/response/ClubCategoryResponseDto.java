package com.likelion.innerjoin.user.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClubCategoryResponseDto {
    private Long categoryId;
    private String categoryName;
}