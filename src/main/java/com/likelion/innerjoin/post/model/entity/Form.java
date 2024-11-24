package com.likelion.innerjoin.post.model.entity;

import com.likelion.innerjoin.common.entity.DataEntity;
import com.likelion.innerjoin.user.model.entity.Club;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "form")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Form extends DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name  = "club_id")
    private Club club;

    private String title;
    private String description;

    @OneToMany(mappedBy = "form", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Question> questionList;
}
