package com.likelion.innerjoin.post.service;

import com.likelion.innerjoin.post.exception.PostNotFoundException;
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

    /**
     * Converts a Post entity to PostResponseDTO.
     *
     * @param post Post entity
     * @return PostResponseDTO
     */
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
                .recruitmentCount(Integer.parseInt(post.getRecruitmentCount()))
                .build();
    }
}
