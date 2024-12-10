package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.Application;
import com.likelion.innerjoin.user.model.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByApplicant(Applicant applicant);
}
