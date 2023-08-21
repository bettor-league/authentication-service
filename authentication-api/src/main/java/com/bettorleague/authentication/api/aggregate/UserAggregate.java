package com.bettorleague.authentication.api.aggregate;

import com.bettorleague.authentication.core.command.RegisterUserCommand;
import com.bettorleague.authentication.core.command.RemoveUserCommand;
import com.bettorleague.authentication.core.event.UserRegisteredEvent;
import com.bettorleague.authentication.core.event.UserRemovedEvent;
import com.bettorleague.authentication.core.model.User;
import com.bettorleague.microservice.cqrs.domain.AggregateRoot;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserAggregate extends AggregateRoot<User> {
    public UserAggregate(RegisterUserCommand command) {
        final UserRegisteredEvent event = UserRegisteredEvent.builder()
                .id(command.getId())
                .email(command.getEmail())
                .password(command.getPassword())
                .build();
        raiseEvent(event);
    }

    public void apply(UserRegisteredEvent event) {
        this.id = event.getId();
    }

    public void on(RemoveUserCommand command) {
        final UserRemovedEvent event = UserRemovedEvent.builder()
                .id(command.getId())
                .build();
        raiseEvent(event);
    }

    public void apply(UserRemovedEvent event) {
        this.id = event.getId();
    }

}