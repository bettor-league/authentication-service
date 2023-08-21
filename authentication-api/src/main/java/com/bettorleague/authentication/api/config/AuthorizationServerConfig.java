package com.bettorleague.authentication.api.config;


import com.bettorleague.authentication.core.model.Authority;
import com.bettorleague.authentication.core.model.AuthorityType;
import com.bettorleague.authentication.core.model.User;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfigurationSource;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Principal;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.bettorleague.authentication.api.security.SecurityConfig.LOGIN_URL;

@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfig {
    final CorsConfigurationSource corsConfigurationSource;
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(final HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
        http.exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(LOGIN_URL)));
        http.cors().configurationSource(corsConfigurationSource);
        return http.build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(final JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JwtEncoder jwtEncoder(final JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return this::computeContextWithUserAuthorities;
    }
    private void computeContextWithUserAuthorities(JwtEncodingContext context){
        final Principal principal = context.getPrincipal();
        if (principal instanceof final Authentication authentication) {
            final Object userAsObject = authentication.getPrincipal();
            if (userAsObject instanceof final User user) {
                final Set<Authority> authorities = user.retrieveAuthorities();
                final Set<String> scopeAuthorities = Optional.ofNullable(authorities).orElse(new HashSet<>())
                        .stream()
                        .filter(predicate -> AuthorityType.SCOPE.equals(predicate.getType()))
                        .map(Authority::getAuthority)
                        .collect(Collectors.toSet());
                final Set<String> roleAuthorities = Optional.ofNullable(authorities).orElse(new HashSet<>())
                        .stream()
                        .filter(predicate -> AuthorityType.ROLE.equals(predicate.getType()))
                        .map(Authority::getAuthority)
                        .collect(Collectors.toSet());
                if (CollectionUtils.isNotEmpty(scopeAuthorities)) {
                    context.getClaims().claims(claimsConsumer -> claimsConsumer.put(AuthorityType.SCOPE.getAttributeName(), scopeAuthorities));
                }
                if (CollectionUtils.isNotEmpty(roleAuthorities)) {
                    context.getClaims().claims(claims -> claims.put(AuthorityType.ROLE.getAttributeName(), roleAuthorities));
                }
            }
        }
    }


}