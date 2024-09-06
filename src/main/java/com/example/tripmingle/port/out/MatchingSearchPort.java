package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.UserPersonality;

import java.util.List;

public interface MatchingSearchPort {
    List<Long> getSimilarUsersByUserPersonalityId(Long userPersonalityId, double[] targetVector);

    List<Long> getSimilarUsersByUserPersonalityIdByHNSW(Long userPersonalityId, double[] featureVector);

    void saveUserPersonality(UserPersonality userPersonality);
}
