package com.example.tripmingle.application.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.tripmingle.adapter.out.MatchingAdapter;
import com.example.tripmingle.dto.etc.DeleteUserPersonalityPublishDTO;
import com.example.tripmingle.dto.etc.UserPersonalityIdPublishDTO;
import com.example.tripmingle.dto.etc.UserPersonalityReCalculatePublishDTO;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserPersonality;
import com.example.tripmingle.port.out.MatchingPort;
import com.example.tripmingle.port.out.PublishPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchingService {
	private final MatchingPort matchingPort;
	private final PublishPort publishPort;

	private final RedisTemplate<String, Object> redisTemplate;
	private final String ALL_USER_PREFERENCES_KEY = "allUserPreferences";
	private final String USER_PREFERENCES_KEY = "userPreferences-";

	public List<Long> getSimilarUserIds(Long userId) {
		return matchingPort.getSimilarUsersByUserId(userId);
	}

	public CompletableFuture<String> addUser(Long userPersonalityId, String messageId) {
		return publishPort.addUserPublish(UserPersonalityIdPublishDTO.builder()
			.userPersonalityId(userPersonalityId)
			.messageId(messageId)
			.build());
	}

	public CompletableFuture<String> prepareData(User currentUser, UserPersonality userPersonality, String messageId) {
		if (matchingPort.getDeletedBit(currentUser.getId())) {
			return publishPort.reCalculateUserPersonality(UserPersonalityReCalculatePublishDTO.builder()
				.userPersonalityId(userPersonality.getId())
				.minUserPersonalityId(-1L)
				.messageId(messageId).build());
		} else {
			Long result = matchingPort.compareMaxCount(currentUser.getId());
			if (result != MatchingAdapter.EQUAL_VALUE) {
				return publishPort.reCalculateUserPersonality(UserPersonalityReCalculatePublishDTO.builder()
					.userPersonalityId(userPersonality.getId())
					.minUserPersonalityId(result)
					.messageId(messageId).build());
			} else {
				return CompletableFuture.completedFuture("Success");
			}
		}
	}

	public CompletableFuture<String> deleteUserPersonality(Long userPersonalityId, String messageId) {
		return publishPort.deleteUserPersonality(DeleteUserPersonalityPublishDTO.builder()
			.messageId(messageId)
			.userPersonalityId(userPersonalityId)
			.build());
	}
}
