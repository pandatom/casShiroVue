package com.pandatom.example.entity;

import lombok.Data;

/**
 * @Description: TODO
 * @author: changxiong
 * @date: 2022年03月28日 3:25 PM
 */
@Data
public class JwtUser {
    private String username;
    private String password;
    private String taken;
}
