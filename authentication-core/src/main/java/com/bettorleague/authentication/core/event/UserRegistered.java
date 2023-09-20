package com.bettorleague.authentication.core.event;

import com.bettorleague.microservice.cqrs.annotations.AggregateIdentifier;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistered extends UserEvent {
    @AggregateIdentifier
    private String aggregateIdentifier;
    private String email;
    private String password;
}
