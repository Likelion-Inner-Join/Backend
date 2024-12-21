package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByClubName(String clubName);

    @Query("SELECT p FROM Post p WHERE p.club.name LIKE %:clubName%")
    List<Post> findByClubNameContaining(@Param("clubName") String clubName);

}
