package com.example.tripmingle.common.constants;

import lombok.Getter;

@Getter
public enum LoginType {

    KAKAO("kakao");

    private String loginType;

    LoginType(String loginType) {
        this.loginType = loginType;
    }
}
