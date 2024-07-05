package com.example.tripmingle.adapter.out;

import org.springframework.stereotype.Component;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.UserPersonalityNotFoundException;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserPersonality;
import com.example.tripmingle.port.out.UserPersonalityPersistPort;
import com.example.tripmingle.repository.UserPersonalityRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserPersonalityPersistAdapter implements UserPersonalityPersistPort {
	private final UserPersonalityRepository userPersonalityRepository;

	@Override
	public UserPersonality getUserPersonalityById(Long userPersonalityId) {
		return userPersonalityRepository.findUserPersonalityById(userPersonalityId)
			.orElseThrow(() -> new UserPersonalityNotFoundException("user personality not found",
				ErrorCode.USER_PERSONALITY_NOT_FOUND));
	}

	@Override
	public boolean existsUserPersonalityByUser(User currentUser) {
		return userPersonalityRepository.existsByUserId(currentUser.getId());
	}

	@Override
	public UserPersonality saveUserPersonality(UserPersonality userPersonality) {
		return userPersonalityRepository.save(userPersonality);
	}

	@Override
	public UserPersonality getUserPersonalityByUserId(Long userId) {
		return userPersonalityRepository.findUserPersonalityByUserId(userId)
			.orElseThrow(() -> new UserPersonalityNotFoundException("user personality not found",
				ErrorCode.USER_PERSONALITY_NOT_FOUND));
	}

	@Override
	public void deleteUserPersonality(UserPersonality userPersonality) {
		userPersonalityRepository.delete(userPersonality);
		userPersonalityRepository.flush();
	}
}
