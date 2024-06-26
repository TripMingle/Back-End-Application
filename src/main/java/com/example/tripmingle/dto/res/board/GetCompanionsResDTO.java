package com.example.tripmingle.dto.res.board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetCompanionsResDTO {
    private Long userId;
    private String nickName;
    private String ageRange;
    private String gender;
    private String nationality;
    private String selfIntroduction;
    private boolean isLeader;
}
