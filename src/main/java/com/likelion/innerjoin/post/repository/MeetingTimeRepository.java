package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.MeetingTime;
import com.likelion.innerjoin.post.model.entity.Recruiting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingTimeRepository extends JpaRepository<MeetingTime, Long> {
    List<MeetingTime> findByRecruiting(Recruiting recruiting);
    List<MeetingTime> findByRecruitingId(Long recruitingId);
}
