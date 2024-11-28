package com.likelion.innerjoin.user.repository;

import com.likelion.innerjoin.user.model.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Applicant findByEmailAndPassword(String email, String password);
}
