package com.example.tripmingle.application.service;

import com.example.tripmingle.dto.req.matching.PostUserPersonalityReqDTO;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserPersonality;
import com.example.tripmingle.port.out.UserPersonalityPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPersonalityService {
    private final UserPersonalityPersistPort userPersonalityPersistPort;
    public UserPersonality getUserPersonalityById(Long userPersonalityId) {
        return userPersonalityPersistPort.getUserPersonalityById(userPersonalityId);
    }

    public boolean existsUserPersonalityByUser(User currentUser) {
        return userPersonalityPersistPort.existsUserPersonalityByUser(currentUser);
    }

    @Transactional
    public UserPersonality saveUserPersonality(PostUserPersonalityReqDTO postUserPersonalityReqDTO, User currentUser) {
        double genderRate = currentUser.getGender().equals("MALE")?1.0:5.0;
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
}
