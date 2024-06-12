package com.example.tripmingle.dto.etc;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoUserInfo {

    private String kakaoId;
    private String ageRange;
    private String birthday;
    private String email;
    private String gender;
    private String birthyear;
    private String nickName;
    private String name;
    private String phoneNumber;

}
