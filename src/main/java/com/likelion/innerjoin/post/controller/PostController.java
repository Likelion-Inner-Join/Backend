package com.likelion.innerjoin.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.post.model.dto.PostResponseDTO;
import com.likelion.innerjoin.post.model.dto.request.PostCreateRequestDTO;
import com.likelion.innerjoin.post.model.dto.request.RecruitingRequestDTO;
import com.likelion.innerjoin.post.model.dto.response.PostCreateResponseDTO;
import com.likelion.innerjoin.post.model.entity.Post;
import com.likelion.innerjoin.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.InvalidMidiDataException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // 홍보글 리스트 조회
    @GetMapping
    @Operation(summary = "홍보글 리스트 조회 api")
    public CommonResponse<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> response = postService.getAllPosts();
        return new CommonResponse<>(response);
    }

    // 특정 홍보글 디테일 조회
    @GetMapping("/{post_id}")
    @Operation(summary = "홍보글 디테일 조회 api")
    public CommonResponse<PostResponseDTO> getPostById(@PathVariable Long post_id) {
        PostResponseDTO postResponseDTO = postService.getPostById(post_id);
        return new CommonResponse<>(postResponseDTO);
    }

    // 홍보글 작성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "홍보글 작성 api")
    public CommonResponse<PostCreateResponseDTO> createPost(
            @RequestPart("post") @Valid PostCreateRequestDTO postCreateRequestDTO, // 홍보글 생성 DTO
            @RequestPart(value = "images", required = false) List<MultipartFile> images, // 이미지들 (옵션)
            HttpSession session) {

            PostCreateResponseDTO responseDTO = postService.createPost(postCreateRequestDTO, images, session);
            return new CommonResponse<>(responseDTO);
    }

}
