package com.kitcd.share_delivery_api.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitcd.share_delivery_api.dto.account.LoginDTO;
import com.kitcd.share_delivery_api.security.exception.InsufficientArgumentException;
import com.kitcd.share_delivery_api.security.exception.NotSupportedAuthException;
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

        if(!isJson(request.getHeader("Content-Type"))){ // 필터 동작 조건 2
            throw new NotSupportedAuthException("Authentication is not supported");
        }

        // 두 조건 모두 통과 시 id/verificationCode 통해 인증
        LoginDTO loginDTO = objectMapper.readValue(request.getReader(), LoginDTO.class);//json 바디 가져와서 dto로 가공

        //빈 문자열 체크 : 빈 알규먼트 존재 시 throw InsufficientArgumentException
        checkArgument(loginDTO);

        // 토큰 만들고 AuthenticationManager에 위임하여 인증 처리 진행
        JsonAuthenticationToken jsonAuthenticationToken = new JsonAuthenticationToken(loginDTO.getPhoneNumber(), loginDTO.getVerificationCode(), loginDTO.getFcmToken());

        return getAuthenticationManager().authenticate(jsonAuthenticationToken);
    }

    private boolean isJson(String  contentType) {
        if(contentType == null)
            return false;

        return contentType.startsWith("application/json");
    }

    private void checkArgument(LoginDTO loginDTO){

        StringBuilder sb = new StringBuilder();

        if(ObjectUtils.isEmpty(loginDTO.getPhoneNumber()))
            sb.append("PhoneNumber is empty  and ");
        if(ObjectUtils.isEmpty(loginDTO.getVerificationCode()))
            sb.append("Verification Code is empty and ");
        if(ObjectUtils.isEmpty(loginDTO.getFcmToken()))
            sb.append("FCM Token Code is empty and ");

        if(sb.length() != 0) {
            sb.setLength(sb.length() - 5); //" and "지우기
            throw new InsufficientArgumentException(sb.toString());
        }

    }
}
