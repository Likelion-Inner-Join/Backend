package com.likelion.innerjoin.post.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.likelion.innerjoin.common.entity.DataEntity;
import com.likelion.innerjoin.user.model.entity.Club;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@Data
@Builder
@Getter
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "recruitment_status")
    private RecruitmentStatus recruitmentStatus; //모집 상태 (예: 서류 평가 완료 등)

    @Column(name = "recruitment_count")
    private Integer recruitmentCount; //모집 인원

    @Enumerated(EnumType.STRING)
    @Column(name = "recruitment_type")
    private RecruitmentType recruitmentType; //모집 유형 (예: 서류만, 서류와 면접)

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PostImage> imageList;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Recruiting> recruitingList;
}
