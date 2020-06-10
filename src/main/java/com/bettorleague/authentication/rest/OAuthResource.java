package com.bettorleague.authentication.rest;

import com.bettorleague.microservice.model.exception.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/oauth")
public class OAuthResource {

    @GetMapping(value = "/me")
    @Operation(security = @SecurityRequirement(name = "OAuth2PasswordBearer"))
    public Principal getUser(Principal principal) {
        return Optional.ofNullable(principal).orElseThrow(() -> new BadRequestException("Not logged"));
    }
}
