package com.kitcd.share_delivery_api.security.provider;

import com.kitcd.share_delivery_api.domain.redis.auth.VerificationType;
import com.kitcd.share_delivery_api.security.service.AccountContext;
import com.kitcd.share_delivery_api.security.token.JsonAuthenticationToken;
import com.kitcd.share_delivery_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


public class JsonAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String phoneNumber = authentication.getName(); //loginId == phoneNumber
        String verificationCode = (String)authentication.getCredentials();


        //휴대폰 번호 검증
        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(phoneNumber);

        //인증코드 검증
        if(!authService.verifyCode(phoneNumber, verificationCode, VerificationType.LOGIN)){
            throw new BadCredentialsException("Invalid Verification Code");
        }

        /* 여기서 추가 검증 절차 진행 가능 */

        // 인증 토큰 생성 반환 (Token은 Auth의 Child)
        return new JsonAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JsonAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
