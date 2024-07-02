package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.JsonParsingException;
import com.example.tripmingle.common.exception.RedisConnectException;
import com.example.tripmingle.common.exception.UserPersonalityNotFoundException;
import com.example.tripmingle.common.utils.PairDeserializer;
import com.example.tripmingle.common.utils.PairSerializer;
import com.example.tripmingle.port.out.MatchingPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.lettuce.core.RedisConnectionException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class MatchingAdapter implements MatchingPort {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String ALL_USER_PREFERENCES_KEY = "allUserPreferences";
    private final String USER_PREFERENCES_KEY = "userPreferences-";

    @Override
    public List<Long> getSimilarUsersByUserId(Long userId) {
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
                throw new UserPersonalityNotFoundException("user personality not found", ErrorCode.USER_PERSONALITY_NOT_FOUND);
            }
        } catch (JsonProcessingException e) {
            // JSON 처리와 관련된 예외
            e.printStackTrace();
            throw new JsonParsingException("json parsing exception", ErrorCode.JSON_PARSE_EXCEPTION);
        } catch (RedisConnectionException e) {
            // Redis 연결과 관련된 예외
            e.printStackTrace();
            throw new RedisConnectException("redis connect exception", ErrorCode.REDIS_CONNECT_EXCEPTION);
        } catch (Exception e) {
            // 그 외 일반 예외
            e.printStackTrace();
            throw new RuntimeException("can't load my similar users");
        }
    }

}
