package com.likelion.innerjoin.post.model.entity;

import com.likelion.innerjoin.common.entity.DataEntity;
import com.likelion.innerjoin.user.model.entity.Applicant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "application")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Application extends DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiting_id")
    private Recruiting recruiting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_time_id")
    private MeetingTime meetingTime;

    @Column(name = "form_result")
    @Enumerated(EnumType.STRING)
    private ResultType formResult;

    @Column(name = "meeting_result")
    @Enumerated(EnumType.STRING)
    private ResultType meetingResult;

    @Column(name = "meeting_score")
    private Integer meetingScore;

    @OneToMany(mappedBy = "application", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Response> responseList;
}
