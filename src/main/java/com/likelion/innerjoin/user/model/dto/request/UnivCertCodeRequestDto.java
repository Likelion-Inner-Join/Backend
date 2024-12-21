package com.likelion.innerjoin.user.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnivCertCodeRequestDto {

    private String email;

    private String univName;

    private Integer code;
}
