package com.kitcd.share_delivery_api.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomLoginAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException {

        log.warn("UnAuthentication Access : " + req.getRemoteHost());
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorized");

    }

}
