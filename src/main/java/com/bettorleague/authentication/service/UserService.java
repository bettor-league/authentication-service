package com.bettorleague.authentication.service;

import com.bettorleague.authentication.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    User create(User user);
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);
}
