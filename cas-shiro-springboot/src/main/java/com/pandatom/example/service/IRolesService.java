package com.pandatom.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pandatom.example.entity.Roles;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hcly
 * @since 2022-03-28
 */

public interface IRolesService extends IService<Roles> {
    public Set<String> queryRoleNamesByUsername(String username);
}
