package com.bettorleague.authentication.api.infrastructure.query;

import com.bettorleague.authentication.api.service.UserService;
import com.bettorleague.authentication.core.model.User;
import com.bettorleague.authentication.core.query.FindAllUsersPaginatedQuery;
import com.bettorleague.authentication.core.query.FindAllUsersQuery;
import com.bettorleague.authentication.core.query.FindUserByIdQuery;
import com.bettorleague.microservice.cqrs.queries.QueryResponse;
import com.bettorleague.microservice.model.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryHandlerImpl implements UserQueryHandler {
    private final UserService userService;

    @Override
    public QueryResponse handle(FindAllUsersPaginatedQuery query) {
        final Pageable pageable = query.getPageable();
        final Page<User> response = userService.findAll(pageable);
        return new QueryResponse(response);
    }

    @Override
    public QueryResponse handle(FindAllUsersQuery query) {
        final List<User> response = userService.findAll();
        return new QueryResponse(response);
    }

    @Override
    public QueryResponse handle(FindUserByIdQuery query) {
        final String id = query.getId();
        final User response = userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return new QueryResponse(response);
    }
}
