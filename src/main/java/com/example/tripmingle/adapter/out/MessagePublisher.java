package com.example.tripmingle.adapter.out;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.tripmingle.dto.etc.DeleteUserPersonalityPublishDTO;
import com.example.tripmingle.dto.etc.MatchingBoardPublishDTO;
import com.example.tripmingle.dto.etc.UserPersonalityIdPublishDTO;
import com.example.tripmingle.dto.etc.UserPersonalityReCalculatePublishDTO;
import com.example.tripmingle.port.out.PublishPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessagePublisher implements PublishPort {
	private final RedisTemplate<String, Object> redisTemplate;

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final String ADD_USER_PUBLISH = "pubsub:addUser";
	public static final String RE_CALCULATE_USER_PUBLISH = "pubsub:reCalculateUser";
	public static final String DELETE_USER_PUBLISH = "pubsub:deleteUser";
	public static final String MATCHING_USER = "pubsub:matching";

	public MessagePublisher(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public static ConcurrentMap<String, CompletableFuture<String>> responseFutures = new ConcurrentHashMap<>();

	@PostConstruct
	private void init() {
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Async
	public CompletableFuture<String> addUserPublish(UserPersonalityIdPublishDTO userPersonalityIdPublishDTO) {
		CompletableFuture<String> future = new CompletableFuture<>();
		responseFutures.put(userPersonalityIdPublishDTO.getMessageId(), future);

		try {
			// JSON 객체 생성
			String jsonMessage = objectMapper.writeValueAsString(userPersonalityIdPublishDTO);

			// JSON 메시지를 Redis에 발행
			redisTemplate.convertAndSend(ADD_USER_PUBLISH, jsonMessage);
			log.info("Published message: " + jsonMessage + " to topic: " + ADD_USER_PUBLISH);

		} catch (Exception e) {
			future.completeExceptionally(e);
			e.printStackTrace();
		}
		return future;
	}

	@Override
	public CompletableFuture<String> reCalculateUserPersonality(
		UserPersonalityReCalculatePublishDTO userPersonalityReCalculatePublishDTO) {
		CompletableFuture<String> future = new CompletableFuture<>();
		responseFutures.put(userPersonalityReCalculatePublishDTO.getMessageId(), future);
		try {
			// JSON 객체 생성
			String jsonMessage = objectMapper.writeValueAsString(userPersonalityReCalculatePublishDTO);

			// JSON 메시지를 Redis에 발행
			redisTemplate.convertAndSend(RE_CALCULATE_USER_PUBLISH, jsonMessage);
			log.info("Published message: " + jsonMessage + " to topic: " + RE_CALCULATE_USER_PUBLISH);
		} catch (Exception e) {
			future.completeExceptionally(e);
			e.printStackTrace();
		}
		return future;

	}

	@Override
	public CompletableFuture<String> deleteUserPersonality(
		DeleteUserPersonalityPublishDTO deleteUserPersonalityPublishDTO) {
		CompletableFuture<String> future = new CompletableFuture<>();
		responseFutures.put(deleteUserPersonalityPublishDTO.getMessageId(), future);
		try {
			String jsonMessage = objectMapper.writeValueAsString(deleteUserPersonalityPublishDTO);

			redisTemplate.convertAndSend(DELETE_USER_PUBLISH, jsonMessage);
			log.info("Published message: " + jsonMessage + " to topic: " + DELETE_USER_PUBLISH);
		} catch (Exception e) {
			future.completeExceptionally(e);
			e.printStackTrace();
		}

		return future;
	}

	@Override
	public CompletableFuture<String> matchingBoard(MatchingBoardPublishDTO matchingBoardPublishDTO) {
		CompletableFuture<String> future = new CompletableFuture<>();
		responseFutures.put(matchingBoardPublishDTO.getMessageId(), future);
		try {
			String jsonMessage = objectMapper.writeValueAsString(matchingBoardPublishDTO);

			redisTemplate.convertAndSend(MATCHING_USER, jsonMessage);
			log.info("Published message: " + jsonMessage + " to topic: " + MATCHING_USER);
		} catch (Exception e) {
			future.completeExceptionally(e);
			e.printStackTrace();
		}

		return future;

	}

	public static void completeResponse(String messageId, String response) {
		CompletableFuture<String> future = responseFutures.remove(messageId);
		if (future != null) {
			future.complete(response);
		}
	}

	public static void completeMatchingResponse(String messageId, String result, String boardId) {
		CompletableFuture<String> future = responseFutures.remove(messageId);
		if (future != null) {
			future.complete(boardId);
		}
	}
}
