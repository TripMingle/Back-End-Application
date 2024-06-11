package com.example.tripmingle.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "refreshToken", timeToLive = 86400000L)
@Getter
public class Refresh {

    @Id
    private final String email;

    @Indexed
    private final String refreshToken;

    private final String expiration;

    @Builder
    public Refresh(String email, String refreshToken, String expiration) {
        this.email = email;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}
