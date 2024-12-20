package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.Form;
import com.likelion.innerjoin.post.model.entity.Post;
import com.likelion.innerjoin.post.model.entity.Recruiting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitingRepository extends JpaRepository<Recruiting, Long> {
    @Query("SELECT r FROM Recruiting r WHERE r.post.id = :postId")
    List<Recruiting> findByPostId(@Param("postId") Long postId);
}
