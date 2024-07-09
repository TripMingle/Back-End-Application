package com.example.tripmingle.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

import static com.example.tripmingle.common.constants.JwtConstants.ACCESS_TOKEN;
import static com.example.tripmingle.common.constants.JwtConstants.REFRESH_TOKEN;

@Component
public class JwtUtils {

	@Value("${spring.jwt.access-token-expiration}")
	private Long accessTokenExpTime;

	@Value("${spring.jwt.refresh-token-expiration}")
	private Long refreshTokenExpTime;

	private SecretKey secretKey;

	public JwtUtils(@Value("${spring.jwt.secret}") String secretKey) {
		this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	public String getEmail(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("email", String.class);
	}

	public String getRole(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("role", String.class);
	}

	public String getLoginType(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("loginType", String.class);
	}

	public String getTokenType(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("tokenType", String.class);
	}

	public Long getRefreshTokenExpTimeByToken(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getExpiration()
			.getTime();
	}

	public Long getAccessTokenExpTime() {
		return this.accessTokenExpTime;
	}

	public Long getRefreshTokenExpTime() {
		return this.refreshTokenExpTime;
	}

	public Boolean isExpired(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getExpiration()
			.before(new Date());
	}

	public String parsingAccessToken(String token) {
		return token.substring(7);
	}

	public String createJwtAccessToken(String email, String role, String loginType) {
		return "Bearer " + Jwts.builder()
			.claim("email", email)
			.claim("role", role)
			.claim("loginType", loginType)
			.claim("tokenType", ACCESS_TOKEN.getMessage())
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + getAccessTokenExpTime()))
			.signWith(secretKey)
			.compact();
	}

	public String createJwtRefreshToken(String email, String role, String loginType) {
		return Jwts.builder()
				.claim("email", email)
				.claim("role", role)
				.claim("loginType", loginType)
				.claim("tokenType", REFRESH_TOKEN.getMessage())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + getRefreshTokenExpTime()))
				.signWith(secretKey)
				.compact();
	}

}
