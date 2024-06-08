package com.example.tripmingle.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //Example
    EXAMPLE(400, "EX001", "예시 에러코드입니다.");

    final private int status;
    final private String ErrorCode;
    final private String message;
}
