package com.bettorleague.authentication.core.query;

import com.bettorleague.microservice.cqrs.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindUserByIdQuery extends BaseQuery {
    private String id;
}