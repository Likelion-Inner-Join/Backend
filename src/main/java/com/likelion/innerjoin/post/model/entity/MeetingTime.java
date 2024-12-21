package com.likelion.innerjoin.post.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.likelion.innerjoin.common.entity.DataEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "meeting_time")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetingTime extends DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_time_id")
    private Long id;

    @Column(name = "allowed_num")
    private int allowedNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "meeting_start_time")
    private LocalDateTime meetingStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "meeting_end_time")
    private LocalDateTime meetingEndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiting_id")
    private Recruiting recruiting;

    @OneToMany(mappedBy = "meetingTime", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Application> applicationList;
}
