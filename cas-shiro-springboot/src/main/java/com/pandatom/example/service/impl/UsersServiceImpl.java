package com.pandatom.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandatom.example.entity.Users;
import com.pandatom.example.mapper.UsersMapper;
import com.pandatom.example.service.IUsersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hcly
 * @since 2022-03-29
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Resource
    private UsersMapper usersMapper;

    @Override
    public Users queryUserByUsername(String username) {
        return usersMapper.queryUserByUsername(username);
    }
}
