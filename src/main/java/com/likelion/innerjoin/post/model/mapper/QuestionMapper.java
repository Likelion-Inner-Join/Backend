package com.likelion.innerjoin.post.model.mapper;

import com.likelion.innerjoin.post.model.dto.response.QuestionResponseDto;
import com.likelion.innerjoin.post.model.entity.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionMapper {
    public QuestionResponseDto toQuestionResponseDto(Question question) {
        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        questionResponseDto.setQuestionId(question.getId());
        questionResponseDto.setNumber(question.getNumber());
        questionResponseDto.setQuestion(question.getContent());
        questionResponseDto.setType(question.getQuestionType());
        questionResponseDto.setList(question.getList());
        return questionResponseDto;
    }

    public List<QuestionResponseDto> toQuestionResponseDtoList(List<Question> questions) {
        List<QuestionResponseDto> questionResponseDtoList = new ArrayList<>();
        for (Question question : questions) {
            questionResponseDtoList.add(toQuestionResponseDto(question));
        }
        return questionResponseDtoList;
    }
}
