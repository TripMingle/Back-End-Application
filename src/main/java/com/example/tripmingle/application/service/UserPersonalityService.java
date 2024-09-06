package com.example.tripmingle.application.service;

import java.util.List;

import com.example.tripmingle.dto.etc.ChangeUserPersonalityDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tripmingle.common.utils.CommonUtils;
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
	private final CommonUtils commonUtils;

	public UserPersonality getUserPersonalityById(Long userPersonalityId) {
		return userPersonalityPersistPort.getUserPersonalityById(userPersonalityId);
	}

	public boolean existsUserPersonalityByUser(User currentUser) {
		return userPersonalityPersistPort.existsUserPersonalityByUser(currentUser);
	}

	@Transactional
	public UserPersonality saveUserPersonality(PostUserPersonalityReqDTO postUserPersonalityReqDTO, User currentUser) {
		double genderRate = currentUser.getGender().equals("MALE") ? 1.0 : 5.0;
		double ageRange;
		switch (currentUser.getAgeRange()) {
			case "10-20":
			case "20-30":
				ageRange = 1.0;
				break;
			case "30-40":
				ageRange = 2.0;
				break;
			case "40-50":
				ageRange = 3.0;
				break;
			default:
				ageRange = 5.0;
				break;
		}

		UserPersonality userPersonality = UserPersonality.builder()
			.user(currentUser)
			.gender(genderRate)
			.vegan(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getVegan()))
			.islam(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getIslam()))
			.hindu(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getHindu()))
			.smoking(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getSmoking()))
			.budget(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getBudget()))
			.accommodationFlexibility(
				commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getAccommodationFlexibility()))
			.foodFlexibility(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getFoodFlexibility()))
			.activity(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getActivity()))
			.photo(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getPhoto()))
			.foodExploration(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getFoodExploration()))
			.adventure(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getAdventure()))
			.personality(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getPersonality()))
			.schedule(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getSchedule()))
			.drink(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getDrink()))
			.ageRange(ageRange)
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

	public void changeUserPersonality(UserPersonality userPersonality, PostUserPersonalityReqDTO postUserPersonalityReqDTO) {
		ChangeUserPersonalityDTO changeUserPersonalityDTO = ChangeUserPersonalityDTO.builder()
				.vegan(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getVegan()))
				.islam(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getIslam()))
				.hindu(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getHindu()))
				.smoking(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getSmoking()))
				.budget(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getBudget()))
				.accommodationFlexibility(
						commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getAccommodationFlexibility()))
				.foodFlexibility(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getFoodFlexibility()))
				.activity(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getActivity()))
				.photo(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getPhoto()))
				.foodExploration(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getFoodExploration()))
				.adventure(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getAdventure()))
				.personality(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getPersonality()))
				.schedule(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getSchedule()))
				.drink(commonUtils.convertIntToDouble(postUserPersonalityReqDTO.getDrink()))
				.build();
		userPersonality.changeUserPersonality(changeUserPersonalityDTO);
	}
}
