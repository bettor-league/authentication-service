package com.bettorleague.authentication.service.impl;

import com.bettorleague.authentication.domain.User;
import com.bettorleague.authentication.repository.UserRepository;
import com.bettorleague.authentication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository repository;

    @Override
    public User create(User user) {

        Optional<User> existing = repository.findById(user.getUsername());

        existing.ifPresent(it-> {throw new IllegalArgumentException("user already exists: " + it.getUsername());});

        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);

        user = repository.save(user);

        log.info("new user has been created: {}", user.getUsername());

        return user;

    }

}
