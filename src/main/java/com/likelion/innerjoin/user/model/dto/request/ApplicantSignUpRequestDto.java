package com.likelion.innerjoin.user.model.dto.request;

import lombok.Data;

@Data
public class ApplicantSignUpRequestDto {
    private String email;
    private String password;
    private String name;
    private String school;
    private String major;
    private String studentNumber;
    private String phoneNum;
}
