package com.example.tripmingle.common.utils;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${spring.jwt.access-token-expiration}")
    private Long accessTokenExpTime;

    @Value("${spring.jwt.refresh-token-expiration}")
    private Long refreshTokenExpTime;

    private SecretKey secretKey;

    public JwtUtils(@Value("${spring.jwt.secret}") String secretKey) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public String getLoginType(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("loginType", String.class);
    }

    public String getTokenType(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("tokenType", String.class);
    }

    public Long getAccessTokenExpTime() {
        return this.accessTokenExpTime;
    }

    public Long getRefreshTokenExpTime() {
        return this.refreshTokenExpTime;
    }

    public boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createJwtToken(String email, String role, String loginType, String tokenType, Long expiredMs) {
        return Jwts.builder()
                .claim("email", email)
                .claim("role", role)
                .claim("loginType", loginType)
                .claim("tokenType", tokenType)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

}
