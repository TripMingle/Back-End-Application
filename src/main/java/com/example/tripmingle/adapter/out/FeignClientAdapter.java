package com.example.tripmingle.adapter.out;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.example.tripmingle.client.MatchingClient;
import com.example.tripmingle.dto.etc.UserPersonalityIdPublishDTO;
import com.example.tripmingle.port.out.FeignClientPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeignClientAdapter implements FeignClientPort {
	private final MatchingClient matchingClient;

	@Override
	public CompletableFuture<String> addUser(UserPersonalityIdPublishDTO userPersonalityIdPublishDTO) {
		return matchingClient.addUser(userPersonalityIdPublishDTO);
	}

	public String test() {
		return matchingClient.test();
	}
}
