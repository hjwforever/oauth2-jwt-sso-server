package com.example.jjwtdemo;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class ServerApplicationTests {
    private final String key = "hjwforeversecuritykey";

    /**
     * 生成token
     */
    @Test
    void testJwt() {
        // 创建JwtBuilder对象
        JwtBuilder jwtBuilder = Jwts.builder()
                // 唯一id{"jti":"666"}
                .setId("666")
                // 接受的用户 {"sub":"Jack"}
                .setSubject("Jack")
                // 签发时间 {"iat":"..."}
                .setIssuedAt(new Date())
                // 签名算法及盐 {"alg":"HS256"}
                .signWith(SignatureAlgorithm.HS256, key);

        // 获取jwt的token
        String token = jwtBuilder.compact();
        System.out.println(token);

        // 三部分的base64解密
        System.out.println("====================");
        String[] split = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        // 无法解密
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
    }

    /**
     * 解析jwt
     */
    @Test
    public void parseJwt() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJKYWNrIiwiaWF0IjoxNjE2NDgyMjExfQ.xrFctumDsMSjjUmcZQW3exf2-qWGiy2SGLn4oihjEaw";
        // 解析token, 获取Claims， jwt中荷载的对象
        Claims claims = (Claims) Jwts.parser()
                .setSigningKey(key)
                .parse(token)
                .getBody();
        System.out.println("jti = " + claims.getId());
        System.out.println("sub = " + claims.getSubject());
        System.out.println("iat = " + claims.getIssuedAt());
    }

    /**
     * 生成jwt(自定义申明) // 设置过期时间
     */
    @Test
    void testJwtHasExpire() {
        // 当前时间
        long now = System.currentTimeMillis();
        // 失效时间
        long exp = now + 60 * 1000;
        // 创建JwtBuilder对象
        JwtBuilder jwtBuilder = Jwts.builder()
                // 唯一id{"jti":"666"}
                .setId("666")
                // 接受的用户 {"sub":"Jack"}
                .setSubject("Jack")
                // 签发时间 {"iat":"..."}
                .setIssuedAt(new Date(now))
                // 签名算法及盐 {"alg":"HS256"}
                .signWith(SignatureAlgorithm.HS256, key)
                // 自定义申明
                .claim("name","test")
                .claim("logo","avatar.jpg");
                // 设置失效时间 {"exp":"..."}
                //.setExpiration(new Date(exp));

        // 获取jwt的token
        String token = jwtBuilder.compact();
        System.out.println(token);

        // 三部分的base64解密
        System.out.println("====================");
        String[] split = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        // 无法解密
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
    }

    /**
     * 解析token(自定义申明)
     */
    @Test
    public void parseJwtHasExpire() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJKYWNrIiwiaWF0IjoxNjE2NDgzODE5LCJuYW1lIjoidGVzdCIsImxvZ28iOiJhdmF0YXIuanBnIn0.oHZGB2JectUSuRT31lKJyB1adHJ9998hoc_Yow1WeIM";
        // 解析token, 获取Claims， jwt中荷载的对象
        Claims claims = (Claims) Jwts.parser()
                .setSigningKey(key)
                .parse(token)
                .getBody();
        System.out.println("jti = " + claims.getId());
        System.out.println("sub = " + claims.getSubject());
        System.out.println("iat = " + claims.getIssuedAt());
        System.out.println("name = " + claims.get("name"));
        System.out.println("avatar = " + claims.get("logo"));
//        System.out.println("exp = " + claims.getExpiration());
    }
}
