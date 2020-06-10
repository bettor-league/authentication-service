package com.bettorleague.authentication.config;


import com.bettorleague.authentication.social.handler.SocialLoginFailureHandler;
import com.bettorleague.authentication.social.handler.SocialLoginSuccessHandler;
import com.bettorleague.authentication.social.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.bettorleague.authentication.social.service.SocialLoginService;
import com.bettorleague.microservice.common.security.UnprotectedPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    @Value("${security.enabled:true}")
    private Boolean securityEnabled;

    @Value("${security.social:false}")
    private Boolean socialEnabled;

    private final List<String> unprotectedPath;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final SocialLoginService socialLoginService;
    private final SocialLoginSuccessHandler socialLoginSuccessHandler;
    private final SocialLoginFailureHandler socialLoginFailureHandler;

    public ResourceConfig(UnprotectedPath unprotectedPath,
                          SocialLoginService socialLoginService,
                          SocialLoginSuccessHandler socialLoginSuccessHandler,
                          SocialLoginFailureHandler socialLoginFailureHandler,
                          HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository){
        this.unprotectedPath = Optional.ofNullable(unprotectedPath).map(UnprotectedPath::getUnprotectedPath).orElseGet(ArrayList::new);
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
        this.socialLoginService = socialLoginService;
        this.socialLoginSuccessHandler = socialLoginSuccessHandler;
        this.socialLoginFailureHandler = socialLoginFailureHandler;
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {

        for(String path: unprotectedPath){
            http.authorizeRequests().antMatchers(path).permitAll();
        }

        if(securityEnabled){
            http.authorizeRequests().anyRequest().authenticated();
        }else{
            http.authorizeRequests().anyRequest().permitAll();
        }

        if(socialEnabled){
            http.oauth2Login()
                    .authorizationEndpoint()
                    .baseUri("/oauth2/authorize")
                    .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                    .and()
                    .redirectionEndpoint()
                    .baseUri("/oauth2/callback/*")
                    .and()
                    .userInfoEndpoint().userService(socialLoginService)
                    .and()
                    .successHandler(socialLoginSuccessHandler)
                    .failureHandler(socialLoginFailureHandler);
        }

        http
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable();
    }
}
