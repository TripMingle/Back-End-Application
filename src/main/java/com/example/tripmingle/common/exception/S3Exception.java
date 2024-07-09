package com.example.tripmingle.common.exception;

import com.example.tripmingle.common.error.ErrorCode;

import lombok.Getter;

@Getter
public class S3Exception extends RuntimeException {

	private final ErrorCode errorCode;

	public S3Exception(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

}

