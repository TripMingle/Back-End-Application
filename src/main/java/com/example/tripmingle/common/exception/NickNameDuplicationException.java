package com.example.tripmingle.common.exception;

import com.example.tripmingle.common.error.ErrorCode;

import lombok.Getter;

@Getter
public class NickNameDuplicationException extends RuntimeException {

	private final ErrorCode errorCode;

	public NickNameDuplicationException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}
