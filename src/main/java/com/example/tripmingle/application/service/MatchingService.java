package com.example.tripmingle.application.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.tripmingle.dto.etc.DeleteUserPersonalityPublishDTO;
import com.example.tripmingle.dto.etc.MatchingBoardPublishDTO;
import com.example.tripmingle.dto.etc.UserPersonalityIdPublishDTO;
import com.example.tripmingle.dto.etc.UserPersonalityReCalculatePublishDTO;
import com.example.tripmingle.dto.req.matching.MatchingBoardReqDTO;
import com.example.tripmingle.entity.UserPersonality;
import com.example.tripmingle.port.out.CacheManagerPort;
import com.example.tripmingle.port.out.MatchingPort;
import com.example.tripmingle.port.out.PublishPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchingService {
	private final CacheManagerPort cacheManagerPort;
	private final PublishPort publishPort;
	private final MatchingPort matchingPort;

	private final RedisTemplate<String, Object> redisTemplate;
	private final String ALL_USER_PREFERENCES_KEY = "allUserPreferences";
	private final String USER_PREFERENCES_KEY = "userPreferences-";

	public List<Long> getSimilarUserIds(Long userPersonalityId) {
		return cacheManagerPort.getSimilarUsersByUserId(userPersonalityId);
	}

	public CompletableFuture<String> addUser(Long userPersonalityId, String messageId) {
		return publishPort.addUserPublish(UserPersonalityIdPublishDTO.builder()
			.userPersonalityId(userPersonalityId)
			.messageId(messageId)
			.build());
	}

	public CompletableFuture<String> prepareData(UserPersonality userPersonality, String messageId) {
		if (cacheManagerPort.getDeletedBit(userPersonality.getId())) {
			return publishPort.reCalculateUserPersonality(UserPersonalityReCalculatePublishDTO.builder()
				.userPersonalityId(userPersonality.getId())
				.messageId(messageId).build());
		}

		return CompletableFuture.completedFuture("Success");
	}

	public CompletableFuture<String> deleteUserPersonality(Long userPersonalityId, String messageId) {
		return publishPort.deleteUserPersonality(DeleteUserPersonalityPublishDTO.builder()
			.messageId(messageId)
			.userPersonalityId(userPersonalityId)
			.build());
	}

	public CompletableFuture<String> matchingBoard(String messageId, Long userId,
		MatchingBoardReqDTO matchingBoardReqDTO) {
		return publishPort.matchingBoard(MatchingBoardPublishDTO.builder()
			.messageId(messageId)
			.userId(userId)
			.countryName(matchingBoardReqDTO.getCountryName())
			.startDate(matchingBoardReqDTO.getStartDate())
			.endDate(matchingBoardReqDTO.getEndDate())
			.build());
	}

	public CompletableFuture<String> calculate(UserPersonality userPersonality, String messageId) {
		return publishPort.reCalculateUserPersonality(UserPersonalityReCalculatePublishDTO.builder()
			.userPersonalityId(userPersonality.getId())
			.messageId(messageId).build());

	}

	public List<Long> matchingBoard2(String messageId, Long userId, MatchingBoardReqDTO matchingBoardReqDTO) {
		return matchingPort.matchingBoard(MatchingBoardPublishDTO.builder()
			.messageId(messageId)
			.userId(userId)
			.countryName(matchingBoardReqDTO.getCountryName())
			.startDate(matchingBoardReqDTO.getStartDate())
			.endDate(matchingBoardReqDTO.getEndDate())
			.build());
	}
}
