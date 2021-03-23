package com.example.jjwtdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright(C), 2020-2021, aruoxi.com
 * FileName: JwtTokenEnhancer
 * Author: hjwforever
 * Date: 2021/3/23 002315:24
 * Description: token增强
 */
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> map = new HashMap<>();
        map.put("enhance","enhancer info");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
        return accessToken;
    }
}
