package com.kitcd.share_delivery_api.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.kitcd.share_delivery_api.domain.account.Account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.util.Date;


@Slf4j
@RequiredArgsConstructor
@Component
public class JWTFactory {

    private final int SECOND = 1000;
    private final int MINUTE = SECOND * 60;

    @Value("${jwt.issuer}") private final String issuer;
    @Value("${jwt.secret-key}") private final String secretKey;
    @Value("${jwt.refresh-secret-key}") private final String refreshSecretKey;
    @Value("${jwt.expiry-period}") private final long expiryPeriod;
    @Value("${jwt.refresh-expiry-period}") private final long refreshExpiryPeriod;

    public String createAccessToken(Account account){
        return createToken(account, secretKey, expiryPeriod);
    }

    public String createRefreshToken(Account account){
        return createToken(account, refreshSecretKey, refreshExpiryPeriod);
    }

    private String createToken(Account account, String key, long period) {

        String token = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(key);
            token = JWT.create() // Token Builder
                    .withIssuer(issuer)
                    .withClaim("phoneNumber", account.getPhoneNumber())
                    .withClaim("role", account.getRole().toString())
                    .withExpiresAt(createExpirationDate(period))
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            log.error(exception.getMessage());
        }

        log.info("created a token for " + account.getAccountId());

        return token;
    }

    private Date createExpirationDate(long period){
        Date current = new Date();
        return new Date(current.getTime() + period * MINUTE);
    }

}