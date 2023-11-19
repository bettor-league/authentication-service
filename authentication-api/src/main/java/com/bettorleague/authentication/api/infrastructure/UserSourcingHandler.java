package com.bettorleague.authentication.api.infrastructure;

import com.bettorleague.authentication.core.event.UserRegistered;
import com.bettorleague.authentication.core.event.UserRemoved;
import com.bettorleague.microservice.cqrs.annotations.ApplyEvent;
import org.springframework.stereotype.Component;

@Component
public class UserSourcingHandler {

    @ApplyEvent
    public UserAggregate apply(UserAggregate state, UserRegistered event) {
        return UserAggregate.builder()
                .aggregateIdentifier(event.getAggregateIdentifier())
                .active(true)
                .build();
    }

    @ApplyEvent
    public UserAggregate apply(UserAggregate state, UserRemoved event) {
        return state.toBuilder()
                .active(false)
                .build();
    }

}
