package com.pandatom.example.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandatom.example.entity.Roles;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hcly
 * @since 2022-03-28
 */
public interface RolesMapper extends BaseMapper<Roles> {
    public Set<String> queryRoleNamesByUsername(String username);
}
