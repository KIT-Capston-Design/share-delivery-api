package com.kitcd.share_delivery_api.security.filter;

import com.kitcd.share_delivery_api.security.jwt.JWTFactory;
import com.kitcd.share_delivery_api.security.token.JWTAuthenticationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//OncePerRequestFilter : 한 요청에 대해 한 번만 처리 되는 것을 보장하는 필터 클래스

@AllArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(AUTHORIZATION_HEADER);

        if(StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)){
            try {
                Authentication jwtAuthenticationToken = new JWTAuthenticationToken(token.substring(TOKEN_PREFIX.length())); // prefix 'Bearer ' 제거 후 인증 토큰 생성
                Authentication authentication = authenticationManager.authenticate(jwtAuthenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (AuthenticationException authenticationException) {
                SecurityContextHolder.clearContext();

            }
        }

        filterChain.doFilter(request, response);
    }

}

