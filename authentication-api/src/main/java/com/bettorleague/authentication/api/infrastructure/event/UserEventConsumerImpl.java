package com.bettorleague.authentication.api.infrastructure.event;

import com.bettorleague.authentication.core.event.UserRegisteredEvent;
import com.bettorleague.authentication.core.event.UserRemovedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventConsumerImpl implements UserEventConsumer {

    private final UserEventHandler eventHandler;

    @Override
    @KafkaListener(topics = "UserRegisteredEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(UserRegisteredEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @Override
    @KafkaListener(topics = "UserRemovedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(UserRemovedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }
}
