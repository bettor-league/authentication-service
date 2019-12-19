package com.bettorleague.authentication;

import com.bettorleague.authentication.domain.Authorities;
import com.bettorleague.authentication.domain.User;
import com.bettorleague.authentication.repository.UserRepository;
import com.bettorleague.authentication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class ConfigAuthenticationService implements CommandLineRunner {

    private final UserService userService;
    private final UserRepository userRepository;

    public ConfigAuthenticationService(UserService userService,
                                       UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args){
        log.warn("Loading admin account...");

        Optional<User> admin = userRepository.findByUsername("admin");
        if(admin.isPresent()){

            log.warn("Admin already added");
            log.warn("Admin login : "+admin.get().getUsername());
            log.warn("Admin password : "+admin.get().getPassword());
        }else {

            userService.create(User.builder()
                    .username("admin")
                    .password("admin")
                    .authorities(Set.of(Authorities.ROLE_USER, Authorities.ROLE_ADMIN))
                    .build());
            log.warn("Admin account added");
        }
    }
}
