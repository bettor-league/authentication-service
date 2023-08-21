package com.bettorleague.authentication.api.infrastructure.event;

import com.bettorleague.authentication.api.aggregate.UserAggregate;
import com.bettorleague.microservice.cqrs.events.BaseEvent;
import com.bettorleague.microservice.cqrs.events.EventModel;
import com.bettorleague.microservice.cqrs.infrastructure.EventStore;
import com.bettorleague.microservice.cqrs.producers.EventProducer;
import com.bettorleague.microservice.cqrs.repository.EventStoreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserEventStore implements EventStore {
    private final EventProducer eventProducer;
    private final EventStoreRepository repository;

    public UserEventStore(EventProducer eventProducer, EventStoreRepository repository) {
        this.eventProducer = eventProducer;
        this.repository = repository;
    }

    @Override
    public void save(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = repository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1
                && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new RuntimeException();
        }
        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel =
                    EventModel.builder()
                            .timestamp(LocalDateTime.now())
                            .aggregateIdentifier(aggregateId)
                            .aggregateType(UserAggregate.class.getTypeName())
                            .version(version)
                            .eventType(event.getClass().getTypeName())
                            .eventData(event)
                            .build();

            var persistedEvent = repository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = repository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new RuntimeException("Incorrect account ID provided!");
        }

        return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }

    @Override
    public List<String> getAggregateIds() {
        var eventStream = repository.findAll();
        if (eventStream == null || eventStream.isEmpty()) {
            throw new IllegalStateException();
        }
        return eventStream.stream()
                .map(EventModel::getAggregateIdentifier)
                .distinct()
                .collect(Collectors.toList());
    }
}
