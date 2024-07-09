package com.example.tripmingle.application.service;

import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserPersistPort userPersistPort;

    public User getCurrentUser() {
        return userPersistPort.findCurrentUserByEmail();
    }

    public List<User> getUsersById(List<Long> userIds){
        return userPersistPort.getUsersById(userIds);
    }

    public User getUserById(Long userId) {
        return userPersistPort.getUserById(userId);
    }

}
