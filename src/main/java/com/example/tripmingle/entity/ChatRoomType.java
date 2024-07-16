package com.example.tripmingle.entity;

import lombok.Getter;

@Getter
public enum ChatRoomType {

	ONE_ON_ONE("ONE_ON_ONE"), GROUP("GROUP");

	private final String chatRoomType;

	ChatRoomType(String chatRoomType) {
		this.chatRoomType = chatRoomType;
	}

}
