package com.example.tripmingle.dto.etc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfo {

    @JsonProperty("age_range")
    private String ageRange;

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("email")
    private String email;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("birthyear")
    private String birthyear;

    private String nickName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone_number")
    private String phoneNumber;

}
