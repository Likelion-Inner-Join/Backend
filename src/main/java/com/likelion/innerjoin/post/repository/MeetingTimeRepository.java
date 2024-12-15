package com.likelion.innerjoin.post.repository;

import com.likelion.innerjoin.post.model.entity.MeetingTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingTimeRepository extends JpaRepository<MeetingTime, Long> {
}
