package com.example.tripmingle.adapter.out;

import com.example.tripmingle.dto.etc.UserPersonalityIdPublishDTO;
import com.example.tripmingle.port.out.PublishPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class MessagePublisher implements PublishPort {
    private final RedisTemplate<String, Object> redisTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String ADD_USER_PUBLISH = "pubsub:addUser";

    public MessagePublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    private ConcurrentMap<String, CompletableFuture<String>> responseFutures = new ConcurrentHashMap<>();


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

    public void completeResponse(String messageId, String response) {
        CompletableFuture<String> future = responseFutures.remove(messageId);
        if (future != null) {
            future.complete(response);
        }
    }

    public void exceptionResponse(String messageId, Exception e) {
        CompletableFuture<String> future = responseFutures.remove(messageId);
        if (future != null) {
            future.completeExceptionally(e);
        }
    }
}
