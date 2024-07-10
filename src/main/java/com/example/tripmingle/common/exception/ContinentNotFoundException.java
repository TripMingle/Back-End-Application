package com.example.tripmingle.common.exception;

import com.example.tripmingle.common.error.ErrorCode;

import lombok.Getter;

@Getter
public class ContinentNotFoundException extends RuntimeException {
	private final ErrorCode errorCode;

	public ContinentNotFoundException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}
