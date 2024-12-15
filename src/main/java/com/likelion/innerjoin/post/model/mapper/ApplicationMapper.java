package com.likelion.innerjoin.post.model.mapper;

import com.likelion.innerjoin.post.model.dto.response.ApplicationDto;
import com.likelion.innerjoin.post.model.entity.Application;
import com.likelion.innerjoin.post.model.entity.Recruiting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ApplicationMapper {
    private final QuestionMapper questionMapper;

    public ApplicationDto toApplicationDto(Application application, boolean withAnswer) {
        Recruiting recruiting = application.getRecruiting();

        ApplicationDto applicationDto = new ApplicationDto();

        //recruiting
        applicationDto.setRecruitingId(recruiting.getId());
        applicationDto.setPositionName(recruiting.getJobTitle());
        applicationDto.setRecruitmentStatus(recruiting.getPost().getRecruitmentStatus());

        // applicationId
        applicationDto.setApplicationId(application.getId());

        // form
        applicationDto.setFormId(recruiting.getForm().getId());
        applicationDto.setFormTitle(recruiting.getForm().getTitle());
        applicationDto.setFormDescription(recruiting.getForm().getDescription());

        // club
        applicationDto.setClubId(recruiting.getPost().getClub().getId());
        applicationDto.setClubName(recruiting.getPost().getClub().getName());

        // post
        applicationDto.setPostId(recruiting.getPost().getId());
        applicationDto.setPostTitle(recruiting.getPost().getTitle());



        //applicant
        applicationDto.setApplicantId(application.getApplicant().getId());
        applicationDto.setName(application.getApplicant().getName());
        applicationDto.setEmail(application.getApplicant().getEmail());
        applicationDto.setPhoneNum(application.getApplicant().getPhoneNum());
        applicationDto.setSchool(application.getApplicant().getSchool());
        applicationDto.setMajor(application.getApplicant().getMajor());
        applicationDto.setStudentNumber(application.getApplicant().getStudentNumber());

        applicationDto.setFormResult(application.getFormResult());
        applicationDto.setFormScore(application.getFormScore());

        applicationDto.setMeetingResult(application.getMeetingResult());
        applicationDto.setMeetingScore(application.getMeetingScore());

        // 면접
        if(application.getMeetingTime() != null) {
            applicationDto.setMeetingEndTime(application.getMeetingTime().getMeetingEndTime());
            applicationDto.setMeetingStartTime(application.getMeetingTime().getMeetingStartTime());
        }
        applicationDto.setMeetingScore(application.getMeetingScore());

        if(withAnswer) {
            applicationDto.setAnswers(
                    application.getResponseList().stream()
                            .map(questionMapper::toAnswerResponseDto)
                            .collect(Collectors.toList())
            );
        }
        return applicationDto;
    }

}
