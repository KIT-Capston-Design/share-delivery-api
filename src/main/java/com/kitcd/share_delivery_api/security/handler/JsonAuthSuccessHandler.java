package com.kitcd.share_delivery_api.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;

import com.kitcd.share_delivery_api.domain.redis.auth.loggedoninf.LoggedOnInformation;
import com.kitcd.share_delivery_api.security.dto.JWTDTO;
import com.kitcd.share_delivery_api.security.jwt.JWTFactory;
import com.kitcd.share_delivery_api.security.token.JsonAuthenticationToken;
import com.kitcd.share_delivery_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JWTFactory jwtFactory;

    @Autowired
    private AuthService authService;

    private final ObjectMapper objectMapper = new ObjectMapper();



    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {

        Account account = (Account) auth.getPrincipal(); //인증 객체로부터 인증 성공 시 저장한 Account 객체 받아오기
        String fcmToken = ((JsonAuthenticationToken)auth).getFcmToken(); //fcm 토큰 받아오기


        //json web token 생성
        JWTDTO jwtDto = JWTDTO.builder()
                        .accountId(account.getAccountId())
                        .accessToken(jwtFactory.createAccessToken(account))
                        .refreshToken(jwtFactory.createRefreshToken(account))
                .build();

        // redis에 저장할 logon 정보 작성 후 레디스 저장
        LoggedOnInformation loggedOnInf = LoggedOnInformation.builder()
                .accountId(account.getAccountId())
                .phoneNumber(account.getPhoneNumber())
                .refreshToken(jwtDto.getRefreshToken())
                .fcmToken(fcmToken)
                .build();

        authService.saveLoggedOnInformation(loggedOnInf);

        res.setStatus(HttpStatus.OK.value());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(res.getWriter(), jwtDto); // jwtDto 객체를 Json 형식으로 변환하여 클라이언트에게 전달.

    }

}
