package com.likelion.innerjoin.user.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubLoginRequestDto {
    private String id;
    private String password;
}
