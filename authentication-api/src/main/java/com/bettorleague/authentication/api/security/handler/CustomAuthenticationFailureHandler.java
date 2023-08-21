package com.bettorleague.authentication.api.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.bettorleague.authentication.api.security.SecurityConfig.LOGIN_FAILURE_URL;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    final SimpleUrlAuthenticationFailureHandler delegate = new SimpleUrlAuthenticationFailureHandler(LOGIN_FAILURE_URL);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        request.setAttribute("errorMessage", exception.getMessage());
        delegate.onAuthenticationFailure(request, response, exception);
    }
}
