package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.User;

import java.util.List;

public interface UserPersistPort {
    User save(User user);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    User findCurrentUserByEmail();

    boolean existsByNickName(String nickName);

    List<User> getUsersById(List<Long> userIds);
}
