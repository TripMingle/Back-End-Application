package com.example.tripmingle.adapter.out;

import com.example.tripmingle.dto.etc.UserPersonalityIdPublishDTO;
import com.example.tripmingle.port.out.PublishPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher implements PublishPort {
    private final RedisTemplate<String, Object> redisTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String ADD_USER_PUBLISH = "pubsub:addUser";

    public MessagePublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addUserPublish(UserPersonalityIdPublishDTO userPersonalityIdPublishDTO) {
        try {
            // JSON 객체 생성
            String jsonMessage = objectMapper.writeValueAsString(userPersonalityIdPublishDTO);


            // JSON 메시지를 Redis에 발행
            redisTemplate.convertAndSend(ADD_USER_PUBLISH, jsonMessage);
            System.out.println("Published message: " + jsonMessage + " to topic: " + ADD_USER_PUBLISH);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
