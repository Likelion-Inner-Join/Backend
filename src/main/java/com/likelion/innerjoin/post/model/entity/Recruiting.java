package com.likelion.innerjoin.post.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recruiting")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recruiting {
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
}
