package com.example.tripmingle.common.config.security;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.tripmingle.common.config.security.filter.CustomLogoutFilter;
import com.example.tripmingle.common.config.security.filter.JwtFilter;
import com.example.tripmingle.common.utils.JwtUtils;
import com.example.tripmingle.repository.RefreshRepository;
import com.example.tripmingle.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;
	private final JwtUtils jwtUtils;
	private final RefreshRepository refreshRepository;
	private final ObjectMapper objectMapper;
	private final UserRepository userRepository;

	@Value("${cors.allowed-origins}")
	private String allowedOrigins;

	@Value("${cors.allowed-remote-origins}")
	private String allowedRemoteOrigins;

	private static final String[] AUTH_WHITELIST = {
		"/swagger-ui/**", "swagger-ui.html/**", "/v3/**", "/country/**", "/continent/**"
	};

	@Autowired
	public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtils jwtUtils,
		RefreshRepository refreshRepository, ObjectMapper objectMapper, UserRepository userRepository) {
		this.authenticationConfiguration = authenticationConfiguration;
		this.jwtUtils = jwtUtils;
		this.refreshRepository = refreshRepository;
		this.objectMapper = objectMapper;
		this.userRepository = userRepository;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors
				.configurationSource(new CorsConfigurationSource() {
					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						CorsConfiguration configuration = new CorsConfiguration();

						configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
						configuration.setAllowedOrigins(Collections.singletonList(allowedRemoteOrigins));

						configuration.setAllowedMethods(Collections.singletonList("*"));
						configuration.setAllowCredentials(true);
						configuration.setAllowedHeaders(Collections.singletonList("*"));
						configuration.setMaxAge(3600L);

						configuration.setExposedHeaders(List.of("Authorization", "access-token", "refresh-token"));
						return configuration;
					}
				}))
			.csrf(auth -> auth.disable())
			.formLogin(auth -> auth.disable())
			.httpBasic(auth -> auth.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(AUTH_WHITELIST).permitAll()
				.requestMatchers("/kakao/**", "/auth/**").permitAll()
				.anyRequest().authenticated())
			.addFilterBefore(new JwtFilter(jwtUtils, refreshRepository), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new CustomLogoutFilter(jwtUtils, refreshRepository), JwtFilter.class)
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

}
