package com.pandatom.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pandatom.example.entity.Users;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hcly
 * @since 2022-03-29
 */
public interface IUsersService extends IService<Users> {
    public Users queryUserByUsername(String username);
}
