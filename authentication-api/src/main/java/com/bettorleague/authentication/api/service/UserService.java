package com.bettorleague.authentication.api.service;

import com.bettorleague.authentication.core.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    Page<User> findAll(Pageable pageable);
    List<User> findAll();
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);

    User save(User user);

    void deleteById(String userId);

    void deleteUser(User user);

    boolean existsByEmail(String email);

    boolean existsByIdentifier(String userIdentifier);

}
