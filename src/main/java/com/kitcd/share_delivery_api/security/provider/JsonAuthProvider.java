package com.kitcd.share_delivery_api.security.provider;

import com.kitcd.share_delivery_api.security.service.AccountContext;
import com.kitcd.share_delivery_api.security.token.JsonAuthToken;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


public class JsonAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String loginId = authentication.getName(); //loginId == phoneNumber
        String password = (String)authentication.getCredentials();


        //id 검증
        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(loginId);

        //pw 검증
        if(!passwordEncoder.matches(password, accountContext.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

        /* 여기서 추가 검증 절차 진행 가능 */

        // 인증 토큰 생성 반환 (Token은 Auth의 Child)
        return new JsonAuthToken(accountContext.getAccount(), null, accountContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JsonAuthToken.class.isAssignableFrom(authentication);
    }
}
