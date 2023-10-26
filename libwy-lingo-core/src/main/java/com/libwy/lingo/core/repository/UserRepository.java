package com.libwy.lingo.core.repository;

import com.libwy.lingo.core.domain.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {
    Optional<User> findUserByUserName(String userName);
    User save(User user);

}
