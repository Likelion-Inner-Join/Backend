package com.likelion.innerjoin.post.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormScoreDto {
    private Long applicationId;
    private List<AnswerScoreDto> score;
}
