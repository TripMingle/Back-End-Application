package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.User;

import java.util.Optional;

public interface AuthPort {
    User save(User user);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
