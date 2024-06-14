package com.example.tripmingle.application.service;

import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserPersistPort userPersistPort;

    public User getCurrentUser() {
        return userPersistPort.findCurrentUserByEmail();
    }

}
