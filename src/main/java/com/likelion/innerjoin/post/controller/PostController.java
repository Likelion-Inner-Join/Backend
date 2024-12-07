package com.likelion.innerjoin.post.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.post.exception.PostNotFoundException;
import com.likelion.innerjoin.post.model.dto.PostResponseDTO;
import com.likelion.innerjoin.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // 홍보글 리스트 조회
    @GetMapping
    public CommonResponse<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> response = postService.getAllPosts();
        return new CommonResponse<>(response);
    }

    // 특정 홍보글 디테일 조회
    @GetMapping("/{post_id}")
    public CommonResponse<PostResponseDTO> getPostById(@PathVariable Long post_id) {
        PostResponseDTO postResponseDTO = postService.getPostById(post_id);

        if (postResponseDTO == null) {
            throw new PostNotFoundException();  // Post가 존재하지 않으면 예외 던지기
        }

        return new CommonResponse<>(postResponseDTO);
    }
}
