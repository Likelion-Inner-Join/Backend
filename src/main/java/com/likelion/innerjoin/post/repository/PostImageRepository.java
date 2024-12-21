package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.Post;
import com.likelion.innerjoin.post.model.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    void deleteByPost(Post post);
    List<PostImage> findByPostId(Long postId);
}
