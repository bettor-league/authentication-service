package com.bettorleague.authentication.api.infrastructure;

import com.bettorleague.authentication.api.service.UserService;
import com.bettorleague.authentication.core.command.RegisterUser;
import com.bettorleague.authentication.core.event.UserEvent;
import com.bettorleague.authentication.core.event.UserRegistered;
import com.bettorleague.microservice.cqrs.annotations.HandleCommand;
import com.bettorleague.microservice.model.exception.registration.EmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCommandHandler {

    final UserService userService;

    @HandleCommand
    public UserEvent handle(RegisterUser command) {
        final String email = command.getEmail();

        if (userService.existsByEmail(email)) {
            throw new EmailException(email);
        }

        return UserRegistered.builder()
                .aggregateIdentifier(command.getAggregateIdentifier())
                .email(email)
                .password(command.getPassword())
                .build();
    }

}
