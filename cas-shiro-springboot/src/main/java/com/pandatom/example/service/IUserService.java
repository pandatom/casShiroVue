package com.pandatom.example.service;

import com.pandatom.example.entity.JwtUser;

import java.util.Map;

public interface IUserService {
    public JwtUser login(JwtUser user);
}
