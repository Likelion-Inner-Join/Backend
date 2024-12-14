package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
