package com.likelion.innerjoin.user.service;

import com.likelion.innerjoin.user.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;

    public boolean isEmailExists(String email) {
        return clubRepository.findByEmail(email).isPresent();
    }
}
