package com.example.tripmingle.common.result;

import lombok.Getter;

//@Schema(description = "결과 응답 데이터 모델")
@Getter
public class ResultResponse {
    //@Schema(description = "Business 상태 코드")
    private final String code;
    //@Schema(description = "응답 메세지")
    private final String message;
    //@Schema(description = "응답 데이터")
    private final Object data;

    public ResultResponse(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }


    public static ResultResponse of(ResultCode resultCode, Object data) {
        return new ResultResponse(resultCode, data);
    }

    public static ResultResponse of(ResultCode resultCode) {
        return new ResultResponse(resultCode, "");
    }
}
