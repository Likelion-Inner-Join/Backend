package com.likelion.innerjoin.post.controller;

import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.post.model.dto.PostModifyRequestDTO;
import com.likelion.innerjoin.post.model.dto.PostResponseDTO;
import com.likelion.innerjoin.post.model.dto.request.PostCreateRequestDTO;
import com.likelion.innerjoin.post.model.dto.response.ApplicationListDto;
import com.likelion.innerjoin.post.model.dto.response.PostCreateResponseDTO;
import com.likelion.innerjoin.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping
    @Operation(summary = "홍보글 리스트 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "홍보글 리스트 조회 성공"),
            @ApiResponse(responseCode = "404", description = "홍보글을 찾을 수 없습니다")
    })
    public CommonResponse<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> response = postService.getAllPosts();
        return new CommonResponse<>(response);
    }


    @GetMapping("/{post_id}")
    @Operation(summary = "특정 홍보글 디테일 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "홍보글 디테일 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 id의 홍보글을 찾을 수 없습니다")
    })
    public CommonResponse<PostResponseDTO> getPostById(@PathVariable Long post_id) {
        PostResponseDTO postResponseDTO = postService.getPostById(post_id);
        return new CommonResponse<>(postResponseDTO);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "홍보글 작성 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "홍보글 작성 및 모집중직무 생성 성공"),
            @ApiResponse(responseCode = "401", description = "세션값이 잘못되었습니다"),
            @ApiResponse(responseCode = "404", description = "해당 form id를 찾을 수 없습니다")
    })
    public CommonResponse<PostCreateResponseDTO> createPost(
            @RequestPart("post") @Valid PostCreateRequestDTO postCreateRequestDTO, // 홍보글 생성 DTO
            @RequestPart(value = "images", required = false) List<MultipartFile> images, // 이미지들 (옵션)
            HttpSession session) {

            PostCreateResponseDTO responseDTO = postService.createPost(postCreateRequestDTO, images, session);
            return new CommonResponse<>(responseDTO);
    }


    @PutMapping("/{post_id}")
    @Operation(summary = "홍보글 수정 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "홍보글 수정 성공"),
            @ApiResponse(responseCode = "401", description = "세션값이 잘못되었습니다"),
            @ApiResponse(responseCode = "404", description = "해당 post id를 찾을 수 없습니다")
    })
    public CommonResponse<PostCreateResponseDTO> updatePost(
            @PathVariable Long post_id,
            @RequestPart("post") @Valid PostModifyRequestDTO postModifyRequestDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            HttpSession session) {

        PostCreateResponseDTO responseDTO = postService.updatePost(post_id, postModifyRequestDTO, images, session);
        return new CommonResponse<>(responseDTO);
    }


    @DeleteMapping("/{post_id}")
    @Operation(summary = "홍보글 삭제 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "홍보글 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "세션값이 잘못되었습니다"),
            @ApiResponse(responseCode = "404", description = "해당 post id를 찾을 수 없습니다")
    })
    public CommonResponse<String> deletePost(@PathVariable Long post_id, HttpSession session) {
        postService.deletePost(post_id, session);
        return new CommonResponse<>("Post deleted successfully.");
    }

    @GetMapping("/{post_id}/application")
    @Operation(summary = "홍보글별 지원자 리스트 조회")
    public CommonResponse<ApplicationListDto> getApplications(@PathVariable Long post_id, HttpSession session) {
        return new CommonResponse<>(postService.getApplications(post_id, session));
    }

}
