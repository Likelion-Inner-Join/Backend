package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.Form;
import com.likelion.innerjoin.post.model.entity.Post;
import com.likelion.innerjoin.post.model.entity.Recruiting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruitingRepository extends JpaRepository<Recruiting, Long> {
    void deleteByPost(Post post);
    Optional<Recruiting> findByPostAndForm(Post post, Form form);
}
