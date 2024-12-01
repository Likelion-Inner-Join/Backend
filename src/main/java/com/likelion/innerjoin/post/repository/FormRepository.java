package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form, Long> {
}
