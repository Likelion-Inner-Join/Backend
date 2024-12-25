
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

    @Builder
    public ApplicantUpdateRequestDto(String name, String major, String phoneNum) {
        this.name = name;
        //this.school = school;
        this.major = major;
        this.phoneNum = phoneNum;
    }
}
