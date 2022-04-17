package com.kitcd.share_delivery_api.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException e) throws IOException, ServletException {

        res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access is denied");

    }
}
