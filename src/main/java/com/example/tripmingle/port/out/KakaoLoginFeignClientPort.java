package com.example.tripmingle.port.out;

import com.example.tripmingle.dto.res.KakaoLoginResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-login", url = "${oauth2.kakao.infoUrl}")
public interface KakaoLoginFeignClientPort {

    @PostMapping("/v2/user/me")
    KakaoLoginResDTO getKakaoUserInfo(@RequestHeader("Authorization") String token);

}
