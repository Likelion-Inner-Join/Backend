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

    public ApplicationDto toApplicationDto(Application application) {
        Recruiting recruiting = application.getRecruiting();

        ApplicationDto applicationDto = new ApplicationDto();

        applicationDto.setApplicationId(application.getId());
        applicationDto.setClubId(recruiting.getPost().getClub().getId());
        applicationDto.setPostId(recruiting.getPost().getId());
        applicationDto.setFormId(recruiting.getForm().getId());
        if(application.getMeetingTime() != null) {
            applicationDto.setMeetingEndTime(application.getMeetingTime().getMeetingEndTime());
            applicationDto.setMeetingStartTime(application.getMeetingTime().getMeetingStartTime());
        }
        applicationDto.setAnswers(
                application.getResponseList().stream()
                        .map(questionMapper::toAnswerResponseDto)
                        .collect(Collectors.toList())
        );

        return applicationDto;
    }

}
