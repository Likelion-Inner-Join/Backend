package com.likelion.innerjoin.user.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClubUpdateRequestDto {

    private String name;
    private String school;
    private String email;
    private String loginId;
    private String password;
    private Long categoryId;

    @Builder
    public ClubUpdateRequestDto(String name, String school, String email, String loginId, String password, Long categoryId) {
        this.name = name;
        this.school = school;
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.categoryId = categoryId;
    }
}