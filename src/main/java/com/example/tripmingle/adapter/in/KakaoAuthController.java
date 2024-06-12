package com.example.tripmingle.adapter.in;

import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.etc.TokenDTO;
import com.example.tripmingle.port.in.KakaoAuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.tripmingle.common.result.ResultCode.OAUTH_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoAuthController {

    private final KakaoAuthUseCase kakaoAuthUseCase;

    @PostMapping("/login")
    public ResponseEntity<ResultResponse> loginKakaoAccount(@RequestHeader("Kakao-Authentication") String kakaoAccessToken) {
        TokenDTO tokenDTO = kakaoAuthUseCase.loginKakaoAccount(kakaoAccessToken);
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.add("access-token", tokenDTO.getAccessToken());
        tokenHeaders.add("refresh-token", tokenDTO.getRefreshToken());
        return ResponseEntity.ok().headers(tokenHeaders).body(ResultResponse.of(OAUTH_SUCCESS));
    }

}
