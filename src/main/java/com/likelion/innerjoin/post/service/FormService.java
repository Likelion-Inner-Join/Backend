package com.likelion.innerjoin.post.service;

import com.likelion.innerjoin.post.exception.UnauthorizedException;
import com.likelion.innerjoin.post.model.dto.request.FormRequestDto;
import com.likelion.innerjoin.post.model.dto.request.QuestionRequestDto;
import com.likelion.innerjoin.post.model.dto.response.FormListResponseDto;
import com.likelion.innerjoin.post.model.dto.response.FormResponseDto;
import com.likelion.innerjoin.post.model.entity.Form;
import com.likelion.innerjoin.post.model.entity.Question;
import com.likelion.innerjoin.post.model.mapper.FormMapper;
import com.likelion.innerjoin.post.repository.FormRepository;
import com.likelion.innerjoin.user.model.entity.Club;
import com.likelion.innerjoin.user.model.entity.User;
import com.likelion.innerjoin.user.repository.ClubRepository;
import com.likelion.innerjoin.user.util.SessionVerifier;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FormService {
    private final ClubRepository clubRepository;
    private final FormRepository formRepository;
    private final FormMapper formMapper;
    private final SessionVerifier sessionVerifier;

    public FormResponseDto createForm(FormRequestDto formRequestDto, HttpSession session) {

        User user = sessionVerifier.verifySession(session);
        if(!(user instanceof Club club)){
            throw new UnauthorizedException("권한이 없습니다.");
        }

        List<Question> questionList = new ArrayList<>();

        if(formRequestDto.getQuestionList() != null){
            for( QuestionRequestDto question : formRequestDto.getQuestionList() ){
                questionList.add(
                        Question.builder()
                                .number(question.getNumber())
                                .content(question.getQuestion())
                                .questionType(question.getType())
                                .list(question.getList())
                                .build()
                );
            }
        }

        Form form = formRepository.save(
                Form.builder()
                        .club(club)
                        .questionList(questionList)
                        .title(formRequestDto.getTitle())
                        .description(formRequestDto.getDescription())
                        .build()
        );

        return formMapper.toFormResponseDto(form);
    }

    public List<FormListResponseDto> getFormList(HttpSession session){
        User user = sessionVerifier.verifySession(session);
        if(!(user instanceof Club club)){
            throw new UnauthorizedException("권한이 없습니다.");
        }
        List<Form> formList = formRepository.findAllByClub(club);
        List<FormListResponseDto> formListResponseDtoList = new ArrayList<>();
        for( Form form : formList ){
            FormListResponseDto formListResponseDto = new FormListResponseDto();
            formListResponseDto.setId(form.getId());
            formListResponseDto.setTitle(form.getTitle());
            formListResponseDto.setDescription(form.getDescription());
            formListResponseDto.setModifiedAt(form.getModifiedAt());
            formListResponseDtoList.add(formListResponseDto);
        }
        return formListResponseDtoList;
    }
}
