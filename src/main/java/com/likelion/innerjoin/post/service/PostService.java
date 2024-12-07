package com.likelion.innerjoin.post.service;

import com.likelion.innerjoin.post.model.dto.PostResponseDTO;
import com.likelion.innerjoin.post.model.entity.Post;
import com.likelion.innerjoin.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<PostResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();  // 게시글 목록을 데이터베이스에서 가져옴


        // Post 엔티티 리스트를 PostResponseDTO로 변환
        return posts.stream()
                .map(post -> toPostResponseDTO(post))  // Post -> PostResponseDTO 변환
                .collect(Collectors.toList());
    }

    // Post 엔티티를 PostResponseDTO로 변환하는 메서드
    private PostResponseDTO toPostResponseDTO(Post post) {
        return PostResponseDTO.builder()
                .postId(post.getId())
                .clubId(post.getClub().getId())
                .title(post.getTitle())
                .body(post.getBody())
                .createdAt(post.getCreatedAt())
                .startTime(post.getStartTime())
                .endTime(post.getEndTime())
                .status(post.getStatus().toString())
                .recruitmentType(post.getRecruitmentType())
                .recruitmentCount(Integer.parseInt(post.getRecruitmentCount()))  // String -> Integer 변환
                .image(post.getImageList().stream()
                        .map(image -> new PostResponseDTO.PostImageDTO(image.getId(), image.getUrl()))
                        .collect(Collectors.toList()))
                .build();
    }
}
