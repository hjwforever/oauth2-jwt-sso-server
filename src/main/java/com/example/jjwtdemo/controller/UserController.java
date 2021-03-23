package com.example.jjwtdemo.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * Copyright(C), 2020-2021, aruoxi.com
 * FileName: UserController
 * Author: hjwforever
 * Date: 2021/3/23 002316:11
 * Description: 用户控制类
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication, HttpServletRequest request) {
//        System.out.println("authentication = " + authentication);
//        System.out.println(authentication.getDetails());
//        return authentication.getPrincipal();
        String header = request.getHeader("Authorization");
        String token = header.substring(header.lastIndexOf("bearer") + 7);
        return Jwts.parser()
                .setSigningKey("test_key".getBytes(StandardCharsets.UTF_8)) // 可中文转码，防止乱码
                .parseClaimsJws(token)
                .getBody();
    }
}
