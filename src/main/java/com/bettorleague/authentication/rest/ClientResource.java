package com.bettorleague.authentication.rest;

import com.bettorleague.authentication.domain.Client;
import com.bettorleague.authentication.domain.User;
import com.bettorleague.authentication.repository.ClientRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/client")
public class ClientResource {

    private final ClientRepository clientRepository;

    public ClientResource(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @GetMapping
    @PreAuthorize("#oauth2.hasScope('server') or hasAuthority('ROLE_ADMIN')")
    @Operation(security = @SecurityRequirement(name = "OAuth2TokenBearer"))
    public Page<Client> getAllClient(@Valid @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @Valid @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                     @Valid @RequestParam(value = "sort", required = false, defaultValue = "clientId") String sort,
                                     @Valid @RequestParam(value = "sortDirection", required = false, defaultValue = "DESC") Sort.Direction sortDirection) {
        return clientRepository.findAll(PageRequest.of(page, size, Sort.by(sortDirection.equals(Sort.Direction.ASC) ? Sort.Order.asc(sort) : Sort.Order.desc(sort))));
    }
}
