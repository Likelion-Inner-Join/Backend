package com.likelion.innerjoin.post.model.entity;

import com.likelion.innerjoin.common.entity.DataEntity;
import com.likelion.innerjoin.post.util.JsonConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "question")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question extends DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private Form form;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType;

    private Long number;

    private String content;

    @Convert(converter = JsonConverter.class)
    private List<String> list;
}
