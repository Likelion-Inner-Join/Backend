package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
