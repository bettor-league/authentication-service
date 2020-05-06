package com.bettorleague.authentication.service.impl;

import com.bettorleague.authentication.domain.Authorities;
import com.bettorleague.authentication.domain.User;
import com.bettorleague.authentication.repository.UserRepository;
import com.bettorleague.authentication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    public User create(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        userOptional.ifPresent(it-> {
            throw new IllegalArgumentException("User already exists: " + it.getEmail());
        });

        String hash = passwordEncoder.encode(user.getPassword());

        user.setPassword(hash);
        user.setActivated(true);
        user.setAuthorities(Set.of(Authorities.ROLE_USER));

        user = userRepository.save(user);

        log.info("New user has been created: {}", user.getUsername());

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + email + " not found"));
    }

    @Override
    public Optional<User> findByEmail(String email){
        return this.userRepository.findByEmail(email);
    }


    @Override
    public Page<User> findAll(Pageable pageable){
        return this.userRepository.findAll(pageable);
    }

}
