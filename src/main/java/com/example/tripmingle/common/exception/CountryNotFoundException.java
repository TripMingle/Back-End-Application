package com.example.tripmingle.common.exception;

import com.example.tripmingle.common.error.ErrorCode;

import lombok.Getter;

@Getter
public class CountryNotFoundException extends RuntimeException {

	private final ErrorCode errorCode;

	public CountryNotFoundException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

}