package com.libwy.lingo.api;

import com.libwy.lingo.api.dto.UserCreateDTO;
import com.libwy.lingo.core.domain.User;
import com.libwy.lingo.core.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // save user
    public User registerUser(final UserCreateDTO dto) {
        String password = this.passwordEncoder.encode(dto.getPassword());
        User user = User.builder()
                .userName(dto.getUsername())
                .password(password)
                .email(dto.getEmail()).build();
        return this.userRepository.save(user);
    }

}
