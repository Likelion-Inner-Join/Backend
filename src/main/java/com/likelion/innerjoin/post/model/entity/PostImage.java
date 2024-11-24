package com.likelion.innerjoin.post.model.entity;

import com.likelion.innerjoin.common.entity.DataEntity;
import jakarta.persistence.*;

public class PostImage extends DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
