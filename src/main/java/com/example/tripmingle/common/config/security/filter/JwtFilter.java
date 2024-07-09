package com.example.tripmingle.common.config.security.filter;

import static com.example.tripmingle.common.constants.JwtConstants.*;
import static jakarta.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpHeaders.*;

import java.io.IOException;
import java.io.PrintWriter;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.LogoutUserException;
import com.example.tripmingle.entity.Refresh;
import com.example.tripmingle.repository.RefreshRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.tripmingle.common.config.security.customuser.CustomUserDetails;
import com.example.tripmingle.common.utils.JwtUtils;
import com.example.tripmingle.entity.User;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;
	private final RefreshRepository refreshRepository;

	public JwtFilter(JwtUtils jwtUtils, RefreshRepository refreshRepository) {
		this.jwtUtils = jwtUtils;
		this.refreshRepository = refreshRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String accessToken = "";
		if (request.getHeader(AUTHORIZATION) != null) {
			accessToken = request.getHeader(AUTHORIZATION).substring(7);
		} else {
			filterChain.doFilter(request, response);
			return;
		}

		isExpiredAccessToken(response, accessToken);
		isAccessToken(response, accessToken);

		String email = jwtUtils.getEmail(accessToken);
		String role = jwtUtils.getRole(accessToken);
		String loginType = jwtUtils.getLoginType(accessToken);

		logoutUser(email);

		User user = User.builder()
			.email(email)
			.role(role)
			.loginType(loginType)
			.build();
		CustomUserDetails customUserDetails = new CustomUserDetails(user);

		Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
			customUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
		filterChain.doFilter(request, response);
	}

	private void isExpiredAccessToken(HttpServletResponse response, String accessToken) throws IOException {
		if (jwtUtils.isExpired(accessToken)) {
			PrintWriter writer = response.getWriter();
			writer.print("access token expired");
			response.setStatus(SC_UNAUTHORIZED);
			throw new ExpiredJwtException(null, null, "Access token expired");
		}
	}

	private void isAccessToken(HttpServletResponse response, String accessToken) throws IOException {
		String tokenType = jwtUtils.getTokenType(accessToken);
		if (!tokenType.equals(ACCESS_TOKEN.getMessage())) {
			PrintWriter writer = response.getWriter();
			writer.print("Invalid Access Token");
			response.setStatus(SC_UNAUTHORIZED);

			// TODO 커스텀 에러 적용하기
			throw new IllegalArgumentException("잘못된 타입의 토큰 입니다.");
		}
	}

	private void logoutUser(String email) {
		Refresh refresh = refreshRepository.findByEmail(email).orElseThrow(() -> new LogoutUserException("Logout User", ErrorCode.LOGOUT_USER));
	}
}
