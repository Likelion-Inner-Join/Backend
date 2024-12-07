package com.likelion.innerjoin.user.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantLoginResponseDto {
    String studentNumber;
    String email;
    String name;
}
