package com.bettorleague.authentication.service;

import com.bettorleague.authentication.domain.User;
import com.bettorleague.microservice.model.security.UserCreationRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User create(UserCreationRequest user);
}
