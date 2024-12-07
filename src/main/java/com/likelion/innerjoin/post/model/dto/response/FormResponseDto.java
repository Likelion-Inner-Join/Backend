package com.likelion.innerjoin.post.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormResponseDto {
    private Long id;
    private String title;
    private String description;
    private List<QuestionResponseDto> questionList;
}
