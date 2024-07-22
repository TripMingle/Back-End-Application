package com.example.tripmingle.port.out;

import java.util.concurrent.CompletableFuture;

import com.example.tripmingle.dto.etc.UserPersonalityIdPublishDTO;

public interface FeignClientPort {
	CompletableFuture<String> addUser(UserPersonalityIdPublishDTO userPersonalityIdPublishDTO);
}
