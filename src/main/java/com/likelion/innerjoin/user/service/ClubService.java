package com.likelion.innerjoin.user.service;

import com.likelion.innerjoin.user.model.dto.response.ClubCategoryResponseDto;

import com.likelion.innerjoin.user.model.entity.ClubCategory;
import com.likelion.innerjoin.user.repository.ClubCategoryRepository;
import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.request.EmailRequestDto;
import com.likelion.innerjoin.user.model.dto.response.EmailResponseDto;
import com.likelion.innerjoin.user.exception.EmailValidationException;
import com.likelion.innerjoin.user.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubCategoryRepository clubCategoryRepository;

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

    /**
     * 모든 카테고리 조회 및 변환
     * @return 카테고리 응답 DTO 리스트
     */
    public List<ClubCategoryResponseDto> getClubCategories() {
        List<ClubCategory> categories = clubCategoryRepository.findAll();

        return categories.stream()
                .map(category -> new ClubCategoryResponseDto(category.getId(), category.getCategoryName()))
                .collect(Collectors.toList());
    }
}
