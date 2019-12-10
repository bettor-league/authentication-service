package com.bettorleague.authentication.rest;


import com.bettorleague.authentication.domain.User;
import com.bettorleague.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

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

    @GetMapping(value = "/current")
    public Principal getUser(Principal principal) {
        return principal;
    }

}
