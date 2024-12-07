package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.Form;
import com.likelion.innerjoin.user.model.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {
    List<Form> findAllByClub(Club club);
}
