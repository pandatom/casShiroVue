package com.pandatom.example.service.impl;

import com.pandatom.example.entity.JwtUser;
import com.pandatom.example.service.IUserService;
import com.pandatom.example.util.JWTUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @author: changxiong
 * @date: 2022年03月28日 3:21 PM
 */
@Service
public class UserService implements IUserService {
    @Override
    public JwtUser login(JwtUser user) {
        //将userId存入token中
//        String token = JWTUtils.createToken(user.getUsername());
//        user.setTaken(token);
        return user;
    }

}
