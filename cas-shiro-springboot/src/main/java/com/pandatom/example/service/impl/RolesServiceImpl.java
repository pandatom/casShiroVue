package com.pandatom.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandatom.example.mapper.RolesMapper;
import com.pandatom.example.entity.Roles;
import com.pandatom.example.service.IRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hcly
 * @since 2022-03-28
 */
@Service
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles> implements IRolesService {

    @Resource
    private RolesMapper rolesMapper;

    @Override
    public Set<String> queryRoleNamesByUsername(String username) {
        return  rolesMapper.queryRoleNamesByUsername(username);
    }
}
