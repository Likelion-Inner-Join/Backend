package com.likelion.innerjoin.post.model.dto.response;

import com.likelion.innerjoin.post.model.entity.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDto {
    private Long questionId;
    private Long number;
    private String question;
    private QuestionType type;
    private List<String> list;
}
