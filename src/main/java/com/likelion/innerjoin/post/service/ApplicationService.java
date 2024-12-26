package com.likelion.innerjoin.post.service;


import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.post.exception.*;
import com.likelion.innerjoin.post.model.dto.request.*;
import com.likelion.innerjoin.post.model.dto.response.ApplicationDto;
import com.likelion.innerjoin.post.model.dto.response.MeetingTimeResponseDTO;
import com.likelion.innerjoin.post.model.entity.*;
import com.likelion.innerjoin.post.model.mapper.ApplicationMapper;
import com.likelion.innerjoin.post.repository.*;
import com.likelion.innerjoin.user.model.entity.Applicant;
import com.likelion.innerjoin.user.model.entity.Club;
import com.likelion.innerjoin.user.model.entity.User;
import com.likelion.innerjoin.user.util.SessionVerifier;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final SessionVerifier sessionVerifier;
    private final RecruitingRepository recruitingRepository;
    private final ApplicationRepository applicationRepository;
    private final ResponseRepository responseRepository;
    private final QuestionRepository questionRepository;
    private final MeetingTimeRepository meetingTimeRepository;

    private final ApplicationMapper applicationMapper;

    private final JavaMailSender mailSender;
    private final PostRepository postRepository;

    @Transactional
    public Application postApplication (ApplicationRequestDto applicationRequestDto, HttpSession session) {
        Applicant applicant = checkApplicant(session);
        Recruiting recruiting = recruitingRepository.findById(applicationRequestDto.getRecruitingId())
                .orElseThrow(() ->new RecruitingNotFoundException("모집중 직무가 존재하지 않습니다."));

        for(Application application : recruiting.getApplication()){
            if(application.getApplicant().equals(applicant)){
                throw new AlreadyAppliedException("이미 지원한 지원자입니다.");
            }
        }

        Application application = new Application();
        application.setApplicant(applicant);
        application.setRecruiting(recruiting);

        Post post = recruiting.getPost();
        if(post.getRecruitmentType() == RecruitmentType.FORM_AND_MEETING){
            application.setFormResult(ResultType.PENDING);
            application.setMeetingResult(ResultType.PENDING);
        }else if(post.getRecruitmentType() == RecruitmentType.FORM_ONLY){
            application.setFormResult(ResultType.PENDING);
            application.setMeetingResult(ResultType.PASS);
        }else if(post.getRecruitmentType() == RecruitmentType.MEETING_ONLY){
            application.setFormResult(ResultType.PASS);
            application.setMeetingResult(ResultType.PENDING);
        }

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

    @Transactional
    public ApplicationDto updateApplication(
            ApplicationPutRequestDto applicationPutRequestDto,
            Long applicationId,
            HttpSession session){
        Club club = checkClub(session);

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("id: " + applicationId + " 지원서가 존재하지 않습니다."));

        if(!application.getRecruiting().getPost().getClub().equals(club) ) {
            throw new UnauthorizedException("권한이 없습니다.");
        }

        MeetingTime meetingTime = meetingTimeRepository.findByMeetingStartTimeAndRecruiting(
                applicationPutRequestDto.getMeetingStartTime(),
                application.getRecruiting()
        );
        if(meetingTime == null) {
            throw new MeetingTimeNotFound("면접시간이 존재하지 않습니다.");
        }

        application.setFormResult(applicationPutRequestDto.getFormResult());
        application.setMeetingResult(applicationPutRequestDto.getMeetingResult());

        if(meetingTime.getApplicationList().size() >= meetingTime.getAllowedNum() && !meetingTime.getApplicationList().contains(application)){
            throw new AllowedNumExceededException("허용 인원을 초과하였습니다.");
        }
        application.setMeetingTime(meetingTime);

        applicationRepository.save(application);
        return applicationMapper.toApplicationDto(application, false);
    }

    @Transactional
    public ApplicationDto updateFormScore(FormScoreDto formScoreDto, HttpSession session) {
        Club club = checkClub(session);

        Application application = applicationRepository.findById(formScoreDto.getApplicationId())
                .orElseThrow(() -> new ApplicationNotFoundException("지원서가 존재하지 않습니다."));

        if(!application.getRecruiting().getPost().getClub().equals(club)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }

        // 점수 입력
        Map<Long, Integer> questionScoreMap = formScoreDto.getScore().stream()
                .collect(Collectors.toMap(AnswerScoreDto::getQuestionId, AnswerScoreDto::getScore));

        Integer totalScore = 0;
        for(Response response : application.getResponseList()) {
            Long questionId = response.getQuestion().getId();
            if (questionScoreMap.containsKey(questionId)) {
                response.setScore(questionScoreMap.get(questionId));
                totalScore += questionScoreMap.get(questionId);
            }
        }
        application.setFormScore(totalScore);

        applicationRepository.save(application);
        return applicationMapper.toApplicationDto(application, true);
    }

    @Transactional
    public ApplicationDto updateMeetingScore(MeetingScoreDto meetingScoreDto, HttpSession session) {
        Club club = checkClub(session);

        Application application = applicationRepository.findById(meetingScoreDto.getApplicationId())
                .orElseThrow(() -> new ApplicationNotFoundException("지원서가 존재하지 않습니다."));

        if(!application.getRecruiting().getPost().getClub().equals(club)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }

        application.setMeetingScore(meetingScoreDto.getScore());
        applicationRepository.save(application);
        return applicationMapper.toApplicationDto(application, false);
    }

    public ErrorCode sendEmail(EmailDto emailDto, HttpSession session) {

        Club club = checkClub(session);
        Post post = postRepository.findById(emailDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("권한이 없습니다."));
        if(!post.getClub().equals(club)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }

        List<Application> application = applicationRepository.findAllById(emailDto.getApplicationIdList());

        // 해당 post에 대한 지원자 이메일인지 검증은 일단 스킵
        List<String> emailList = new ArrayList<>();
        for(Application app : application){
            emailList.add(app.getApplicant().getEmail());
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailList.toArray(new String[0])); // 여러 명의 수신자
        message.setSubject(emailDto.getTitle());
        message.setText(emailDto.getContent());
        message.setFrom("innerjoin75@gmail.com");

        mailSender.send(message);
        return ErrorCode.SUCCESS;
    }

    /**
     * 면접 시간 설정
     * @param dto 설정할 면접시간정보
     * @param session 세션값
     * @return MeetingTimeDTO
     */
    @Transactional
    public MeetingTimeResponseDTO selectMeetingTime (MeetingTimeSelectionDto dto, HttpSession session){
        Applicant applicant = checkApplicant(session);
        Application application = applicationRepository.findById(dto.getApplicationId())
                .orElseThrow(()-> new ApplicationNotFoundException("지원 이력이 없습니다."));
        if(!application.getApplicant().equals(applicant)){
            throw new UnauthorizedException("권한이 없습니다.");
        }

        MeetingTime meetingTime = meetingTimeRepository.findById(dto.getMeetingTimeId())
                .orElseThrow(() -> new MeetingTimeNotFound("면접시간이 존재하지 않습니다."));
        if(!meetingTime.getRecruiting().equals(application.getRecruiting())){
            throw new UnauthorizedException("권한이 없습니다.");
        }
        if(meetingTime.getAllowedNum()<= meetingTime.getApplicationList().size()){
            throw new AllowedNumExceededException("면접 허용 인원을 초과했습니다.");
        }

        // 예약 종료 시간 확인
        LocalDateTime reservationEndTime = meetingTime.getRecruiting().getReservationEndTime();
        if (reservationEndTime != null && LocalDateTime.now().isAfter(reservationEndTime)) {
            throw new IllegalStateException("면접 예약이 종료되었습니다.");
        }

        //면접 시간 설정
        application.setMeetingTime(meetingTime);
        applicationRepository.save(application);

        return new MeetingTimeResponseDTO(
                meetingTime.getId(),
                meetingTime.getAllowedNum(),
                meetingTime.getApplicationList().size() + 1,
                null,
                meetingTime.getMeetingStartTime(),
                meetingTime.getMeetingEndTime()
        );
    }

    Applicant checkApplicant (HttpSession session) {
        User user = sessionVerifier.verifySession(session);
        if(!(user instanceof Applicant applicant)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        return applicant;
    }

    Club checkClub (HttpSession session) {
        User user = sessionVerifier.verifySession(session);
        if(!(user instanceof Club club)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        return club;
    }
}
