package com.likelion.innerjoin.post.service;


import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.post.exception.RecruitingNotFoundException;
import com.likelion.innerjoin.post.exception.UnauthorizedException;
import com.likelion.innerjoin.post.model.dto.request.ApplicationRequestDto;
import com.likelion.innerjoin.post.model.entity.Application;
import com.likelion.innerjoin.post.model.entity.Recruiting;
import com.likelion.innerjoin.post.model.entity.ResultType;
import com.likelion.innerjoin.post.repository.ApplicationRepository;
import com.likelion.innerjoin.post.repository.RecruitingRepository;
import com.likelion.innerjoin.user.model.entity.Applicant;
import com.likelion.innerjoin.user.model.entity.User;
import com.likelion.innerjoin.user.util.SessionVerifier;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final SessionVerifier sessionVerifier;
    private final RecruitingRepository recruitingRepository;
    private final ApplicationRepository applicationRepository;


    public ErrorCode postApplication (ApplicationRequestDto applicationRequestDto, HttpSession session) {
        Applicant applicant = checkApplicant(session);
        Recruiting recruiting = recruitingRepository.findById(applicationRequestDto.getRecruitingId())
                .orElseThrow(() ->new RecruitingNotFoundException("모집중 직무가 존재하지 않습니다."));

        Application application = new Application();
        application.setApplicant(applicant);
        application.setRecruiting(recruiting);

        // TODO: recruiting type에 따라서 결과를 미리 넣어놓기 (response도 있는지 없는지에 따라 처리해줘야함)
        application.setFormResult(ResultType.PENDING);
        application.setMeetingResult(ResultType.PENDING);

        applicationRepository.save(application);
        return ErrorCode.CREATED;
    }


    Applicant checkApplicant (HttpSession session) {
        User user = sessionVerifier.verifySession(session);
        if(!(user instanceof Applicant applicant)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        return applicant;
    }
}
