package com.libwy.lingo.api.security;

import com.libwy.lingo.core.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DomainUserDetailsService implements UserDetailsService {
    public DomainUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByUserName(username).map(
                user -> new User(username, user.getPassword(), Collections.emptyList())
        ).orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the database"));
    }
}
