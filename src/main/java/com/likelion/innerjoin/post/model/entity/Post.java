package com.likelion.innerjoin.post.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.likelion.innerjoin.common.entity.DataEntity;
import com.likelion.innerjoin.user.model.entity.Club;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    private String title;

    @Column(name = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private String body;

    @Enumerated(EnumType.STRING)
    private RecruitmentStatus status;

    @Column(name = "recruitment_count")
    private Integer recruitmentCount;

    @Column(name = "recruitment_type")
    private String recruitmentType;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PostImage> imageList;
}
