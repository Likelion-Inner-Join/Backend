package com.likelion.innerjoin.post.service;


import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.post.exception.ApplicationNotFoundException;
import com.likelion.innerjoin.post.exception.QuestionNotFoundException;
import com.likelion.innerjoin.post.exception.RecruitingNotFoundException;
import com.likelion.innerjoin.post.exception.UnauthorizedException;
import com.likelion.innerjoin.post.model.dto.request.AnswerRequestDto;
import com.likelion.innerjoin.post.model.dto.request.ApplicationRequestDto;
import com.likelion.innerjoin.post.model.dto.response.ApplicationDto;
import com.likelion.innerjoin.post.model.entity.Application;
import com.likelion.innerjoin.post.model.entity.Recruiting;
import com.likelion.innerjoin.post.model.entity.Response;
import com.likelion.innerjoin.post.model.entity.ResultType;
import com.likelion.innerjoin.post.model.mapper.ApplicationMapper;
import com.likelion.innerjoin.post.repository.ApplicationRepository;
import com.likelion.innerjoin.post.repository.QuestionRepository;
import com.likelion.innerjoin.post.repository.RecruitingRepository;
import com.likelion.innerjoin.post.repository.ResponseRepository;
import com.likelion.innerjoin.user.model.entity.Applicant;
import com.likelion.innerjoin.user.model.entity.Club;
import com.likelion.innerjoin.user.model.entity.User;
import com.likelion.innerjoin.user.util.SessionVerifier;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final SessionVerifier sessionVerifier;
    private final RecruitingRepository recruitingRepository;
    private final ApplicationRepository applicationRepository;
    private final ResponseRepository responseRepository;
    private final QuestionRepository questionRepository;

    private final ApplicationMapper applicationMapper;


    public Application postApplication (ApplicationRequestDto applicationRequestDto, HttpSession session) {
        Applicant applicant = checkApplicant(session);
        Recruiting recruiting = recruitingRepository.findById(applicationRequestDto.getRecruitingId())
                .orElseThrow(() ->new RecruitingNotFoundException("모집중 직무가 존재하지 않습니다."));

        Application application = new Application();
        application.setApplicant(applicant);
        application.setRecruiting(recruiting);

        // TODO: recruiting type에 따라서 결과를 미리 넣어놓기 (response도 있는지 없는지에 따라 처리해줘야함)
        application.setFormResult(ResultType.PENDING);
        application.setMeetingResult(ResultType.PENDING);

        application.setResponseList(new ArrayList<>());
        for(AnswerRequestDto answer : applicationRequestDto.getAnswers()) {
            Response response = new Response();
            response.setApplication(application);
            response.setQuestion(
                    questionRepository.findById(answer.getQuestionId())
                    .orElseThrow(() -> new QuestionNotFoundException("질문이 존재하지 않습니다."))
            );
            response.setContent(answer.getAnswer());
            application.getResponseList().add(response);
        }


        return applicationRepository.save(application);
    }


    public ApplicationDto getApplicationDetail(Long applicationId, HttpSession session) {
        User user = sessionVerifier.verifySession(session);
        if(!(user instanceof Applicant) & !(user instanceof Club)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("지원 내역이 존재하지 않습니다."));


        if(user instanceof Applicant applicant) {
            if(!applicant.equals(application.getApplicant())) {
                throw new UnauthorizedException("권한이 없습니다.");
            }
        }else {
            Club club = (Club) user;
            if (!club.equals(application.getRecruiting().getPost().getClub())) {
                throw new UnauthorizedException("권한이 없습니다.");
            }
        }

        return applicationMapper.toApplicationDto(application, true);
    }

    public List<ApplicationDto> getApplicationList(HttpSession session) {
        Applicant applicant = checkApplicant(session);

        List<Application> applicationList = applicationRepository.findByApplicant(applicant);

        return applicationList.stream()
                .map(application -> applicationMapper.toApplicationDto(application, false))
                .collect(Collectors.toList());
    }


    Applicant checkApplicant (HttpSession session) {
        User user = sessionVerifier.verifySession(session);
        if(!(user instanceof Applicant applicant)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        return applicant;
    }
}
