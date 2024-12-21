package com.likelion.innerjoin.post.service;

import com.likelion.innerjoin.common.service.BlobService;
import com.likelion.innerjoin.post.exception.*;
import com.likelion.innerjoin.post.model.dto.request.PostModifyRequestDTO;
import com.likelion.innerjoin.post.model.dto.response.PostDetailResponseDTO;
import com.likelion.innerjoin.post.model.dto.request.PostCreateRequestDTO;
import com.likelion.innerjoin.post.model.dto.request.RecruitingRequestDTO;
import com.likelion.innerjoin.post.model.dto.response.ApplicationListDto;
import com.likelion.innerjoin.post.model.dto.response.PostCreateResponseDTO;
import com.likelion.innerjoin.post.model.dto.response.PostListResponseDTO;
import com.likelion.innerjoin.post.model.entity.*;
import com.likelion.innerjoin.post.model.mapper.ApplicationMapper;
import com.likelion.innerjoin.post.repository.FormRepository;
import com.likelion.innerjoin.post.repository.PostImageRepository;
import com.likelion.innerjoin.post.repository.PostRepository;
import com.likelion.innerjoin.post.repository.RecruitingRepository;
import com.likelion.innerjoin.user.model.entity.Club;
import com.likelion.innerjoin.user.model.entity.User;
import com.likelion.innerjoin.user.util.SessionVerifier;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final FormRepository formRepository;
    private final RecruitingRepository recruitingRepository;
    private final BlobService blobService;
    private final SessionVerifier sessionVerifier;
    private final ApplicationMapper applicationMapper;


    // 홍보글 리스트 조회
    public List<PostListResponseDTO> getAllPosts(String clubName) {
        List<Post> posts;

        if (clubName != null && !clubName.isEmpty()) {
            posts = postRepository.findByClubNameContaining(clubName); // 동아리 이름으로 검색
        } else {
            posts = postRepository.findAll(); // 전체 조회
        }

        if (posts.isEmpty()) {
            throw new PostNotFoundException();
        }

        return posts.stream()
                .map(this::toPostResponseDTO)
                .collect(Collectors.toList());
    }

    private int calculateDDay(LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();

        if (endTime.isBefore(now)) {
            return -1; // endTime이 현재 시각 이전이면 -1
        } else if (endTime.toLocalDate().isEqual(now.toLocalDate())) {
            return 0; // 당일이고 아직 마감 시간이 지나지 않았으면 0
        } else {
            return (int) ChronoUnit.DAYS.between(now.toLocalDate(), endTime.toLocalDate()); // 남은 일수 계산
        }
    }

    // Post 엔티티를 PostResponseDTO로 변환
    private PostListResponseDTO toPostResponseDTO(Post post) {
        // PostImage 리스트를 PostImageDTO로 변환
        List<PostListResponseDTO.PostImageDTO> imageDTOs = post.getImageList().stream()
                .map(image -> new PostListResponseDTO.PostImageDTO(image.getId(), image.getImageUrl()))
                .collect(Collectors.toList());

        // D-Day 계산
        int dDay = calculateDDay(post.getEndTime());

        return PostListResponseDTO.builder()
                .postId(post.getId())
                .clubId(post.getClub().getId())
                .clubName(post.getClub().getName())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .startTime(post.getStartTime())
                .endTime(post.getEndTime())
                .recruitmentCount(post.getRecruitmentCount())
                .recruitmentStatus(post.getRecruitmentStatus().toString())
                .recruitmentType(post.getRecruitmentType().toString())
                .dDay(dDay) // D-Day 추가
                .categoryName(post.getClub().getCategory().getCategoryName()) // Category Name 추가
                .image(imageDTOs)
                .build();
    }


    // 특정 홍보글 디테일 조회
    public PostDetailResponseDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        // Recruiting 엔티티 조회
        List<Recruiting> recruitingList = recruitingRepository.findByPostId(postId);
        if (recruitingList == null || recruitingList.isEmpty()) {
            throw new RecruitingNotFoundException("Recruiting not found with post_id: " + postId);
        }

        // Recruiting 리스트를 변환
        List<PostDetailResponseDTO.RecruitingDTO> recruitingDTOList = recruitingList.stream()
                .map(recruiting -> PostDetailResponseDTO.RecruitingDTO.builder()
                        .recruitingId(recruiting.getId())
                        .formId(recruiting.getForm().getId())
                        .jobTitle(recruiting.getJobTitle())
                        .build())
                .collect(Collectors.toList());

        // D-Day 계산
        int dDay = calculateDDay(post.getEndTime());

        // PostResponseDTO로 변환하여 반환
        return PostDetailResponseDTO.builder()
                .postId(post.getId())
                .clubId(post.getClub().getId())
                .clubName(post.getClub().getName())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .startTime(post.getStartTime())
                .endTime(post.getEndTime())
                .recruitmentCount(post.getRecruitmentCount())
                .recruitmentStatus(post.getRecruitmentStatus().toString())
                .recruitmentType(post.getRecruitmentType().toString())
                .dDay(dDay)
                .categoryName(post.getClub().getCategory().getCategoryName())
                .image(post.getImageList().stream()
                        .map(image -> new PostDetailResponseDTO.PostImageDTO(image.getId(), image.getImageUrl()))
                        .collect(Collectors.toList()))
                .recruitingList(recruitingDTOList)
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
                .startTime(postCreateRequestDTO.getStartTime())
                .endTime(postCreateRequestDTO.getEndTime())
                .content(postCreateRequestDTO.getContent())
                .recruitmentStatus(RecruitmentStatus.valueOf(postCreateRequestDTO.getRecruitmentStatus()))
                .recruitmentCount(postCreateRequestDTO.getRecruitmentCount())
                .recruitmentType(RecruitmentType.valueOf(postCreateRequestDTO.getRecruitmentType()))
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

        // 기존 이미지 삭제
        List<PostImage> existingImages = postImageRepository.findByPostId(postId);
        for (PostImage image : existingImages) {
            String imageUrl = image.getImageUrl();
            String filename = imageUrl.substring(imageUrl.lastIndexOf('/') + 1); // URL에서 파일명 추출

            try {
                boolean deleted = blobService.deleteFile(filename); // Blob Storage에서 삭제
                if (!deleted) {
                    throw new ImageProcessingException("Blob Storage에서 이미지 삭제 실패: " + filename, null);
                }
                postImageRepository.delete(image); // DB에서 삭제
            } catch (Exception e) {
                throw new ImageProcessingException("DB에서 이미지 삭제 중 에러 발생: " + imageUrl, e);
            }
        }

        // 새로운 이미지 저장
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

    // 홍보글 삭제
    @Transactional
    public void deletePost(Long postId, HttpSession session) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        // 유저의 Club과 게시글의 Club이 일치하는지 확인
        if (!post.getClub().getId().equals(checkClub(session).getId())) {
            throw new UnauthorizedException("삭제할 권한이 없습니다.");
        }

        // 홍보글과 연관된 이미지 리스트 조회
        List<PostImage> images = postImageRepository.findByPostId(postId);

        // Blob Storage와 DB에서 이미지 삭제
        for (PostImage image : images) {
            String imageUrl = image.getImageUrl();
            String filename = imageUrl.substring(imageUrl.lastIndexOf('/') + 1); // URL에서 파일명 추출

            try {
                boolean deleted = blobService.deleteFile(filename); // Blob Storage에서 삭제
                if (!deleted) {
                    throw new ImageProcessingException("Blob Storage에서 이미지 삭제 실패: " + filename, null);
                }
                postImageRepository.delete(image); // DB에서 삭제
            } catch (Exception e) {
                throw new ImageProcessingException("DB에서 이미지 삭제 중 에러 발생: " + imageUrl, e);
            }
        }

        // 홍보글 삭제
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
