package com.bettorleague.authentication.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TokenProvider {
    private final AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

    @Value("${bettorleague.oauth2.client.ui.client-id:#{null}}")
    private String clientId;


    public TokenProvider(AuthorizationServerTokenServices defaultAuthorizationServerTokenServices){
        this.defaultAuthorizationServerTokenServices = defaultAuthorizationServerTokenServices;
    }


    public OAuth2AccessToken getAccessToken(Authentication authentication) {
        return defaultAuthorizationServerTokenServices.createAccessToken(convertAuthentication(authentication));
    }

    private OAuth2Authentication convertAuthentication(Authentication authentication) {
        OAuth2Request request = new OAuth2Request(null, clientId, null, true, Set.of("ui"), null, null, null, null);
        return new OAuth2Authentication(request, new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), "N/A", authentication.getAuthorities()));
    }
}
