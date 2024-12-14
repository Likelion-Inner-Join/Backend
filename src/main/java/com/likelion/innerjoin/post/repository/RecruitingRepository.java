package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.Recruiting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitingRepository extends JpaRepository<Recruiting, Long> {
}
