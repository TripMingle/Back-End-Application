package com.example.tripmingle.common.config.security.filter;

import com.example.tripmingle.common.config.security.customuser.CustomUserDetails;
import com.example.tripmingle.common.utils.JwtUtils;
import com.example.tripmingle.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

import static com.example.tripmingle.common.constants.JwtConstants.ACCESS_TOKEN;
import static jakarta.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(AUTHORIZATION);

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        isExpiredAccessToken(response, accessToken);
        isAccessToken(response, accessToken);

        String email = jwtUtils.getEmail(accessToken);
        String role = jwtUtils.getRole(accessToken);
        String loginType = jwtUtils.getLoginType(accessToken);

        User user = User.builder()
                .email(email)
                .role(role)
                .loginType(loginType)
                .build();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
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
}
