package com.likelion.innerjoin.post.service;

import com.likelion.innerjoin.post.exception.PostNotFoundException;
import com.likelion.innerjoin.post.model.dto.PostResponseDTO;
import com.likelion.innerjoin.post.model.entity.Post;
import com.likelion.innerjoin.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    /**
     * Fetch all posts from the database.
     *
     * @return List of PostResponseDTO
     */
    public List<PostResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        // If no posts are found, throw PostNotFoundException
        if (posts.isEmpty()) {
            throw new PostNotFoundException();
        }

        return posts.stream()
                .map(this::toPostResponseDTO)
                .collect(Collectors.toList());
    }

    // 특정 포스트 조회
    public PostResponseDTO getPostById(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            return toPostResponseDTO(post);
        } else {
            return null;  // 해당 post가 없다면 null 반환
        }
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
                .recruitmentType(post.getRecruitmentType())
                .recruitmentCount(post.getRecruitmentCount())
                .image(imageDTOs)
                .build();
    }
}
