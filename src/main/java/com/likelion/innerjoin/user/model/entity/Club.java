package com.likelion.innerjoin.user.model.entity;

import com.likelion.innerjoin.common.entity.DataEntity;
import com.likelion.innerjoin.post.model.entity.Form;
import com.likelion.innerjoin.post.model.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "club")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Club extends DataEntity implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    private String school;

    private String email;

    private String password;

    @Column(name = "login_id", unique = true, nullable = false)
    private String loginId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ClubCategory category;

    @OneToMany(mappedBy = "club", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Post> postList;

    @OneToMany(mappedBy = "club", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Form> formList;
}
