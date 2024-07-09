package com.example.tripmingle.common.config.security.filter;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.TokenNotFoundException;
import com.example.tripmingle.common.utils.JwtUtils;
import com.example.tripmingle.entity.Refresh;
import com.example.tripmingle.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static com.example.tripmingle.common.constants.JwtConstants.REFRESH_TOKEN;
import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@Slf4j
@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtUtils jwtUtils;
    private final RefreshRepository refreshRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, filterChain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("/auth/logout")) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        String refresh = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(REFRESH_TOKEN.getMessage())) {
                    refresh = cookie.getValue();
                }
            }
        }

        if (request.getHeader("Authorization") == null) {
            throw new TokenNotFoundException("Token Not Found", ErrorCode.TOKEN_NOT_FOUND);
        }
        String accessToken = jwtUtils.parsingAccessToken(request.getHeader("Authorization"));
        String tokenEmail = jwtUtils.getEmail(accessToken);
        Refresh refreshEntity = refreshRepository.findByEmail(tokenEmail).orElseThrow(() -> new TokenNotFoundException("Token Not Found", ErrorCode.TOKEN_NOT_FOUND));
        if (refresh == null) {
            refresh = refreshEntity.getRefreshToken();
        }


        try {
            jwtUtils.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            response.setStatus(SC_BAD_REQUEST);
            return;
        }

        String category = jwtUtils.getTokenType(refresh);
        if (!category.equals(REFRESH_TOKEN.getMessage())) {
            response.setStatus(SC_BAD_REQUEST);
            return;
        }

        Boolean isExistRefresh = refreshRepository.existsByRefreshToken(refresh);
        if (!isExistRefresh) {
            response.setStatus(SC_BAD_REQUEST);
            return;
        }

        refreshRepository.delete(refreshEntity);

        Cookie cookie = new Cookie(REFRESH_TOKEN.getMessage(), null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
