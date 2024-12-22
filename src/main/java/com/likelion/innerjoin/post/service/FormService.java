package com.likelion.innerjoin.post.service;

import com.likelion.innerjoin.post.exception.FormNotFoundException;
import com.likelion.innerjoin.post.exception.UnauthorizedException;
import com.likelion.innerjoin.post.model.dto.request.FormRequestDto;
import com.likelion.innerjoin.post.model.dto.request.QuestionRequestDto;
import com.likelion.innerjoin.post.model.dto.response.FormListResponseDto;
import com.likelion.innerjoin.post.model.dto.response.FormResponseDto;
import com.likelion.innerjoin.post.model.entity.Form;
import com.likelion.innerjoin.post.model.entity.Question;
import com.likelion.innerjoin.post.model.mapper.FormMapper;
import com.likelion.innerjoin.post.model.mapper.QuestionMapper;
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
    private final QuestionMapper questionMapper;

    /**
     * 지원폼 생성
     * @param formRequestDto 지원폼 정보
     * @param session 세션 정보
     * @return 생성된 지원폼
     */
    public FormResponseDto createForm(FormRequestDto formRequestDto, HttpSession session) {
        Form form = Form.builder()
                .club(checkClub(session))
                .title(formRequestDto.getTitle())
                .description(formRequestDto.getDescription())
                .build();
        form.setQuestionList(questionMapper.toQuestionList(formRequestDto.getQuestionList(), form));
        return formMapper.toFormResponseDto(formRepository.save(form));
    }

    /**
     * 지원폼 목록 조회 api
     * @param session 세션 정보
     * @return 조회된 지원폼 리스트
     */
    public List<FormListResponseDto> getFormList(HttpSession session) {
        List<Form> formList = formRepository.findAllByClub(checkClub(session));
        List<FormListResponseDto> formListResponseDtoList = new ArrayList<>();
        for (Form form : formList) {
            FormListResponseDto formListResponseDto = new FormListResponseDto();
            formListResponseDto.setId(form.getId());
            formListResponseDto.setTitle(form.getTitle());
            formListResponseDto.setDescription(form.getDescription());
            formListResponseDto.setModifiedAt(form.getModifiedAt());
            formListResponseDtoList.add(formListResponseDto);
        }
        return formListResponseDtoList;
    }

    /**
     * 지원폼 상세조회 api
     * @param session 세션정보
     * @param formId 지원폼 id
     * @return 조회된 지원폼
     */
    public FormResponseDto getForm(HttpSession session, Long formId) {
        Form form = formRepository.findById(formId).orElseThrow(() -> new FormNotFoundException("id: " + formId + " 지원폼이 존재하지 않습니다."));
//        if (!form.getClub().equals(checkClub(session))) {
//            throw new UnauthorizedException("권한이 없습니다.");
//        }
        return formMapper.toFormResponseDto(form);
    }

    /**
     * form 수정
     * @param session 세션 정보
     * @param formRequestDto 수정할 내용
     * @param formId form id
     * @return 수정된 form
     */
    public FormResponseDto updateForm(HttpSession session, FormRequestDto formRequestDto, Long formId) {
        Form form = formRepository.findById(formId).orElseThrow(() -> new FormNotFoundException("id: " + formId + " 지원폼이 존재하지 않습니다."));
        if (!form.getClub().equals(checkClub(session))) {
            throw new UnauthorizedException("권한이 없습니다.");
        }

        form.setTitle(formRequestDto.getTitle());
        form.setDescription(formRequestDto.getDescription());
        form.getQuestionList().clear();
        form.getQuestionList().addAll(questionMapper.toQuestionList(formRequestDto.getQuestionList(), form));

        return formMapper.toFormResponseDto(formRepository.save(form));
    }

    public Long deleteForm(HttpSession session, Long formId) {
        Form form = formRepository.findById(formId).orElseThrow(() -> new FormNotFoundException("id: " + formId + " 지원폼이 존재하지 않습니다."));
        if (!form.getClub().equals(checkClub(session))) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        formRepository.delete(form);
        return form.getId();
    }


    Club checkClub(HttpSession session) {
        User user = sessionVerifier.verifySession(session);
        if (!(user instanceof Club club)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        return club;
    }
}
