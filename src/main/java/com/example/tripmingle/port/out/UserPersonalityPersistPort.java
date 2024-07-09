package com.example.tripmingle.port.out;

import java.util.List;

import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserPersonality;

public interface UserPersonalityPersistPort {
	UserPersonality getUserPersonalityById(Long userPersonalityId);

	boolean existsUserPersonalityByUser(User currentUser);

	UserPersonality saveUserPersonality(UserPersonality userPersonality);

	UserPersonality getUserPersonalityByUserId(Long userId);

	void deleteUserPersonality(UserPersonality userPersonality);

	List<UserPersonality> getUserPersonalitiesByIds(List<Long> similarUsers);
}
