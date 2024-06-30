package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserPersonality;

public interface UserPersonalityPersistPort {
    UserPersonality getUserPersonalityById(Long userPersonalityId);

    boolean existsUserPersonalityByUser(User currentUser);
}
