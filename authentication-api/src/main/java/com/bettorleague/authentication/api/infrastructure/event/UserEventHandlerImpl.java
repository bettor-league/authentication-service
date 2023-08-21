package com.bettorleague.authentication.api.infrastructure.event;

import com.bettorleague.authentication.api.service.UserService;
import com.bettorleague.authentication.core.event.UserRegisteredEvent;
import com.bettorleague.authentication.core.event.UserRemovedEvent;
import com.bettorleague.authentication.core.model.Authority;
import com.bettorleague.authentication.core.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void on(UserRegisteredEvent event) {
        final String id = event.getId();
        final String email = event.getEmail();
        final String password = event.getPassword();
        final String encodedPassword = passwordEncoder.encode(password);
        final User user = User.builder()
                .id(id)
                .email(email)
                .password(encodedPassword)
                .activated(true)
                .authorities(Set.of(Authority.USER))
                .build();
        userService.save(user);
    }

    @Override
    public void on(UserRemovedEvent event) {
        final String id = event.getId();
        userService.deleteById(id);
    }
}
