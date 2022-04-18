package com.kitcd.share_delivery_api.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitcd.share_delivery_api.domain.account.Account;

import com.kitcd.share_delivery_api.security.dto.JsonWebTokenDTO;
import com.kitcd.share_delivery_api.security.jwt.JsonWebTokenFactory;
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
    private JsonWebTokenFactory jsonWebTokenFactory;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {

        Account account = (Account) auth.getPrincipal(); //인증 객체로부터 인증 성공 시 저장한 Account 객체 받아오기
        res.setStatus(HttpStatus.OK.value());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);

        //json web token 생성
        JsonWebTokenDTO jwtDto = JsonWebTokenDTO.builder()
                        .accessToken(jsonWebTokenFactory.createAccessToken(account))
                        .refreshToken(jsonWebTokenFactory.createRefreshToken(account))
                .build();

        objectMapper.writeValue(res.getWriter(), jwtDto); // jwtDto 객체를 Json 형식으로 변환하여 클라이언트에게 전달.

    }

}
