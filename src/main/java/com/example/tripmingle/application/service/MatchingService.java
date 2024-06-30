package com.example.tripmingle.application.service;

import com.example.tripmingle.common.utils.PairDeserializer;
import com.example.tripmingle.common.utils.PairSerializer;
import com.example.tripmingle.port.out.MatchingPort;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingPort matchingPort;

    private final RedisTemplate<String, Object> redisTemplate;
    private final String ALL_USER_PREFERENCES_KEY = "allUserPreferences";
    private final String USER_PREFERENCES_KEY = "userPreferences-";

    public List<Long> getSimilarUserIds(Long userId){
        return matchingPort.getSimilarUsersByUserId(userId);
    }


    //@PostConstruct
    public void init() {
        System.out.println("PostConstruct method called");
        Long userId = 1L;
        List<Long> preferences = getUserPreferenceById(userId);
        System.out.println("User ID: " + userId + " preferences:");
        for (Long preference : preferences) {
            System.out.println("    Other User ID: " + preference);
        }
    }


    public List<Long> getUserPreferenceById(Long userId) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer((Class<Pair<Long, Double>>) (Class<?>) Pair.class, new PairSerializer());
            module.addDeserializer((Class<Pair<Long, Double>>) (Class<?>) Pair.class, new PairDeserializer());
            mapper.registerModule(module);

            String redisKey = USER_PREFERENCES_KEY + userId;
            String json = (String) redisTemplate.opsForValue().get(redisKey);
            if (json != null) {
                System.out.println("Retrieved JSON for user " + userId + ": " + json);  // JSON 데이터 로그 확인
                return mapper.readValue(json, new TypeReference<List<Long>>() {});
            } else {
                System.out.println("No data found in Redis for key: " + redisKey);
                return Collections.emptyList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //@PostConstruct
    public Map<Long, List<Pair<Long, Double>>> getALLUserPreferences() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer((Class<Pair<Long, Double>>) (Class<?>) Pair.class, new PairSerializer());
            module.addDeserializer((Class<Pair<Long, Double>>) (Class<?>) Pair.class, new PairDeserializer());
            mapper.registerModule(module);

            String json = (String) redisTemplate.opsForValue().get(ALL_USER_PREFERENCES_KEY);

            if (json != null) {
                System.out.println("Retrieved JSON: " + json);  // JSON 데이터 로그 확인
                printUserPreferences(mapper.readValue(json, new TypeReference<Map<Long, List<Pair<Long, Double>>>>() {}));
                return mapper.readValue(json, new TypeReference<Map<Long, List<Pair<Long, Double>>>>() {});
            } else {
                System.out.println("No data found in Redis for key: " + USER_PREFERENCES_KEY);
                return Collections.emptyMap();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }


    public static void printUserPreferences(Map<Long, List<Pair<Long, Double>>> userPreferences) {
        for (Map.Entry<Long, List<Pair<Long, Double>>> entry : userPreferences.entrySet()) {
            Long userId = entry.getKey();
            List<Pair<Long, Double>> preferences = entry.getValue();

            System.out.println("User ID: " + userId);
            for (Pair<Long, Double> preference : preferences) {
                System.out.println("    Other User ID: " + preference.getLeft() + ", Similarity: " + preference.getRight());
            }
        }
    }

}
