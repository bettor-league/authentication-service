package com.bettorleague.authentication.api.security.handler;

import com.bettorleague.microservice.common.rest.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@RequiredArgsConstructor
public class AuthenticationErrorResponseHandler implements AuthenticationFailureHandler {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final GlobalExceptionHandler exceptionHandler;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        handlerExceptionResolver.resolveException(request, response, exceptionHandler, new ResponseStatusException(HttpStatus.UNAUTHORIZED, authException.getMessage(), authException.getCause()));
    }
}
