package com.bettorleague.authentication.rest;


import com.bettorleague.authentication.domain.User;
import com.bettorleague.authentication.repository.UserRepository;
import com.bettorleague.authentication.service.UserService;
import com.bettorleague.microservice.model.exception.ResourceNotFoundException;
import com.bettorleague.microservice.model.security.UserCreationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Validated
@RestController
@RequestMapping("/user")
public class UserResource {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserResource(UserService userService,
                        UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('server') or hasAuthority('ROLE_ADMIN')")
    @Operation(security = @SecurityRequirement(name = "OAuth2TokenBearer"))
    public User createUser(@Valid @RequestBody UserCreationRequest user) {
        return userService.create(user);
    }

    @GetMapping
    @PreAuthorize("#oauth2.hasScope('server') or hasAuthority('ROLE_ADMIN')")
    @Operation(security = @SecurityRequirement(name = "OAuth2TokenBearer"))
    public Page<User> getAllUser(@Valid @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                 @Valid @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                 @Valid @RequestParam(value = "sort", required = false, defaultValue = "username") String sort,
                                 @Valid @RequestParam(value = "sortDirection", required = false, defaultValue = "DESC") Sort.Direction sortDirection) {
        return userRepository.findAll(PageRequest.of(page, size, Sort.by(sortDirection.equals(Sort.Direction.ASC) ? Sort.Order.asc(sort) : Sort.Order.desc(sort))));
    }

    @GetMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('server') or hasRole('ROLE_ADMIN')")
    @Operation(security = @SecurityRequirement(name = "OAuth2TokenBearer"))
    public User getUserById(@Valid @NotEmpty @PathVariable(value = "id") String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('server') or hasRole('ROLE_ADMIN')")
    @Operation(security = @SecurityRequirement(name = "OAuth2TokenBearer"))
    public void deleteUserById(@Valid @NotEmpty @PathVariable(value = "id") String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }



}
