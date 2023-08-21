package com.bettorleague.authentication.api.social;

import com.bettorleague.authentication.api.social.impl.FacebookOAuth2UserInfo;
import com.bettorleague.authentication.api.social.impl.GithubOAuth2UserInfo;
import com.bettorleague.authentication.api.social.impl.GoogleOAuth2UserInfo;
import com.bettorleague.microservice.model.exception.BadRequestException;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();

    public static OAuth2UserInfo from(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase("google")) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase("facebook")) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase("github")) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new BadRequestException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}