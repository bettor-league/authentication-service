package com.bettorleague.authentication.api.security;

import com.bettorleague.authentication.api.security.handler.CustomAccessDeniedHandler;
import com.bettorleague.authentication.api.security.handler.CustomAuthenticationEntryPoint;
import com.bettorleague.authentication.api.security.util.JwtToAuthenticationConverter;
import com.bettorleague.authentication.api.service.UserService;
import com.bettorleague.authentication.core.model.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    public static final String LOGIN_URL = "/login";
    public static final String LOGIN_FAILURE_URL = "/login?error";
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtToAuthenticationConverter jwtToAuthenticationConverter;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(final HttpSecurity http,
                                                          final CorsConfigurationSource corsConfigurationSource) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/").permitAll()
                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                .requestMatchers(HttpMethod.GET, "/user", "/user/**").hasAnyAuthority(Authority.READ.getAuthority(), Authority.USER.getAuthority())
                .requestMatchers(HttpMethod.DELETE, "/user/**").hasAnyAuthority(Authority.WRITE.getAuthority(), Authority.ADMIN.getAuthority())
                .requestMatchers("/oauth2/**", "/assets/**", "/webjars/**", "/login", "/error", "/csrf", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated();
        http.cors().configurationSource(corsConfigurationSource);
        http.csrf().disable();
        http.oauth2ResourceServer(configurer -> configurer
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
                .jwt()
                .jwtAuthenticationConverter(jwtToAuthenticationConverter)
        );
        http.formLogin()
                .loginPage(LOGIN_URL);
        http.oauth2Login()
                .loginPage(LOGIN_URL)
                .userInfoEndpoint().userService(userService);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }


}
