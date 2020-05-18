package com.bettorleague.authentication.config;

import com.bettorleague.authentication.domain.MongoTokenStore;
import com.bettorleague.authentication.service.ClientService;
import com.bettorleague.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final UserService userService;
    private final ClientService clientService;
    private final MongoTemplate mongoTemplate;

    @Qualifier("authenticationManagerBean")
    private final AuthenticationManager authenticationManager;


    public AuthorizationServerConfig(UserService userService,
                                     ClientService clientService,
                                     MongoTemplate mongoTemplate,
                                     AuthenticationManager authenticationManager){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.clientService = clientService;
        this.mongoTemplate = mongoTemplate;
    }

    

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(mongoTokenStore())
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

    @Bean
    MongoTokenStore mongoTokenStore(){
        return new MongoTokenStore(mongoTemplate);
    }

}
