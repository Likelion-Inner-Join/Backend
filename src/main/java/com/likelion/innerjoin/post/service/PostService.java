package com.likelion.innerjoin.post.service;

import com.likelion.innerjoin.common.service.BlobService;
import com.likelion.innerjoin.post.exception.*;
import com.likelion.innerjoin.post.model.dto.PostModifyRequestDTO;
import com.likelion.innerjoin.post.model.dto.PostResponseDTO;
import com.likelion.innerjoin.post.model.dto.request.PostCreateRequestDTO;
import com.likelion.innerjoin.post.model.dto.request.RecruitingRequestDTO;
import com.likelion.innerjoin.post.model.dto.response.ApplicationDto;
import com.likelion.innerjoin.post.model.dto.response.ApplicationListDto;
import com.likelion.innerjoin.post.model.dto.response.PostCreateResponseDTO;
import com.likelion.innerjoin.post.model.entity.*;
import com.likelion.innerjoin.post.model.mapper.ApplicationMapper;
import com.likelion.innerjoin.post.repository.FormRepository;
import com.likelion.innerjoin.post.repository.PostImageRepository;
import com.likelion.innerjoin.post.repository.PostRepository;
import com.likelion.innerjoin.post.repository.RecruitingRepository;
import com.likelion.innerjoin.user.model.entity.Club;
import com.likelion.innerjoin.user.model.entity.User;
import com.likelion.innerjoin.user.repository.ClubRepository;
import com.likelion.innerjoin.user.util.SessionVerifier;
import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final ClubRepository clubRepository;
    private final FormRepository formRepository;
    private final RecruitingRepository recruitingRepository;
    private final BlobService blobService;
    private final SessionVerifier sessionVerifier;
    private final ApplicationMapper applicationMapper;


    // 모든 홍보글 조회
    public List<PostResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        if (posts.isEmpty()) {
            throw new PostNotFoundException();
        }

        return posts.stream()
                .map(this::toPostResponseDTO)
                .collect(Collectors.toList());
    }

    // Post 엔티티를 PostResponseDTO로 변환
    private PostResponseDTO toPostResponseDTO(Post post) {
        // PostImage 리스트를 PostImageDTO로 변환
        List<PostResponseDTO.PostImageDTO> imageDTOs = post.getImageList().stream()
                .map(image -> new PostResponseDTO.PostImageDTO(image.getId(), image.getImageUrl()))
                .collect(Collectors.toList());

        return PostResponseDTO.builder()
                .postId(post.getId())
                .clubId(post.getClub().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .startTime(post.getStartTime())
                .endTime(post.getEndTime())
                .recruitmentStatus(post.getRecruitmentStatus().toString())
                .recruitmentCount(post.getRecruitmentCount())
                .image(imageDTOs)
                .build();
    }


    // 특정 홍보글 조회
    public PostResponseDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));  // post가 없으면 예외 던짐

        // post_id로 Recruiting 엔티티들을 조회
        List<Recruiting> recruitingList = recruitingRepository.findByPostId(postId);

        // 직무(jobTitle) 리스트 추가
        List<String> jobTitleList = recruitingList.stream()
                .map(Recruiting::getJobTitle)
                .collect(Collectors.toList());

        // PostResponseDTO로 변환하여 반환
        return PostResponseDTO.builder()
                .postId(post.getId())
                .clubId(post.getClub().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .startTime(post.getStartTime())
                .endTime(post.getEndTime())
                .recruitmentStatus(post.getRecruitmentStatus().toString())  // Enum 값을 String으로 변환
                .recruitmentCount(post.getRecruitmentCount())
                .recruitmentType(post.getRecruitmentType().toString())
                .jobTitleList(jobTitleList)
                .image(post.getImageList().stream()
                        .map(image -> new PostResponseDTO.PostImageDTO(image.getId(), image.getImageUrl()))
                        .collect(Collectors.toList()))  // 이미지 리스트 변환
                .build();
    }


    // 홍보글 작성
    @Transactional
    public PostCreateResponseDTO createPost(PostCreateRequestDTO postCreateRequestDTO, List<MultipartFile> images, HttpSession session) {

        Club club = checkClub(session);

        // Post 엔티티 생성 및 저장
        Post post = Post.builder()
                .club(club)
                .title(postCreateRequestDTO.getTitle())
                .startTime(LocalDateTime.parse(postCreateRequestDTO.getStartTime()))
                .endTime(LocalDateTime.parse(postCreateRequestDTO.getEndTime()))
                .content(postCreateRequestDTO.getContent())
                .recruitmentStatus(RecruitmentStatus.valueOf(postCreateRequestDTO.getRecruitmentStatus()))
                .recruitmentCount(postCreateRequestDTO.getRecruitmentCount())
                .build();

        postRepository.save(post);

        // Recruiting 엔티티 생성 및 저장
        if (postCreateRequestDTO.getRecruiting() != null) {
            for (RecruitingRequestDTO recruitingRequest : postCreateRequestDTO.getRecruiting()) {
                Form form = formRepository.findById(recruitingRequest.getFormId())
                        .orElseThrow(() -> new FormNotFoundException("Form not found with id: " + recruitingRequest.getFormId()));

                // Form의 club_id가 조회한 club_id와 일치하는지 확인
                if (!form.getClub().getId().equals(club.getId())) {
                    throw new UnauthorizedException("지원폼의 club_id가 현재 유저의 club_id와 일치하지 않습니다.");
                }

                Recruiting recruiting = Recruiting.builder()
                        .form(form)
                        .post(post)
                        .jobTitle(recruitingRequest.getJobTitle())
                        .build();

                recruitingRepository.save(recruiting);
            }
        }

        // 이미지 처리 및 저장
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                try {
                    String imageUrl = blobService.storeFile(image.getOriginalFilename(), image.getInputStream(), image.getSize());
                    PostImage postImage = PostImage.builder()
                            .post(post)
                            .imageUrl(imageUrl)
                            .build();
                    postImageRepository.save(postImage);
                } catch (IOException e) {
                    throw new ImageProcessingException("Error processing image: " + e.getMessage(), e);
                }
            }
        }

        return new PostCreateResponseDTO(post.getId());
    }

    // 홍보글 수정
    @Transactional
    public PostCreateResponseDTO updatePost(Long postId, PostModifyRequestDTO postModifyRequestDTO, List<MultipartFile> images, HttpSession session) {

        // 기존 홍보글 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        // post의 club_id가 유저의 club_id와 일치하는지 확인
        if (!post.getClub().getId().equals(checkClub(session).getId())) {
            throw new UnauthorizedException("홍보글의 club_id가 현재 유저의 club_id와 일치하지 않습니다.");
        }

        // 수정된 내용으로 Post 엔티티 업데이트
        post.setTitle(postModifyRequestDTO.getTitle());
        post.setStartTime(LocalDateTime.parse(postModifyRequestDTO.getStartTime()));
        post.setEndTime(LocalDateTime.parse(postModifyRequestDTO.getEndTime()));
        post.setContent(postModifyRequestDTO.getContent());
        post.setRecruitmentStatus(RecruitmentStatus.valueOf(postModifyRequestDTO.getRecruitmentStatus()));
        post.setRecruitmentCount(postModifyRequestDTO.getRecruitmentCount());

        postRepository.save(post);

        // 이미지 처리 및 저장
        if (images != null && !images.isEmpty()) {
            postImageRepository.deleteByPost(post);  // 기존 이미지 삭제
            for (MultipartFile image : images) {
                try {
                    String imageUrl = blobService.storeFile(image.getOriginalFilename(), image.getInputStream(), image.getSize());
                    PostImage postImage = PostImage.builder()
                            .post(post)
                            .imageUrl(imageUrl)
                            .build();
                    postImageRepository.save(postImage);
                } catch (IOException e) {
                    throw new ImageProcessingException("Error processing image: " + e.getMessage(), e);
                }
            }
        }

        return new PostCreateResponseDTO(post.getId());
    }

    // 홍보글 삭제
    @Transactional
    public void deletePost(Long postId, HttpSession session) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        // 유저의 Club과 게시글의 Club이 일치하는지 확인
        if (!post.getClub().getId().equals(checkClub(session).getId())) {
            throw new UnauthorizedException("삭제할 권한이 없습니다.");
        }

        postRepository.delete(post);
    }

    Club checkClub(HttpSession session) {
        User user = sessionVerifier.verifySession(session);
        if (!(user instanceof Club club)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        return club;
    }

    /**
     * 홍보글에 대한 모든 지원 조회
     * @param post_id 홍보글 아이디
     * @param session 세션값
     * @return 지원 리스트
     */
    public ApplicationListDto getApplications(Long post_id, HttpSession session) {
        User user = sessionVerifier.verifySession(session);
        if(!(user instanceof Club club)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }

        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new PostNotFoundException("해당 post가 없습니다: " + post_id));
        if(!post.getClub().getId().equals(club.getId())) {
            throw new UnauthorizedException("권한이 없습니다.");
        }

        List<Application> applicationList = new ArrayList<>();
        for(Recruiting recruiting : post.getRecruitingList()){
            applicationList.addAll(recruiting.getApplication());
        }

        ApplicationListDto applicationListDto = new ApplicationListDto();
        applicationListDto.setPostId(post.getId());
        applicationListDto.setApplicationList(
                applicationList.stream()
                        .map(application -> applicationMapper.toApplicationDto(application, false))
                        .collect(Collectors.toList())
        );

        return applicationListDto;
    }

}
