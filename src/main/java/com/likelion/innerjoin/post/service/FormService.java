package com.likelion.innerjoin.post.service;

import com.likelion.innerjoin.post.exception.UnauthorizedException;
import com.likelion.innerjoin.post.model.dto.request.FormRequestDto;
import com.likelion.innerjoin.post.model.dto.request.QuestionRequestDto;
import com.likelion.innerjoin.post.model.dto.response.FormResponseDto;
import com.likelion.innerjoin.post.model.entity.Form;
import com.likelion.innerjoin.post.model.entity.Question;
import com.likelion.innerjoin.post.model.mapper.FormMapper;
import com.likelion.innerjoin.post.repository.FormRepository;
import com.likelion.innerjoin.user.model.entity.Club;
import com.likelion.innerjoin.user.repository.ClubRepository;
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

    public FormResponseDto createForm(FormRequestDto formRequestDto, HttpSession session) {
        if( session == null ){
            throw new UnauthorizedException("잘못된 접근입니다.");
        }

        Long userId = (Long) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if( userId == null || !role.equals("club") ){
            throw new UnauthorizedException("잘못된 유저입니다.");
        }

        Club club = clubRepository.findById(userId).orElseThrow(() -> new UnauthorizedException("잘못된 유저입니다."));

        List<Question> questionList = new ArrayList<>();
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
}
