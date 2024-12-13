package com.likelion.innerjoin.user.service;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.request.EmailRequestDto;
import com.likelion.innerjoin.user.model.dto.response.EmailResponseDto;
import com.likelion.innerjoin.user.exception.EmailValidationException;
import com.likelion.innerjoin.user.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    /**
     * 이메일 중복 확인
     * @param requestDto 요청 DTO
     * @return 응답 DTO
     */
    public EmailResponseDto checkEmailExists(EmailRequestDto requestDto) {
        String email = requestDto.getEmail();

        // 이메일 형식 유효성 검증
        if (email == null || !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new EmailValidationException("이메일 형식이 잘못되었습니다.");
        }
        // 이메일 중복 여부 확인
        boolean exists = clubRepository.findByEmail(email).isPresent();
        return new EmailResponseDto(exists);
    }
}
