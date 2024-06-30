package com.example.tripmingle.application.service;

import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserPersonality;
import com.example.tripmingle.port.out.UserPersonalityPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
