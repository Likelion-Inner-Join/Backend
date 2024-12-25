
package com.likelion.innerjoin.user.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicantUpdateRequestDto {

    private String name;
    private String major;
    private String phoneNum;
    private String password;

    @Builder
    public ApplicantUpdateRequestDto(String name, String major, String phoneNum, String password) {
        this.name = name;
        //this.school = school;
        this.major = major;
        this.phoneNum = phoneNum;
        this.password = password;
    }
}
