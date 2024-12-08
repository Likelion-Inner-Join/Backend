package com.likelion.innerjoin.post.model.mapper;

import com.likelion.innerjoin.post.model.dto.request.QuestionRequestDto;
import com.likelion.innerjoin.post.model.dto.response.QuestionResponseDto;
import com.likelion.innerjoin.post.model.entity.Form;
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

    public List<Question> toQuestionList(List<QuestionRequestDto> questionResponseDtoList, Form form) {
        List<Question> questionList = new ArrayList<>();

        if (questionResponseDtoList != null) {
            for (QuestionRequestDto question : questionResponseDtoList) {
                questionList.add(
                        Question.builder()
                                .number(question.getNumber())
                                .content(question.getQuestion())
                                .questionType(question.getType())
                                .list(question.getList())
                                .form(form)
                                .build()
                );
            }
        }

        return questionList;
    }
}
