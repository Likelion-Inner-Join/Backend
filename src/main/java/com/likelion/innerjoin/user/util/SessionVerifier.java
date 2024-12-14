package com.likelion.innerjoin.user.util;

import com.likelion.innerjoin.post.exception.UnauthorizedException;
import com.likelion.innerjoin.user.model.entity.User;
import com.likelion.innerjoin.user.repository.ApplicantRepository;
import com.likelion.innerjoin.user.repository.ClubRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SessionVerifier {
    private final ClubRepository clubRepository;
    private final ApplicantRepository applicantRepository;

    public User verifySession(HttpSession session) {
        if( session == null ){
            throw new UnauthorizedException("잘못된 접근입니다.");
        }

        if( session.getAttribute("userId") == null || session.getAttribute("role") == null ){
            throw new UnauthorizedException("잘못된 접근입니다.");
        }

        Long userId = (Long) session.getAttribute("userId") ;

        String role = (String) session.getAttribute("role");

        if( userId == null ){
            throw new UnauthorizedException("잘못된 유저입니다.");
        }

        if( role.equals("club") ){
            return clubRepository.findById(userId).orElseThrow(() -> new UnauthorizedException("잘못된 유저입니다."));
        }else if ( role.equals("applicant") ){
            return applicantRepository.findById(userId).orElseThrow(() -> new UnauthorizedException("잘못된 유저입니다."));
        }else {
            throw new UnauthorizedException("잘못된 유저입니다.");
        }
    }
}
