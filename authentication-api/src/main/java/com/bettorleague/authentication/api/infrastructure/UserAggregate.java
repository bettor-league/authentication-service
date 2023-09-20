package com.bettorleague.authentication.api.infrastructure;

import com.bettorleague.microservice.cqrs.annotations.Aggregate;
import com.bettorleague.microservice.cqrs.annotations.AggregateIdentifier;
import lombok.*;

@Aggregate
@Builder(toBuilder = true)
public class UserAggregate {

    @AggregateIdentifier
    private String aggregateIdentifier;
    private Boolean active;
    private String field;

}
