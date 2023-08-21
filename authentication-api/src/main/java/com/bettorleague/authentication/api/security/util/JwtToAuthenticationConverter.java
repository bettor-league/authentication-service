package com.bettorleague.authentication.api.security.util;

import com.bettorleague.authentication.core.model.Authority;
import com.bettorleague.authentication.core.model.AuthorityType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtToAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {

    @Override
    public JwtAuthenticationToken convert(Jwt jwt) {
        final String name = jwt.getSubject();
        final Collection<Authority> scopeAuthorities = extractAuthorities(jwt, AuthorityType.SCOPE);
        final Collection<Authority> roleAuthorities = extractAuthorities(jwt, AuthorityType.ROLE);
        final Collection<Authority> authorities = new ArrayList<>();
        authorities.addAll(scopeAuthorities);
        authorities.addAll(roleAuthorities);
        return new JwtAuthenticationToken(jwt, authorities, name);
    }

    protected Collection<Authority> extractAuthorities(Jwt jwt, AuthorityType authorityType) {
        return this.getAttributes(jwt, authorityType.getAttributeName())
                .stream()
                .map(Authority::fromLabel)
                .collect(Collectors.toList());
    }

    private Collection<String> getAttributes(Jwt jwt, String attributeName) {
        Object scopes = jwt.getClaims().get(attributeName);
        if (scopes instanceof String) {
            if (StringUtils.hasText((String) scopes)) {
                return Arrays.asList(((String) scopes).split(" "));
            } else {
                return Collections.emptyList();
            }
        } else if (scopes instanceof Collection) {
            return (Collection<String>) scopes;
        }

        return Collections.emptyList();
    }
}
