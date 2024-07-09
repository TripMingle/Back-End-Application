package com.example.tripmingle.dto.req.oauth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class KakaoUserDetailReqDTO {

    private String kakaoAccessToken;
    private String nickName;
    private String nationality;

}
