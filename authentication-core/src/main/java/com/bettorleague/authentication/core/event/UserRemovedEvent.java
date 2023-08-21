package com.bettorleague.authentication.core.event;

import com.bettorleague.microservice.cqrs.events.BaseEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserRemovedEvent extends BaseEvent {

}