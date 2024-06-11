package com.example.tripmingle.common.config.security.customuser;

import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO 커스텀 에러 적용
        User user = userRepository.findByEmail(username).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        return new CustomUserDetails(user);
    }

}
