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

        applicationDto.setApplicationId(application.getId());

        applicationDto.setClubId(recruiting.getPost().getClub().getId());
        applicationDto.setClubName(recruiting.getPost().getClub().getName());

        applicationDto.setPostId(recruiting.getPost().getId());
        applicationDto.setPostTitle(recruiting.getPost().getTitle());

        applicationDto.setFormId(recruiting.getForm().getId());
        applicationDto.setFormTitle(recruiting.getForm().getTitle());
        applicationDto.setFormDescription(recruiting.getForm().getDescription());

        applicationDto.setApplicantId(application.getApplicant().getId());

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
