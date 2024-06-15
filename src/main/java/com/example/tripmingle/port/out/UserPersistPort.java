package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.User;

public interface UserPersistPort {
    User save(User user);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    User findCurrentUserByEmail();
}
