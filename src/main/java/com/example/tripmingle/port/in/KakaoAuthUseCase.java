package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.etc.TokenDTO;

public interface KakaoAuthUseCase {

    TokenDTO loginKakaoAccount(String kakaoAccessToken);

}
