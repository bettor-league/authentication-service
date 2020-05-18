package com.bettorleague.authentication.service.impl;

import com.bettorleague.authentication.domain.Authorities;
import com.bettorleague.authentication.domain.User;
import com.bettorleague.authentication.repository.UserRepository;
import com.bettorleague.authentication.service.UserService;
import com.bettorleague.microservice.model.exception.BadRequestException;
import com.bettorleague.microservice.model.exception.registration.EmailException;
import com.bettorleague.microservice.model.exception.registration.UsernameException;
import com.bettorleague.microservice.model.security.UserCreationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User create(UserCreationRequest userCreationRequest) {
        log.info("New user creation attempt with email : {} & username {}", userCreationRequest.getEmail(), userCreationRequest.getUsername() );

        User user = new User();

        userRepository.findByEmail(userCreationRequest.getEmail()).ifPresent(optionalUser -> {
            log.error("User creation failed, email already used : " + optionalUser.getEmail());
            throw new EmailException(optionalUser.getEmail());
        });

        userRepository.findByUsername(userCreationRequest.getUsername()).ifPresent(optionalUser -> {
            log.error("User creation failed, username already used : " + optionalUser.getUsername());
            throw new UsernameException(optionalUser.getUsername());
        });

        String hash = passwordEncoder.encode(
                Optional.ofNullable(userCreationRequest.getPassword())
                        .orElseThrow(() -> new BadRequestException("Password cannot be empty"))
        );

        user.setUsername(userCreationRequest.getUsername().toLowerCase());
        user.setEmail(userCreationRequest.getEmail().toLowerCase());
        user.setPassword(hash);
        user.setActivated(true);
        user.setAuthorities(Set.of(Authorities.ROLE_USER));

        user = userRepository.save(user);

        log.info("New user has been created: {}", user.getUsername());

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
        return userRepository.findByEmailOrUsername(emailOrUsername,emailOrUsername)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with Email : %s or Username : %s not found",emailOrUsername,emailOrUsername)));
    }

}
