package com.likelion.innerjoin.user.repository;

import com.likelion.innerjoin.user.model.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Club findByLoginIdAndPassword(String loginId, String password);
    Optional<Club> findByEmail(String email);
    Optional<Club> findByLoginId(String loginId);
}
