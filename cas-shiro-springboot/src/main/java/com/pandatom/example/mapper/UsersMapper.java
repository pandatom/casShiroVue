package com.pandatom.example.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandatom.example.entity.Users;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hcly
 * @since 2022-03-29
 */
public interface UsersMapper extends BaseMapper<Users> {
    public Users queryUserByUsername(String username);
}
