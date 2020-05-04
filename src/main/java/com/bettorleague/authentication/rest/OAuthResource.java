package com.bettorleague.authentication.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/oauth")
public class OAuthResource {

    @GetMapping(value = "/me")
    public Principal getUser(Principal principal) {
        return principal;
    }
}
