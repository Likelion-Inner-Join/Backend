package com.likelion.innerjoin.user.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.dto.ClubCategoryDto;
import com.likelion.innerjoin.user.model.entity.ClubCategory;
import com.likelion.innerjoin.user.service.ClubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/club/category")
public class ClubCategoryController {

    @Autowired
    private ClubCategoryService clubCategoryService;

    @GetMapping
    public CommonResponse<List<ClubCategoryDto>> getClubCategories() {
        List<ClubCategory> categories = clubCategoryService.getAllCategories();
        List<ClubCategoryDto> categoryList = categories.stream()
                .map(category -> new ClubCategoryDto(category.getId(), category.getCategoryName()))
                .collect(Collectors.toList());

        return new CommonResponse<>(categoryList);
    }
}

