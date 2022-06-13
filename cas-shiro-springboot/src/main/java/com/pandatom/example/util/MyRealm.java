package com.pandatom.example.util;

/**
 * @Description: TODO
 * @author: changxiong
 * @date: 2022年03月29日 11:09 AM
 */

import com.pandatom.example.entity.CommonConstant;
import com.pandatom.example.entity.Users;
import com.pandatom.example.mapper.PermissionsMapper;
import com.pandatom.example.mapper.RolesMapper;
import com.pandatom.example.mapper.UsersMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;

/**
 * 1.创建一个类继承AuthorizingRealm类（实现了Realm接口的类）
 * 2.重写doGetAuthorizationInfo和doGetAuthenticationInfo方法
 * 3.重写getName方法返回当前realm的一个自定义名称
 */
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UsersMapper userDAO;
    @Resource
    private RolesMapper roleDAO;
    @Resource
    private PermissionsMapper permissionDAO;

    @Autowired
    private RedisUtil redisUtil;

    public String getName() {
        return "myRealm";
    }


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 获取授权数据(将当前用户的角色及权限信息查询出来)
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户的用户名
        System.out.println(principalCollection.getPrimaryPrincipal());
        System.out.println(principalCollection.getRealmNames());Users user= (Users) principalCollection.getPrimaryPrincipal();
        String username = user.getUsername();
        //根据用户名查询当前用户的角色列表
        Set<String> roleNames = roleDAO.queryRoleNamesByUsername(username);
        //根据用户名查询当前用户的权限列表
        Set<String> ps = permissionDAO.queryPermissionsByUsername(username);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roleNames);
        info.setStringPermissions(ps);
        return info;
    }

    /**
     * 用户信息认证是在用户进行登录的时候进行验证(不存redis)
     * 也就是说验证用户输入的账号和密码是否正确，错误抛出异常
     *
     * @param auth 用户登录的账号密码信息
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        if (token == null) {
            throw new AuthenticationException("token为空!");
        }
        // 校验token有效性
        Users loginUser = null;
        try {
            loginUser = this.checkUserTokenIsEffect(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return null;
        }
//        return new SimpleAuthenticationInfo(loginUser,loginUser.getPassword(), ByteSource.Util.bytes(loginUser.getUsername()), getName());
        return new SimpleAuthenticationInfo(loginUser,token, getName());
    }

    /**
     * 校验token的有效性
     *
     * @param token
     */
    public Users checkUserTokenIsEffect(String token) throws AuthenticationException {
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtils.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token非法无效!");
        }

        // 查询用户信息
        Users loginUser = userDAO.queryUserByUsername(username);
        if (loginUser == null) {
            throw new AuthenticationException("用户不存在!");
        }
        // 校验token是否超时失效 & 或者账号密码是否错误
        if (!jwtTokenRefresh(token, username, loginUser.getPassword())) {
            throw new AuthenticationException(CommonConstant.TOKEN_IS_INVALID_MSG);
        }
        return loginUser;
    }

    public boolean jwtTokenRefresh(String token, String userName, String passWord) {
        String keys=CommonConstant.PREFIX_USER_TOKEN + token;
        System.out.println(keys);
        String cacheToken = String.valueOf(redisUtil.get(keys));
//        String cacheToken = "ss";
        if (!cacheToken.isEmpty()) {
            // 校验token有效性
            if (!JWTUtils.verify(cacheToken, userName, passWord)) {
                String time = JWTUtils.getTime(token);
                String newAuthorization = JWTUtils.createToken(userName,time, passWord);
                // 设置超时时间
                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, newAuthorization);
                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JWTUtils.EXPIRE_TIME * 2 / 1000);
            }

            return true;
        } else {
            return false;
        }

    }

    /**
     * 重写方法,清除当前用户的的 授权缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的  认证缓存  和 授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }



}
