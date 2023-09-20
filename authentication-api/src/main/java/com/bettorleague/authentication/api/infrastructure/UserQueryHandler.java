package com.bettorleague.authentication.api.infrastructure;

import com.bettorleague.authentication.api.service.UserService;
import com.bettorleague.authentication.core.model.User;
import com.bettorleague.authentication.core.query.FindAllUserPaginated;
import com.bettorleague.authentication.core.query.FindUserByIdentifier;
import com.bettorleague.microservice.cqrs.annotations.HandleQuery;
import com.bettorleague.microservice.model.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserQueryHandler {
    final UserService userService;
    @HandleQuery
    public Page<User> handle(FindAllUserPaginated query) {
        final Pageable pageable = query.getPageable();
        return userService.findAll(pageable);
    }

    @HandleQuery
    public User handle(FindUserByIdentifier query){
        final String userId = query.getIdentifier();
        return userService.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }
}
