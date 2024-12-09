package com.likelion.innerjoin.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.post.model.dto.PostResponseDTO;
import com.likelion.innerjoin.post.model.dto.request.PostCreateRequestDTO;
import com.likelion.innerjoin.post.model.dto.request.RecruitingRequestDTO;
import com.likelion.innerjoin.post.model.dto.response.PostCreateResponseDTO;
import com.likelion.innerjoin.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CommonResponse<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> response = postService.getAllPosts();
        return new CommonResponse<>(response);
    }

    // 특정 홍보글 디테일 조회
    @GetMapping("/{post_id}")
    public CommonResponse<PostResponseDTO> getPostById(@PathVariable Long post_id) {
        PostResponseDTO postResponseDTO = postService.getPostById(post_id);
        return new CommonResponse<>(postResponseDTO);
    }

    //멀티파트 요청으로 홍보글 생성

    @PostMapping
    public CommonResponse<PostCreateResponseDTO> createPost(
            @RequestParam("clubId") Long clubId,
            @RequestParam("title") String title,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            @RequestParam("body") String body,
            @RequestParam("status") String status,
            @RequestParam("recruitmentCount") Integer recruitmentCount,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "recruiting") String recruiting) {  // JSON 형태로 String으로 받기

        try {
            // recruiting 문자열을 파싱해 JSON 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();

            // recruiting의 JSON 문자열을 List<RecruitingRequestDTO>로 변환
            List<RecruitingRequestDTO> recruitingList = objectMapper.readValue(
                    recruiting, objectMapper.getTypeFactory().constructCollectionType(List.class, RecruitingRequestDTO.class));

            PostCreateResponseDTO result = postService.createPostWithRecruiting(
                    clubId, title, startTime, endTime, body, status, recruitmentCount, images, recruitingList);

            return new CommonResponse<>(result);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponse<>(ErrorCode.VALID_ERROR);
        }
    }
}
