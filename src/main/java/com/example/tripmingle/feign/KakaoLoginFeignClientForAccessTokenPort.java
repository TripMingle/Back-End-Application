package com.example.tripmingle.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tripmingle.dto.res.oauth.KakaoTokenResDTO;

@FeignClient(name = "kakao-token", url = "${oauth2.kakao.baseUrl}")
public interface KakaoLoginFeignClientForAccessTokenPort {

	@GetMapping("/oauth/token")
	KakaoTokenResDTO getToken(@RequestParam("grant_type") String type,
		@RequestParam("client_id") String clientId,
		@RequestParam("client_secret") String clientSecret,
		@RequestParam("code") String code);

}
