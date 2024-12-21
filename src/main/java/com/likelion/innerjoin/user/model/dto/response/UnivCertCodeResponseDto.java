package com.likelion.innerjoin.user.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UnivCertCodeResponseDto {
    private boolean success;
    private String univName;
    private String certifiedEmail;
    private String certifiedDate;
}
