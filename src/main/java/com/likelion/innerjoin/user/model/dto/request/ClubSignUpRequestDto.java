package com.likelion.innerjoin.user.model.dto.request;

import lombok.Data;

@Data
public class ClubSignUpRequestDto {
    private String name;         // 동아리 이름
    private String loginId;      // 로그인 ID
    private String password;     // 비밀번호
    private String email;        // 이메일
    private String school;       // 학교
    private Long categoryId;     // 카테고리 ID
}
