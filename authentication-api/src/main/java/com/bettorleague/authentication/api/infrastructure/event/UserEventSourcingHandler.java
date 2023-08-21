package com.bettorleague.authentication.api.infrastructure.event;


import com.bettorleague.authentication.api.aggregate.UserAggregate;
import com.bettorleague.microservice.cqrs.domain.AggregateRoot;
import com.bettorleague.microservice.cqrs.handlers.EventSourcingHandler;
import com.bettorleague.microservice.cqrs.infrastructure.EventStore;
import com.bettorleague.microservice.cqrs.producers.EventProducer;
import com.bettorleague.microservice.cqrs.events.BaseEvent;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class UserEventSourcingHandler implements EventSourcingHandler<UserAggregate> {

    private final EventStore eventStore;
    private final EventProducer eventProducer;

    public UserEventSourcingHandler(EventStore eventStore, EventProducer eventProducer) {
        this.eventStore = eventStore;
        this.eventProducer = eventProducer;
    }

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.save(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public UserAggregate getById(String id) {
        var aggregate = new UserAggregate();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }

        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for (var aggregateId : aggregateIds) {
            var events = eventStore.getEvents(aggregateId);
            for (var event : events) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}
