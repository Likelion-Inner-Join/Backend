package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.MeetingTime;
import com.likelion.innerjoin.post.model.entity.Recruiting;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MeetingTimeRepository extends JpaRepository<MeetingTime, Long> {
    List<MeetingTime> findByRecruiting(Recruiting recruiting);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<MeetingTime> findByRecruitingId(Long recruitingId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    MeetingTime findByMeetingStartTimeAndRecruiting(LocalDateTime startTime, Recruiting recruiting);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<MeetingTime> findById(Long id);
}
