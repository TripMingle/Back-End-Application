package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.UserNotFoundException;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.UserPersistPort;
import com.example.tripmingle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserPersistAdapter implements UserPersistPort {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User Not Found.", ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User findCurrentUserByEmail() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new UserNotFoundException("User Not Found.", ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public boolean existsByNickName(String nickName) {
        return userRepository.existsByNickName(nickName);
    }

    @Override
    public List<User> getUsersById(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }
}
