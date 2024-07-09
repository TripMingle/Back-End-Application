package com.example.tripmingle.common.exception;

import com.example.tripmingle.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class TokenNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public TokenNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
