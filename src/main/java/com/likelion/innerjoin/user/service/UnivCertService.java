package com.likelion.innerjoin.user.service;

import com.likelion.innerjoin.common.exception.ErrorCode;
import com.likelion.innerjoin.common.response.CommonResponse;
import com.likelion.innerjoin.user.exception.UnivNameException;
import com.likelion.innerjoin.user.model.dto.request.UnivCertRequestDto;
import com.likelion.innerjoin.user.model.dto.response.UnivCertResponseDto;
import com.univcert.api.UnivCert;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UnivCertService {

    @Value("${univcert.api-key}")
    private String apiKey;

    @Value("${univcert.univ-check:true}")
    private boolean univCheck;

    /**
     * 학교 이름 유효성 확인
     */
    public boolean isUnivNameValid(String univName) {
        try {
            Map<String, Object> responseMap = UnivCert.check(univName);

            if (Boolean.TRUE.equals(responseMap.get("success"))) {
                return true;
            } else {
                String errorMessage = (String) responseMap.get("message");
                throw new UnivNameException(errorMessage);
            }
        } catch (Exception e) {
            throw new UnivNameException("학교명을 확인해주세요");
        }
    }

    /**
     * 이메일 인증 요청
     */
    public CommonResponse<Object> certifyEmail(UnivCertRequestDto requestDto) {
        String email = requestDto.getEmail();
        String univName = requestDto.getUnivName();

        if (email == null || !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return new CommonResponse<>(ErrorCode.EMAIL_FORMAT_INVALID);
        }

        if (!isUnivNameValid(univName)) {
            return new CommonResponse<>(ErrorCode.INVALID_UNIV_NAME);
        }

        try {
            Map<String, Object> responseMap = UnivCert.certify(apiKey, email, univName, univCheck);

            if (Boolean.TRUE.equals(responseMap.get("success"))) {
                // 성공 응답 반환
                return new CommonResponse<>(ErrorCode.SUCCESS, null);
            } else {
                String errorMessage = (String) responseMap.get("message");
                return new CommonResponse<>(ErrorCode.UNIV_CERT_API_ERROR, errorMessage);
            }
        } catch (Exception e) {
            return new CommonResponse<>(ErrorCode.INTERNAL_SERVER_ERROR, "인증 요청 중 오류 발생: " + e.getMessage());
        }
    }
}
