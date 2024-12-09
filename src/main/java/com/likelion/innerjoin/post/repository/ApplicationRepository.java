package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
