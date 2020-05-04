package com.bettorleague.authentication.config;

import com.bettorleague.authentication.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final String NOOP_PASSWORD_ENCODE = "{noop}";
    private final TokenStore tokenStore = new InMemoryTokenStore();
    private final UserServiceImpl userService;
    @Qualifier("authenticationManagerBean")
    private final AuthenticationManager authenticationManager;

    private @Value("${bettorleague.oauth2.client.ui.client-id}") String uiClientId;
    private @Value("${bettorleague.oauth2.client.ui.client-secret}") String uiClientSecret;

    private @Value("${bettorleague.oauth2.client.server.client-id}") String serverClientId;
    private @Value("${bettorleague.oauth2.client.server.client-secret}") String serverClientSecret;

    public AuthorizationServerConfig(UserServiceImpl userService,
                                     AuthenticationManager authenticationManager){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.inMemory()
                .withClient(uiClientId)
                .authorizedGrantTypes("refresh_token", "password")
                .secret(uiClientSecret)
                .scopes("ui")
                .and()
                .withClient(serverClientId)
                .secret(serverClientSecret)
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("server");
        // @formatter:on
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .allowFormAuthenticationForClients();
    }


}
