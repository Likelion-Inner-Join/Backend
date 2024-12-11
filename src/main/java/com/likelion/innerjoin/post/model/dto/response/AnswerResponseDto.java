package com.likelion.innerjoin.post.model.dto.response;


import com.likelion.innerjoin.post.model.entity.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponseDto {
    private Long questionId;
    private String question;
    private String answer;
    private int score;
    private QuestionType questionType;
}
