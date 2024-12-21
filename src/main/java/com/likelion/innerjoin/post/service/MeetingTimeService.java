package com.likelion.innerjoin.post.service;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.post.exception.PostNotFoundException;
import com.likelion.innerjoin.post.exception.RecruitingNotFoundException;
import com.likelion.innerjoin.post.exception.UnauthorizedException;
import com.likelion.innerjoin.post.model.dto.response.MeetingTimeResponseDTO;
import com.likelion.innerjoin.post.model.dto.response.MeetingTimeListResponseDTO;
import com.likelion.innerjoin.post.model.entity.Application;
import com.likelion.innerjoin.post.model.entity.MeetingTime;
import com.likelion.innerjoin.post.model.entity.Recruiting;
import com.likelion.innerjoin.post.model.entity.Post;
import com.likelion.innerjoin.post.model.dto.request.MeetingTimeRequestDTO;
import com.likelion.innerjoin.post.repository.MeetingTimeRepository;
import com.likelion.innerjoin.post.repository.PostRepository;
import com.likelion.innerjoin.post.repository.RecruitingRepository;
import com.likelion.innerjoin.user.model.entity.Club;
import com.likelion.innerjoin.user.model.entity.User;
import com.likelion.innerjoin.user.util.SessionVerifier;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingTimeService {

    private final RecruitingRepository recruitingRepository;
    private final MeetingTimeRepository meetingTimeRepository;
    private final PostRepository postRepository;
    private final SessionVerifier sessionVerifier;
    
    //면접시간 리스트 생성
    public void createMeetingTimes(Long recruitingId, List<MeetingTimeRequestDTO.MeetingTimeDto> meetingTimeDtos, HttpSession session) {

        Recruiting recruiting = recruitingRepository.findById(recruitingId)
                .orElseThrow(() -> new RecruitingNotFoundException("Recruiting not found with id: " + recruitingId));

        Post post = postRepository.findById(recruiting.getPost().getId())
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        // post의 club_id가 유저의 club_id와 일치하는지 확인
        if (!post.getClub().getId().equals(checkClub(session).getId())) {
            throw new UnauthorizedException("홍보글의 club_id가 현재 유저의 club_id와 일치하지 않습니다.");
        }

        // 요청DTO를 MeetingTime 엔티티로 변환
        List<MeetingTime> meetingTimes = meetingTimeDtos.stream()
                .map(dto -> {
                    MeetingTime meetingTime = new MeetingTime();
                    meetingTime.setAllowedNum(dto.getAllowedNum());
                    meetingTime.setMeetingStartTime(dto.getMeetingStartTime());
                    meetingTime.setMeetingEndTime(dto.getMeetingEndTime());
                    meetingTime.setRecruiting(recruiting);
                    return meetingTime;
                })
                .collect(Collectors.toList());

        meetingTimeRepository.saveAll(meetingTimes);
    }


    Club checkClub(HttpSession session) {
        User user = sessionVerifier.verifySession(session);
        if (!(user instanceof Club club)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        return club;
    }

    //면접시간 정보 조회
    public CommonResponse<MeetingTimeListResponseDTO> getMeetingTimesByRecruitingId(Long recruitingId) {
        // recruiting 찾기
        Recruiting recruiting = recruitingRepository.findById(recruitingId)
                .orElseThrow(() -> new IllegalArgumentException("Recruiting not found with id: " + recruitingId));

        // recruiting과 연관된 면접시간 리스트
        List<MeetingTime> meetingTimes = meetingTimeRepository.findByRecruiting(recruiting);

        // DTO로 변환
        List<MeetingTimeResponseDTO> meetingTimeDtos = meetingTimes.stream()
                .map(meetingTime -> {
                    // 예약된 사람 리스트
                    List<Application> applications = meetingTime.getApplicationList();
                    List<MeetingTimeResponseDTO.ApplicantDTO> applicantDtos = applications.stream()
                            .map(application -> new MeetingTimeResponseDTO.ApplicantDTO(
                                    application.getApplicant().getId(),
                                    application.getApplicant().getName(),
                                    application.getApplicant().getStudentNumber()
                            ))
                            .collect(Collectors.toList());

                    return new MeetingTimeResponseDTO(
                            meetingTime.getId(),
                            meetingTime.getAllowedNum(),
                            applications.size(), // 예약된 사람 수
                            applicantDtos,
                            meetingTime.getMeetingStartTime(),
                            meetingTime.getMeetingEndTime()
                    );
                })
                .collect(Collectors.toList());

        MeetingTimeListResponseDTO responseDto = new MeetingTimeListResponseDTO(recruitingId, meetingTimeDtos);
        return new CommonResponse<>(responseDto);
    }
}
