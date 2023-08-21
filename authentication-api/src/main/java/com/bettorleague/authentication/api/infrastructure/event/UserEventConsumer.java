package com.bettorleague.authentication.api.infrastructure.event;

import com.bettorleague.authentication.core.event.UserRegisteredEvent;
import com.bettorleague.authentication.core.event.UserRemovedEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface UserEventConsumer {
  void consume(@Payload UserRegisteredEvent event, Acknowledgment ack);

  void consume(@Payload UserRemovedEvent event, Acknowledgment ack);
}
