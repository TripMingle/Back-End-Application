package com.example.tripmingle.client;

import java.util.concurrent.CompletableFuture;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.tripmingle.dto.etc.UserPersonalityIdPublishDTO;

@FeignClient(name = "matchingClient", url = "http://localhost:8081")
public interface MatchingClient {
	@PostMapping("/matching/user")
	CompletableFuture<String> addUser(@RequestBody UserPersonalityIdPublishDTO userPersonalityIdPublishDTO);

}
