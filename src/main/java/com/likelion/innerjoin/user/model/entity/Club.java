package com.likelion.innerjoin.user.model.entity;

import com.likelion.innerjoin.common.entity.DataEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "club")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Club extends DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Integer id;

    private String name;
    @Column(name = "image_url")
    private String imageUrl;
    private String school;
    private String email;
    private String password;
    @Column(name = "id", unique = true, nullable = false)
    private String loginId;
    @Column(name = "cate_list")
    private String categoryList;

}
