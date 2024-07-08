package com.example.tripmingle.common.exception;

import com.example.tripmingle.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyExistsChatRoomUserException extends RuntimeException{

    private final ErrorCode errorCode;

    public AlreadyExistsChatRoomUserException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
