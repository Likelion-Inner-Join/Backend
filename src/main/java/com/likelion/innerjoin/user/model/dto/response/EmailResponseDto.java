package com.likelion.innerjoin.user.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailResponseDto {
    private Boolean isExists; // 이메일 중복 여부
}
