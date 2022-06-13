//package com.pandatom.example.util;
//
//
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.pandatom.example.entity.Users;
//import com.pandatom.example.mapper.PermissionsMapper;
//import com.pandatom.example.mapper.RolesMapper;
//import com.pandatom.example.mapper.UsersMapper;
//import io.buji.pac4j.realm.Pac4jRealm;
//import io.buji.pac4j.subject.Pac4jPrincipal;
//import io.buji.pac4j.token.Pac4jToken;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.crypto.hash.Md5Hash;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.subject.SimplePrincipalCollection;
//import org.pac4j.core.profile.CommonProfile;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.annotation.Resource;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * 认证与授权
// * @author gongtao
// * @version 2018-03-30 13:55
// **/
//public class CasRealm extends Pac4jRealm {
//    private final static Logger log = LoggerFactory.getLogger(CasRealm.class);
//    private String clientName;
//
//    @Resource
//    private UsersMapper userDAO;
//    @Resource
//    private RolesMapper roleDAO;
//    @Resource
//    private PermissionsMapper permissionDAO;
//
//
//    public String getClientName() {
//        return clientName;
//    }
//
//    public void setClientName(String clientName) {
//        this.clientName = clientName;
//    }
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return token instanceof JwtToken||token instanceof AuthenticationToken;
//    }
//
//    /**
//     * 认证
//     * @param authenticationToken
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
////        System.out.println(authenticationToken.getClass().getName());
//        if (!(authenticationToken instanceof JwtToken)) {
//            final Pac4jToken pac4jToken = (Pac4jToken) authenticationToken;
//            final List<CommonProfile> commonProfileList = pac4jToken.getProfiles();
//            final CommonProfile commonProfile = commonProfileList.get(0);
//            System.out.println("单点登录返回的信息" + commonProfile.toString());
//            //todo
//            final Pac4jPrincipal principal = new Pac4jPrincipal(commonProfileList, getPrincipalNameAttribute());
//            final PrincipalCollection principalCollection = new SimplePrincipalCollection(principal, getName());
//            return new SimpleAuthenticationInfo(principalCollection, commonProfileList.hashCode());
//        } else {
//            // 这里的 token是从 JWTFilter 的 executeLogin 方法传递过来的，已经经过了解密
////            System.out.println(authenticationToken.getCredentials());
//            String token = (String)authenticationToken.getCredentials();
//            String username = JWTUtils.getUsername(token);
//
//            if (StringUtils.isBlank(username)) {
//                throw new AuthenticationException("token校验不同过");
//            }
//
//            Users users = userDAO.queryUserByUsername(username);
//            if (users == null) {
//                throw new AuthenticationException("用户名或密码错误");
//            }
////            if (!JWTUtils.verify(token, username, users.getPassword())) {
////                throw new AuthenticationException("token校验不通过");
////            }
//            return new SimpleAuthenticationInfo(token, token, getName());
//        }
//    }
//
//    /**
//     * 授权/验权（todo 后续有权限在此增加）
//     * @param principals
//     * @return
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println(principals);
//        String username = JWTUtils.getUsername(principals.toString());;
//        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
//
//        //获取用户角色集合
//        Set<String> roleSet = roleDAO.queryRoleNamesByUsername(username);
//        authInfo.setRoles(roleSet);
//
//        //获取用户权限集合
//        Set<String> permissionSet = permissionDAO.queryPermissionsByUsername(username);
//        authInfo.setStringPermissions(permissionSet);
//
//        return authInfo;
//    }
//
//    /**
//     * 清理缓存权限
//     */
//    public void clearCachedAuthorizationInfo()
//    {
//        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
//    }
//}
