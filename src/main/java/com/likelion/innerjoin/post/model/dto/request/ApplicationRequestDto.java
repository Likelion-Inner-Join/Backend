package com.likelion.innerjoin.post.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequestDto {
    private Long recruitingId;
    private Long applicantId;
    private List<AnswerRequestDto> answers;
}
