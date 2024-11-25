package com.likelion.innerjoin.user.model.entity;

import com.likelion.innerjoin.common.entity.DataEntity;
import com.likelion.innerjoin.post.model.entity.Application;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "applicant")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Applicant extends DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicant_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @Column(name = "student_number")
    private String studentNumber;
    @Column(name = "applicant_name")
    private String name;

    private String school;
    private String major;
    @Column(name = "phone_number")
    private String phoneNum;

    @OneToMany(mappedBy = "applicant", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Application> applicationList;
}
