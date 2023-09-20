package com.bettorleague.authentication.core.command;

import com.bettorleague.microservice.cqrs.annotations.AggregateIdentifier;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemoveUser extends UserCommand{
    @AggregateIdentifier
    private String aggregateIdentifier;
}