package com.likelion.innerjoin.user.service;

import com.likelion.innerjoin.user.model.dto.response.ClubCategoryResponseDto;

import com.likelion.innerjoin.user.model.entity.ClubCategory;
import com.likelion.innerjoin.user.repository.ClubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubCategoryRepository clubCategoryRepository;

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
