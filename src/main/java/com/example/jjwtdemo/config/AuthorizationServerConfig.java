package com.example.jjwtdemo.config;

import com.example.jjwtdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright(C), 2020-2021, aruoxi.com
 * FileName: AuthorizationServerConfg
 * Author: hjwforever
 * Date: 2021/3/23 002315:44
 * Description: 授权服务器配置类
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 设置jwt增强内容
        TokenEnhancerChain chain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        chain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService)
                // accessToken转jwtToken
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(chain);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 必须要身份认证, 单点登录必须配置
        security.tokenKeyAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 客户端id
                .withClient("client")
                // 秘钥
                .secret(passwordEncoder.encode("112233"))
                // 重定向地址
//                .redirectUris("http://www.baidu.com")
                .redirectUris("http://localhost:8081/login")
                // 授权范围
                .scopes("all")
                // accessToken失效时间
                .accessTokenValiditySeconds(10)
                // refreshToken刷新令牌失效时间
                .refreshTokenValiditySeconds(86400)
                // 自动授权
                .autoApprove(true)
                /**
                 * 授权类型
                 * authorization_code 授权码模式
                 * password 密码模式
                 * refresh_token 刷新令牌
                 */
                .authorizedGrantTypes("authorization_code", "password", "refresh_token");
    }
}
