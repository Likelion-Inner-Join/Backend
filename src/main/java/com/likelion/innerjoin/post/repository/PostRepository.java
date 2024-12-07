package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
