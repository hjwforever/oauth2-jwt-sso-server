package com.example.jjwtdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

/**
 * Copyright(C), 2020-2021, aruoxi.com
 * FileName: JwtTokenStoreConfig
 * Author: hjwforever
 * Date: 2021/3/23 002315:38
 * Description:
 */
@Configuration
public class JwtTokenStoreConfig {

    @Bean("jwtTokenStore")
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 设置jwt秘钥
        jwtAccessTokenConverter.setSigningKey("test_key");
        return  jwtAccessTokenConverter;
    }

    @Bean
    public JwtTokenEnhancer jwtTokenEnhancer() {
        return new JwtTokenEnhancer();
    };
}
