package com.example.tripmingle.client;

import java.util.concurrent.CompletableFuture;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.tripmingle.dto.etc.MatchingBoardPublishDTO;
import com.example.tripmingle.dto.etc.MatchingResDTO;
import com.example.tripmingle.dto.etc.UserPersonalityIdPublishDTO;

@FeignClient(name = "matchingClient", url = "http://localhost:8081")
public interface MatchingClient {
	@PostMapping("/matching/user")
	CompletableFuture<String> addUser(@RequestBody UserPersonalityIdPublishDTO userPersonalityIdPublishDTO);

	@PostMapping("/matching")
	ResponseEntity<MatchingResDTO> matchingBoard(@RequestBody MatchingBoardPublishDTO matchingBoardPublishDTO);
}
