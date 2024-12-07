package com.likelion.innerjoin.post.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.post.model.dto.PostResponseDTO;
import com.likelion.innerjoin.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public CommonResponse<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> response = postService.getAllPosts();
        return new CommonResponse<>(response);
    }
}
