package com.bettorleague.authentication.api.infrastructure.event;

import com.bettorleague.microservice.cqrs.events.BaseEvent;
import com.bettorleague.microservice.cqrs.producers.EventProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer implements EventProducer {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public UserEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void produce(String topic, BaseEvent event) {
    this.kafkaTemplate.send(topic, event);
  }
}
