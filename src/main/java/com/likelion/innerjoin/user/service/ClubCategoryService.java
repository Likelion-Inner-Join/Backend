package com.likelion.innerjoin.user.service;

import com.likelion.innerjoin.user.model.entity.ClubCategory;
import com.likelion.innerjoin.user.repository.ClubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubCategoryService {
    private final ClubCategoryRepository clubCategoryRepository;

    public List<ClubCategory> getAllCategories() {
        return clubCategoryRepository.findAll();
    }
}
