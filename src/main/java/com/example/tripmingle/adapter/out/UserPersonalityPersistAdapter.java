package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.UserPersonalityNotFoundException;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserPersonality;
import com.example.tripmingle.port.out.UserPersonalityPersistPort;
import com.example.tripmingle.repository.UserPersonalityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersonalityPersistAdapter implements UserPersonalityPersistPort {
    private final UserPersonalityRepository userPersonalityRepository;

    @Override
    public UserPersonality getUserPersonalityById(Long userPersonalityId) {
        return userPersonalityRepository.findUserPersonalityById(userPersonalityId)
                .orElseThrow(()-> new UserPersonalityNotFoundException("userpersonality not found", ErrorCode.USER_PERSONALITY_NOT_FOUND));
    }

    @Override
    public boolean existsUserPersonalityByUser(User currentUser) {
        return userPersonalityRepository.existsByUserId(currentUser.getId());
    }
}
