package com.likelion.innerjoin.user.repository;

import com.likelion.innerjoin.user.model.entity.Applicant;
import com.likelion.innerjoin.user.model.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Applicant findByEmailAndPassword(String email, String password);
    Optional<Applicant> findByEmail(String email);
}
