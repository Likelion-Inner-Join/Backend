package com.likelion.innerjoin.user.service;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.exception.EmailValidationException;
import com.likelion.innerjoin.user.exception.SignUpIDException;
import com.likelion.innerjoin.user.model.dto.request.ApplicantSignUpRequestDto;
import com.likelion.innerjoin.user.model.entity.Applicant;
import com.likelion.innerjoin.user.repository.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

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
}