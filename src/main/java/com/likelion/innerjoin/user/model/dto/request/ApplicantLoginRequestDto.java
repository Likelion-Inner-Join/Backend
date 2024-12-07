package com.likelion.innerjoin.user.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantLoginRequestDto {
    private String email;
    private String password;
}
