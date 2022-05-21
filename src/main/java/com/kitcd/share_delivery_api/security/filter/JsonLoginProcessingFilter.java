package com.kitcd.share_delivery_api.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitcd.share_delivery_api.dto.account.loginDTO;
import com.kitcd.share_delivery_api.security.token.JsonAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//json으로 로그인 시도의 경우 처리 필터
public class JsonLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonLoginProcessingFilter(){
        super(new AntPathRequestMatcher("/api/auth/login")); // 필터 동작 조건 1
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        if(!isJson(request)){ // 필터 동작 조건 2
            throw new IllegalStateException("Authentication is not supported");
        }

        // 두 조건 모두 통과 시 id/verificationCode 통해 인증
        loginDTO loginDTO = objectMapper.readValue(request.getReader(), loginDTO.class);//json 바디 가져와서 dto로 가공

        //빈 문자열 체크
        if(ObjectUtils.isEmpty(loginDTO.getPhoneNumber()) || ObjectUtils.isEmpty(loginDTO.getVerificationCode()) || ObjectUtils.isEmpty(loginDTO.getFcmToken())){
            throw new IllegalArgumentException("phoneNumber or VerificationCode or FcmToken is empty");
        }

        // 토큰 만들고 AuthenticationManager에 위임하여 인증 처리 진행
        JsonAuthenticationToken jsonAuthenticationToken = new JsonAuthenticationToken(loginDTO.getPhoneNumber(), loginDTO.getVerificationCode(), loginDTO.getFcmToken());

        return getAuthenticationManager().authenticate(jsonAuthenticationToken);
    }

    private boolean isJson(HttpServletRequest request) {
        return request.getHeader("Content-Type").startsWith("application/json");
    }
}
