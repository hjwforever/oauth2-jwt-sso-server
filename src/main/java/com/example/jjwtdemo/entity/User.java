package com.example.jjwtdemo.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Copyright(C), 2020-2021, aruoxi.com
 * FileName: User
 * Author: hjwforever
 * Date: 2021/3/23 002315:53
 * Description: 用户类
 */
public class User implements UserDetails {

    private String username;
    private String password;
//    private Collection<? extends GrantedAuthority> authorities;
    private List<GrantedAuthority> authorities;

    public User(String username, String password, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
