package com.example.tripmingle.adapter.out;

import java.util.ArrayList;
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
import com.example.tripmingle.dto.res.country.GetContinentsResDTO;
import com.example.tripmingle.dto.res.country.GetCountriesResDTO;
import com.example.tripmingle.port.out.CacheManagerPort;
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
public class CacheManagerAdapter implements CacheManagerPort {

	private final RedisTemplate<String, Object> redisTemplate;
	private static final String USER_PREFERENCES_KEY = "userPreferences-";
	private static final String DELETED_BIT = "deletedBit-"; //이후 지워진게 있는지
	private static final String CONTINENTS_KEY = "continents";
	private static final String COUNTRY_KEY = "country-";
	ObjectMapper mapper = new ObjectMapper();

	@PostConstruct
	void init() {
		SimpleModule module = new SimpleModule();
		module.addSerializer((Class<Pair<Long, Double>>)(Class<?>)Pair.class, new PairSerializer());
		module.addDeserializer((Class<Pair<Long, Double>>)(Class<?>)Pair.class, new PairDeserializer());
		mapper.registerModule(module);

	}

	@Override
	public List<Long> getSimilarUsersByUserId(Long userPersonalityId) {
		try {
			String redisKey = USER_PREFERENCES_KEY + userPersonalityId;
			Object json = redisTemplate.opsForValue().get(redisKey);
			if (json != null) {

				log.info("Retrieved JSON for user " + userPersonalityId + ": " + json);
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

	@Override
	public List<GetContinentsResDTO> getContinentsInCache() {
		List<GetContinentsResDTO> result = new ArrayList<>();
		try {
			result = mapper.convertValue(
				redisTemplate.opsForValue().get(CONTINENTS_KEY),
				new TypeReference<List<GetContinentsResDTO>>() {
				});
		} catch (RedisConnectionException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public void setContinentsAtCache(List<GetContinentsResDTO> getContinentsResDTOS) {
		redisTemplate.opsForValue().set(CONTINENTS_KEY, getContinentsResDTOS);
	}

	@Override
	public List<GetCountriesResDTO> getCountriesAtCache(String continent) {
		List<GetCountriesResDTO> result = new ArrayList<>();
		String continentName = setContinentName(continent);
		try {
			result = mapper.convertValue(
				redisTemplate.opsForValue().get(COUNTRY_KEY + continentName),
				new TypeReference<List<GetCountriesResDTO>>() {
				});
		} catch (RedisConnectionException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public void setCountriesAtCache(String continent, List<GetCountriesResDTO> getCountriesResDTOS) {
		String continentName = setContinentName(continent);
		redisTemplate.opsForValue().set(COUNTRY_KEY + continentName, getCountriesResDTOS);
	}

	private String setContinentName(String continent) {
		switch (continent.toLowerCase()) {
			case "아시아":
			case "asia":
				return "asia";
			case "유럽":
			case "europe":
				return "europe";
			case "북아메리카":
			case "north america":
				return "north america";
			case "남아메리카":
			case "south america":
				return "south america";
			case "아프리카":
			case "africa":
				return "africa";
			case "오세아니아":
			case "oceania":
				return "oceania";
			default:
				throw new IllegalArgumentException("Unknown continent: " + continent);
		}
	}

}
