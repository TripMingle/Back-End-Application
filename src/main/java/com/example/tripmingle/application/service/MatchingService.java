package com.example.tripmingle.application.service;

import com.example.tripmingle.dto.etc.UserPersonalityIdPublishDTO;
import com.example.tripmingle.port.out.MatchingPort;
import com.example.tripmingle.port.out.PublishPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingPort matchingPort;
    private final PublishPort publishPort;

    private final RedisTemplate<String, Object> redisTemplate;
    private final String ALL_USER_PREFERENCES_KEY = "allUserPreferences";
    private final String USER_PREFERENCES_KEY = "userPreferences-";

    public List<Long> getSimilarUserIds(Long userId){
        return matchingPort.getSimilarUsersByUserId(userId);
    }
    public CompletableFuture<String> addUser(Long userPersonalityId, String messageId) {
        return publishPort.addUserPublish(UserPersonalityIdPublishDTO.builder()
                .userPersonalityId(userPersonalityId)
                .messageId(messageId)
                .build());
    }
}
