package com.example.jjwtdemo.service;

import com.example.jjwtdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Copyright(C), 2020-2021, aruoxi.com
 * FileName: UserService
 * Author: hjwforever
 * Date: 2021/3/23 002315:47
 * Description:
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = passwordEncoder.encode("123456");
        return new User(username,password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
