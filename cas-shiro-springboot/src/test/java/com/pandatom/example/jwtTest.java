package com.pandatom.example;

import com.pandatom.example.util.JWTUtils;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.util.Date;

/**
 * @Description: TODO
 * @author: changxiong
 * @date: 2022年03月28日 2:58 PM
 */
public class jwtTest {
    public static void main(String[] args) {
        String token = JWTUtils.createToken("tom", "new Date()","123456");
        String time = JWTUtils.getTime(token);
        String name = JWTUtils.getUsername(token);
        System.out.println(time);
    }
}
