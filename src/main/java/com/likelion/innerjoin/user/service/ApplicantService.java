package com.likelion.innerjoin.user.service;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.post.exception.UnauthorizedException;
import com.likelion.innerjoin.user.exception.EmailValidationException;
import com.likelion.innerjoin.user.exception.SignUpIDException;
import com.likelion.innerjoin.user.model.dto.request.ApplicantSignUpRequestDto;
import com.likelion.innerjoin.user.model.dto.request.ApplicantUpdateRequestDto;
import com.likelion.innerjoin.user.model.dto.response.ApplicantResponseDto;
import com.likelion.innerjoin.user.model.entity.Applicant;
import com.likelion.innerjoin.user.repository.ApplicantRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.likelion.innerjoin.user.model.entity.User;
import com.likelion.innerjoin.user.util.SessionVerifier;


@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final SessionVerifier sessionVerifier;

    /**
     * 지원자 회원가입
     *
     * @param requestDto 지원자 회원가입 요청 DTO
     */
    @Transactional
    public void signUpApplicant(ApplicantSignUpRequestDto requestDto) {
        String email = requestDto.getEmail();
        // 이메일 입력 형식 체크
        if (email == null || !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new EmailValidationException("이메일 형식이 잘못되었습니다.");
        }
        // 이메일 중복 체크
        if (applicantRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new EmailValidationException("이미 존재하는 이메일입니다.");
        }
        // Applicant 엔티티 생성 및 저장
        Applicant applicant = Applicant.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .name(requestDto.getName())
                .school(requestDto.getSchool())
                .major(requestDto.getMajor())
                .studentNumber(requestDto.getStudentNumber())
                .phoneNum(requestDto.getPhoneNum())
                .build();

        applicantRepository.save(applicant);
    }

    /**
     * 지원자 회원 정보 조회
     *
     * @param applicantId 조회하려는 지원자 ID
     * @param session     사용자 세션
     * @return ApplicantResponseDto
     */
    public ApplicantResponseDto getApplicantInfo(Long applicantId, HttpSession session) {
        // 세션에서 사용자 정보 확인
        Applicant applicant = checkApplicant(session);

        // 요청된 applicantId와 세션의 applicantId 비교
        if (!applicant.getId().equals(applicantId)) {
            throw new UnauthorizedException("해당 지원자 접근 권한이 없습니다.");
        }

        return toApplicantResponseDto(applicant);
    }

    /**
     * 세션에서 지원자 정보 확인
     */
    Applicant checkApplicant(HttpSession session) {
        User user = sessionVerifier.verifySession(session);
        if (!(user instanceof Applicant applicant)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        return applicant;
    }

    /**
     * Applicant 엔티티 -> ApplicantResponseDto 변환
     */
    private ApplicantResponseDto toApplicantResponseDto(Applicant applicant) {
        return ApplicantResponseDto.builder()
                .applicantId(applicant.getId())
                .email(applicant.getEmail())
                .name(applicant.getName())
                .school(applicant.getSchool())
                .major(applicant.getMajor())
                .studentNumber(applicant.getStudentNumber())
                .phoneNum(applicant.getPhoneNum())
                .build();
    }

    /**
     * 지원자 정보 수정
     *
     * @param applicantId 지원자 ID
     * @param updateRequestDto 지원자 수정 요청 DTO
     * @param session 사용자 세션
     */
    @Transactional
    public void updateApplicantInfo(Long applicantId, ApplicantUpdateRequestDto updateRequestDto, HttpSession session) {
        // 세션에서 사용자 정보 확인
        Applicant applicant = checkApplicant(session);

        // 요청된 applicantId와 세션의 applicantId 비교
        if (!applicant.getId().equals(applicantId)) {
            throw new UnauthorizedException("해당 지원자 접근 권한이 없습니다.");
        }

        // 지원자 정보 업데이트
        applicant.setName(updateRequestDto.getName());
        //applicant.setSchool(updateRequestDto.getSchool());
        applicant.setMajor(updateRequestDto.getMajor());
        applicant.setPhoneNum(updateRequestDto.getPhoneNum());
        applicantRepository.save(applicant);
    }
}