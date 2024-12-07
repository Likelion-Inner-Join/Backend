package com.likelion.innerjoin.post.model.mapper;

import com.likelion.innerjoin.post.model.dto.response.FormResponseDto;
import com.likelion.innerjoin.post.model.entity.Form;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormMapper {
    private final QuestionMapper questionMapper;

    public FormResponseDto toFormResponseDto(Form form) {
        FormResponseDto formResponseDto = new FormResponseDto();
        formResponseDto.setId(form.getId());
        formResponseDto.setTitle(form.getTitle());
        formResponseDto.setDescription(form.getDescription());
        formResponseDto.setQuestionList(questionMapper.toQuestionResponseDtoList(form.getQuestionList()));
        return formResponseDto;
    }
}
