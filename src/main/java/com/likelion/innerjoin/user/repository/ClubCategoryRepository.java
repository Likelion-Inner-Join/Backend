package com.likelion.innerjoin.user.repository;

import com.likelion.innerjoin.user.model.entity.ClubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubCategoryRepository extends JpaRepository<ClubCategory, Long> {
}
