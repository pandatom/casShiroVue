package com.pandatom.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandatom.example.entity.Permissions;
import com.pandatom.example.mapper.PermissionsMapper;
import com.pandatom.example.service.IPermissionsService;
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
 * @since 2022-03-29
 */
@Service
public class PermissionsServiceImpl extends ServiceImpl<PermissionsMapper, Permissions> implements IPermissionsService {

    @Resource
    private PermissionsMapper permissionsMapper;

    @Override
    public Set<String> queryPermissionsByUsername(String username) {
        return permissionsMapper.queryPermissionsByUsername(username);
    }
}
