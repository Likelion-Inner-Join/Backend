package com.likelion.innerjoin.user.repository;

import com.likelion.innerjoin.user.model.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Club findByLoginIdAndPassword(String loginId, String password);
}
