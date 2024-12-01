package com.likelion.innerjoin.user.service;


import com.likelion.innerjoin.user.exception.ApplicantNotFoundException;
import com.likelion.innerjoin.user.model.dto.request.LoginRequestDto;
import com.likelion.innerjoin.user.model.dto.response.LoginResponseDto;
import com.likelion.innerjoin.user.model.entity.Applicant;
import com.likelion.innerjoin.user.repository.ApplicantRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LoginService {
    public final ApplicantRepository applicantRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpSession session) {
        try{
            Applicant applicant =
                    applicantRepository.findByEmailAndPassword(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword()
                    );

            if(applicant == null) {throw new ApplicantNotFoundException("아이디나 비밀번호가 잘못되었습니다.");}

            session.setAttribute("userId", applicant.getId());
            session.setAttribute("role", "applicant");
            return new LoginResponseDto(applicant.getStudentNumber(), applicant.getEmail(), applicant.getName());
        }catch (NoSuchElementException e){
            throw new ApplicantNotFoundException("아이디나 비밀번호가 잘못되었습니다.");
        }
    }
}
