package com.likelion.innerjoin.post.service;

import com.likelion.innerjoin.post.exception.PostNotFoundException;
import com.likelion.innerjoin.post.model.dto.PostResponseDTO;
import com.likelion.innerjoin.post.model.dto.request.PostCreateRequestDTO;
import com.likelion.innerjoin.post.model.dto.request.RecruitingRequestDTO;
import com.likelion.innerjoin.post.model.dto.response.PostCreateResponseDTO;
import com.likelion.innerjoin.post.model.entity.*;
import com.likelion.innerjoin.post.repository.FormRepository;
import com.likelion.innerjoin.post.repository.PostImageRepository;
import com.likelion.innerjoin.post.repository.PostRepository;
import com.likelion.innerjoin.post.repository.RecruitingRepository;
import com.likelion.innerjoin.user.model.entity.Club;
import com.likelion.innerjoin.user.repository.ClubRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final ClubRepository clubRepository;
    private final FormRepository formRepository;
    private final RecruitingRepository recruitingRepository;

    // 생성자 주입
    @Autowired
    public PostService(PostRepository postRepository,
                       PostImageRepository postImageRepository,
                       ClubRepository clubRepository,
                       FormRepository formRepository,
                       RecruitingRepository recruitingRepository) {
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
        this.clubRepository = clubRepository;
        this.formRepository = formRepository;
        this.recruitingRepository = recruitingRepository;
    }

    /**
     * 모든 홍보글 조회
     *
     * @return List of PostResponseDTO
     */
    public List<PostResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        if (posts.isEmpty()) {
            throw new PostNotFoundException();
        }

        return posts.stream()
                .map(this::toPostResponseDTO)
                .collect(Collectors.toList());
    }

    // 특정 홍보글 조회
    public PostResponseDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));  // post가 없으면 예외 던짐

        return toPostResponseDTO(post);
    }

    /**
     * Post 엔티티를 PostResponseDTO로 변환
     *
     * @param post Post entity
     * @return PostResponseDTO
     */
    private PostResponseDTO toPostResponseDTO(Post post) {
        // PostImage 리스트를 PostImageDTO로 변환
        List<PostResponseDTO.PostImageDTO> imageDTOs = post.getImageList().stream()
                .map(image -> new PostResponseDTO.PostImageDTO(image.getId(), image.getUrl()))
                .collect(Collectors.toList());

        return PostResponseDTO.builder()
                .postId(post.getId())
                .clubId(post.getClub().getId())
                .title(post.getTitle())
                .body(post.getBody())
                .createdAt(post.getCreatedAt())
                .startTime(post.getStartTime())
                .endTime(post.getEndTime())
                .status(post.getStatus().toString())
                .recruitmentCount(post.getRecruitmentCount())
                .image(imageDTOs)
                .build();
    }

    // 홍보글 작성 및 Recruiting 생성
    @Transactional
    public PostCreateResponseDTO createPostWithRecruiting(
            Long clubId, String title, String startTime, String endTime, String body, String status,
            Integer recruitmentCount, List<MultipartFile> images, List<RecruitingRequestDTO> recruiting) throws IOException {

        // Club 조회
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("Club not found"));

        // status 값을 Enum으로 변환
        RecruitmentStatus recruitmentStatus = RecruitmentStatus.valueOf(status.toUpperCase());

        // Post 엔티티 생성
        Post post = Post.builder()
                .club(club)
                .title(title)
                .startTime(LocalDateTime.parse(startTime))
                .endTime(LocalDateTime.parse(endTime))
                .body(body)
                .status(recruitmentStatus)
                .recruitmentCount(recruitmentCount)
                .build();

        // Post 저장
        Post savedPost = postRepository.save(post);

        // Recruiting 저장
        if (recruiting != null && !recruiting.isEmpty()) {
            for (RecruitingRequestDTO recruitingDTO : recruiting) {
                // Form 조회
                Form form = formRepository.findById(recruitingDTO.getFormId())
                        .orElseThrow(() -> new RuntimeException("Form not found"));

                Recruiting recruitingEntity = Recruiting.builder()
                        .post(savedPost)
                        .form(form)
                        .jobTitle(recruitingDTO.getJobTitle())
                        .build();

                recruitingRepository.save(recruitingEntity);
            }
        }

        // 이미지 저장 (이미지 처리)
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                PostImage postImage = new PostImage();
                postImage.setPost(savedPost);
                postImage.setUrl(saveImage(image));  // 이미지 저장 후 URL 반환
                postImageRepository.save(postImage);
            }
        }

        return new PostCreateResponseDTO(savedPost.getId());
    }

    // 이미지 저장 메서드
    private String saveImage(MultipartFile image) throws IOException {
        // 실제 이미지 저장 로직은 Storage 사용하도록 수정 예정
        String imageUrl = "http://example.com/images/" + image.getOriginalFilename();
        return imageUrl;
    }
}
