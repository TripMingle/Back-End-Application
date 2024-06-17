package com.example.tripmingle.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserAdditionDetailsReqDTO {

    private String kakaoAccessToken;
    private String nickName;
    private String nationality;

}
