package com.example.tripmingle.port.out;

import com.example.tripmingle.dto.res.KakaoLoginResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-login", url = "${oauth2.kakao.infoUrl}")
public interface KakaoLoginFeignClientPort {

    @GetMapping("/v2/user/m2")
    KakaoLoginResDTO getKakaoUserInfo(@RequestHeader("Authorization") String accessToken);

}
