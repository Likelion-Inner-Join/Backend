package com.likelion.innerjoin.user.service;

import com.likelion.innerjoin.post.exception.UnauthorizedException;
import com.likelion.innerjoin.user.model.dto.response.ClubCategoryResponseDto;

import com.likelion.innerjoin.user.model.dto.response.ClubResponseDto;
import com.likelion.innerjoin.user.model.entity.Club;
import com.likelion.innerjoin.user.model.entity.ClubCategory;
import com.likelion.innerjoin.user.model.entity.User;
import com.likelion.innerjoin.user.repository.ClubCategoryRepository;
import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.model.dto.request.EmailRequestDto;
import com.likelion.innerjoin.user.model.dto.response.EmailResponseDto;
import com.likelion.innerjoin.user.exception.EmailValidationException;
import com.likelion.innerjoin.user.repository.ClubRepository;
import com.likelion.innerjoin.user.util.SessionVerifier;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubCategoryRepository clubCategoryRepository;
    private final SessionVerifier sessionVerifier;

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

    /**
     * 동아리 회원 정보 조회
     *
     * @param clubId  조회하려는 동아리 ID
     * @param session 사용자 세션
     * @return ClubResponseDto
     */
    public ClubResponseDto getClubInfo(Long clubId, HttpSession session) {
        // 세션에서 사용자 정보 확인
        Club club = checkClub(session);

        // 요청된 clubId와 세션의 clubId 비교
        if (!club.getId().equals(clubId)) {
            throw new UnauthorizedException("해당 동아리 접근 권한이 없습니다.");
        }

        return toClubResponseDto(club);
    }

    /**
     * 세션에서 클럽 정보 확인
     */
    Club checkClub(HttpSession session) {
        User user = sessionVerifier.verifySession(session);
        if(!(user instanceof Club club)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        return club;
    }
    /**
     * Club 엔티티 -> ClubResponseDto 변환
     */
    private ClubResponseDto toClubResponseDto(Club club) {
        return ClubResponseDto.builder()
                .clubId(club.getId())
                .id(club.getLoginId())
                .name(club.getName())
                .school(club.getSchool())
                .email(club.getEmail())
                .imageUrl(club.getImageUrl())
                .categoryId(club.getCategoryList())
                .build();
    }
}
