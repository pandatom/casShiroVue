package com.pandatom.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pandatom.example.entity.Permissions;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hcly
 * @since 2022-03-29
 */
public interface IPermissionsService extends IService<Permissions> {
    public Set<String> queryPermissionsByUsername(String  username) ;
}
