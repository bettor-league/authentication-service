package com.bettorleague.authentication.rest;


import com.bettorleague.authentication.domain.User;
import com.bettorleague.authentication.service.UserService;
import org.springdoc.core.converters.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('server')")
    public User createUser(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping
    @PageableAsQueryParam
    public Page<User> getAllUser(Pageable pageable) {
        return userService.findAll(pageable);
    }

}
