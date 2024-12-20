package com.likelion.innerjoin.post.model.entity;

import com.likelion.innerjoin.common.entity.DataEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "recruiting")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recruiting extends DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruiting_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private Form form;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name  = "post_id")
    private Post post;

    @Column(name = "job_title")
    private String jobTitle;

    @OneToMany(mappedBy = "recruiting", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Application> application;

    @OneToMany(mappedBy = "recruiting", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<MeetingTime> meetingTimeList;
}
