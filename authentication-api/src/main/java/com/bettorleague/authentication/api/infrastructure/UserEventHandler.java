package com.bettorleague.authentication.api.infrastructure;

import com.bettorleague.authentication.api.service.UserService;
import com.bettorleague.authentication.core.event.UserRegistered;
import com.bettorleague.authentication.core.event.UserRemoved;
import com.bettorleague.authentication.core.model.Authority;
import com.bettorleague.authentication.core.model.User;
import com.bettorleague.microservice.cqrs.annotations.HandleEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventHandler {

    private final UserService userService;

    @HandleEvent
    public void handle(UserRegistered event) {
        final String email = event.getEmail();
        final String password = event.getPassword();
        final User user = User.builder()
                .email(email)
                .password(password)
                .activated(true)
                .authorities(Set.of(Authority.USER))
                .build();
        userService.save(user);
    }

    @HandleEvent
    public void handle(UserRemoved event) {
        final String userIdentifier = event.getAggregateIdentifier();
        userService.deleteById(userIdentifier);
    }

}
