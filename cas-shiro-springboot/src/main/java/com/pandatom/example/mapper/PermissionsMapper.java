package com.pandatom.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandatom.example.entity.Permissions;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hcly
 * @since 2022-03-29
 */
public interface PermissionsMapper extends BaseMapper<Permissions> {
    public Set<String> queryPermissionsByUsername(String  username) ;
}
