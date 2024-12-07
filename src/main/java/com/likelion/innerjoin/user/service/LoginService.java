package com.likelion.innerjoin.user.service;


import com.likelion.innerjoin.user.exception.ApplicantNotFoundException;
import com.likelion.innerjoin.user.model.dto.request.ApplicantLoginRequestDto;
import com.likelion.innerjoin.user.model.dto.request.ClubLoginRequestDto;
import com.likelion.innerjoin.user.model.dto.response.ApplicantLoginResponseDto;
import com.likelion.innerjoin.user.model.dto.response.ClubLoginResponseDto;
import com.likelion.innerjoin.user.model.entity.Applicant;
import com.likelion.innerjoin.user.model.entity.Club;
import com.likelion.innerjoin.user.repository.ApplicantRepository;
import com.likelion.innerjoin.user.repository.ClubRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LoginService {
    public final ApplicantRepository applicantRepository;
    public final ClubRepository clubRepository;

    public ApplicantLoginResponseDto applicantLogin(ApplicantLoginRequestDto applicantLoginRequestDto, HttpSession session) {
        try{
            Applicant applicant =
                    applicantRepository.findByEmailAndPassword(
                            applicantLoginRequestDto.getEmail(),
                            applicantLoginRequestDto.getPassword()
                    );

            if(applicant == null) {throw new ApplicantNotFoundException("아이디나 비밀번호가 잘못되었습니다.");}

            session.setAttribute("userId", applicant.getId());
            session.setAttribute("role", "applicant");
            return new ApplicantLoginResponseDto(applicant.getStudentNumber(), applicant.getEmail(), applicant.getName());
        }catch (NoSuchElementException e){
            throw new ApplicantNotFoundException("아이디나 비밀번호가 잘못되었습니다.");
        }
    }

    public ClubLoginResponseDto clubLogin(ClubLoginRequestDto clubLoginRequestDto, HttpSession session) {
        try{
            Club club =
                    clubRepository.findByLoginIdAndPassword(
                            clubLoginRequestDto.getId(),
                            clubLoginRequestDto.getPassword()
                    );

            if(club == null) {throw new ApplicantNotFoundException("아이디나 비밀번호가 잘못되었습니다.");}

            session.setAttribute("userId", club.getId());
            session.setAttribute("role", "club");

            return new ClubLoginResponseDto(club.getLoginId(), club.getEmail());
        }catch (NoSuchElementException e){
            throw new ApplicantNotFoundException("아이디나 비밀번호가 잘못되었습니다.");
        }
    }
}
