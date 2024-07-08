package com.example.tripmingle.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tripmingle.common.utils.UserUtils;
import com.example.tripmingle.dto.req.matching.PostUserPersonalityReqDTO;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserPersonality;
import com.example.tripmingle.port.out.UserPersonalityPersistPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPersonalityService {
	private final UserPersonalityPersistPort userPersonalityPersistPort;
	private final UserUtils userUtils;

	public UserPersonality getUserPersonalityById(Long userPersonalityId) {
		return userPersonalityPersistPort.getUserPersonalityById(userPersonalityId);
	}

	public boolean existsUserPersonalityByUser(User currentUser) {
		return userPersonalityPersistPort.existsUserPersonalityByUser(currentUser);
	}

	@Transactional
	public UserPersonality saveUserPersonality(PostUserPersonalityReqDTO postUserPersonalityReqDTO, User currentUser) {
		double genderRate = currentUser.getGender().equals("MALE") ? 1.0 : 5.0;
		UserPersonality userPersonality = UserPersonality.builder()
			.user(currentUser)
			.gender(genderRate)
			.vegan(postUserPersonalityReqDTO.getVegan())
			.islam(postUserPersonalityReqDTO.getIslam())
			.hindu(postUserPersonalityReqDTO.getHindu())
			.smoking(postUserPersonalityReqDTO.getSmoking())
			.budget(postUserPersonalityReqDTO.getBudget())
			.accommodationFlexibility(postUserPersonalityReqDTO.getAccommodationFlexibility())
			.foodFlexibility(postUserPersonalityReqDTO.getFoodFlexibility())
			.activity(postUserPersonalityReqDTO.getActivity())
			.instagramPicture(postUserPersonalityReqDTO.getInstagramPicture())
			.foodExploration(postUserPersonalityReqDTO.getFoodExploration())
			.adventure(postUserPersonalityReqDTO.getAdventure())
			.personality(postUserPersonalityReqDTO.getPersonality())
			.schedule(postUserPersonalityReqDTO.getSchedule())
			.build();
		return userPersonalityPersistPort.saveUserPersonality(userPersonality);
	}

	public UserPersonality getUserPersonalityByUserId(Long userId) {
		return userPersonalityPersistPort.getUserPersonalityByUserId(userId);
	}

	public void delete(User currentUser, UserPersonality userPersonality) {
		userUtils.validateMasterUser(currentUser.getId(), userPersonality.getUser().getId());
		userPersonalityPersistPort.deleteUserPersonality(userPersonality);
	}

	public List<UserPersonality> getUserPersonalityByIds(List<Long> similarUsers) {
		return userPersonalityPersistPort.getUserPersonalitiesByIds(similarUsers);
	}
}
