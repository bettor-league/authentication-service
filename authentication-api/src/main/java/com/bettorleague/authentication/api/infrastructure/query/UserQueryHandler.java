package com.bettorleague.authentication.api.infrastructure.query;

import com.bettorleague.authentication.core.query.FindAllUsersPaginatedQuery;
import com.bettorleague.authentication.core.query.FindAllUsersQuery;
import com.bettorleague.authentication.core.query.FindUserByIdQuery;
import com.bettorleague.microservice.cqrs.queries.QueryResponse;

public interface UserQueryHandler {

    QueryResponse handle(FindAllUsersPaginatedQuery query);

    QueryResponse handle(FindAllUsersQuery query);

    QueryResponse handle(FindUserByIdQuery query);
}
