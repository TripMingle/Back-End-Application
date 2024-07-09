package com.example.tripmingle.entity;

import lombok.Getter;

@Getter
public enum ChatRoomType {

    ONE_ON_ONE("one_on_one"), GROUP("group");

    private final String chatRoomType;

    ChatRoomType(String chatRoomType) {
        this.chatRoomType = chatRoomType;
    }

}
