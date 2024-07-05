package com.example.tripmingle.adapter.out;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MatchingAdapter implements MatchingPort {

	private final RedisTemplate<String, Object> redisTemplate;
	public static final Long EQUAL_VALUE = -1L;
	private static final String USER_PREFERENCES_KEY = "userPreferences-";
	private static final String MAX_USER_COUNT = "maxUserCount-"; //당시 가장 큰 id
	private static final String DELETED_BIT = "deletedBit-"; //이후 지워진게 있는지
	private static final String CURRENT_MAX_USER_COUNT = "currentMaxUserCount"; //현재 가장 큰 id
	ObjectMapper mapper = new ObjectMapper();

	@PostConstruct
	void init() {
		SimpleModule module = new SimpleModule();
		module.addSerializer((Class<Pair<Long, Double>>)(Class<?>)Pair.class, new PairSerializer());
		module.addDeserializer((Class<Pair<Long, Double>>)(Class<?>)Pair.class, new PairDeserializer());
		mapper.registerModule(module);

	}

	@Override
	public List<Long> getSimilarUsersByUserId(Long userId) {
		try {
			String redisKey = USER_PREFERENCES_KEY + userId;
			System.out.println(redisKey);
			Object json = redisTemplate.opsForValue().get(redisKey);
			System.out.println(json);
			if (json != null) {

				log.info("Retrieved JSON for user " + userId + ": " + json);
				String value = json.toString();
				List<Pair<Long, Double>> result = mapper.readValue(value,
					new TypeReference<List<Pair<Long, Double>>>() {
					});
				return result.stream().map(Pair::getLeft).collect(Collectors.toList());

			} else {
				throw new UserPersonalityNotFoundException("user personality not found",
					ErrorCode.USER_PERSONALITY_NOT_FOUND);
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

	@Override
	public boolean getDeletedBit(Long userId) {
		try {
			String key = DELETED_BIT + userId;
			Object value = redisTemplate.opsForValue().get(key);
			if (value == null) {
				throw new UserPersonalityNotFoundException("user personality not found",
					ErrorCode.USER_PERSONALITY_NOT_FOUND);
			}

			String valueStr = value.toString();
			return !valueStr.equals("0");

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

	//매직넘버 어떻게할지
	@Override
	public Long compareMaxCount(Long userId) {
		try {

			int currentMax = (int)redisTemplate.opsForValue().get(CURRENT_MAX_USER_COUNT);
			int userMax = (int)redisTemplate.opsForValue().get(MAX_USER_COUNT + userId);

			return currentMax == userMax ? -1L : userMax;

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
