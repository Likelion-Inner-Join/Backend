package com.likelion.innerjoin.user.model.dto.request;

import lombok.Data;

@Data
public class UnivCertRequestDto {
    private String email;       // 사용자 이메일
    private String univName;    // 사용자 입력 대학명
}
