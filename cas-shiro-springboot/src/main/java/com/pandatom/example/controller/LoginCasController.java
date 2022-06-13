//package com.pandatom.example.controller;
//
//
//import com.pandatom.example.entity.Record;
//import com.pandatom.example.entity.Users;
//import com.pandatom.example.mapper.UsersMapper;
//import com.pandatom.example.service.IPermissionsService;
//import com.pandatom.example.service.IRecordService;
//import com.pandatom.example.service.IRolesService;
//import com.pandatom.example.service.IUsersService;
//import com.pandatom.example.util.JWTUtils;
//import com.pandatom.example.util.JwtToken;
//import io.buji.pac4j.subject.Pac4jPrincipal;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.net.URLEncoder;
//import java.time.LocalDateTime;
//import java.util.*;
//
//
//
//@Controller
//public class LoginCasController {
//
//    @Autowired
//    private IUsersService usersService;
//    @Autowired
//    private IRolesService  rolesService;
//    @Autowired
//    private IPermissionsService permissionsService;
//    @Autowired
//    private IRecordService recordService;
//
//    /**
//     * 生成前端需要的用户信息
//     * @param jwtToken token
//     * @param username userId
//     * @return userInfo
//     */
//    private HashMap<String, Object> generateUserInfo(JwtToken jwtToken, String username) {
//
//        HashMap<String, Object> userInfo = new HashMap<>();
//
//        userInfo.put("token", jwtToken.getCredentials());
////        userInfo.put("expireTime", jwtToken.getExpireAt());
//
//        Set<String> roles = rolesService.queryRoleNamesByUsername(username);
//        userInfo.put("roles", roles);
//
//        return userInfo;
//    }
//
//
//    /**
//     * 登录认证
//     * @return 登录结果
//     */
//    @GetMapping({"/", "", "/index"})
//    public void login(HttpServletRequest request, HttpServletResponse response) throws ClassCastException {
//
//        Pac4jPrincipal principal = (Pac4jPrincipal)request.getUserPrincipal();
//        String username = (String)principal.getProfile().getId();
////        String cn = (String)principal.getProfile().getAttribute("cn");
//
//        /*
//        根据统一认证返回信息确定用户角色，并一同写入数据库
//        略
//        */
//
//        Users users = usersService.queryUserByUsername(username);
//
//        Record record = new Record();
//        record.setLoginTime(new Date());
//        record.setUsername(users.getUsername());
//        recordService.save(record);
//        String token = JWTUtils.createToken(users.getUsername(),record.getLoginTime(), users.getPassword());
//
//        JwtToken jwtToken = new JwtToken(token);
//        HashMap<String, Object> userInfo = this.generateUserInfo(jwtToken, users.getUsername());
//
//        try {
//            Set<String> set = (HashSet<String>)userInfo.get("roles");
//            String role = set.iterator().next();
//
//            //重定向到前端登录页面，http://前端服务器地址:前端项目端口（前端部署到nginx后nginx配置端口）
//            //  + "/"（注意/是前端路由中登录页面的path，并且与nginx.conf文件中的location后的/一致）+ 返回给前端的数据。
//            //注意：前端服务器地址与前端项目中config/index.js配置的  host: 'x.x.x.x',保持一致。
////            response.sendRedirect("http://localhost:3000/"+"?userName="+users.getUsername()+
////                    "&token="+userInfo.get("token")+"&roles="+role);
//            response.sendRedirect("http://localhost:3000/"+"?token="+token);
//        } catch (Exception e) {
//            System.out.println("LoginController中的异常");
//            e.printStackTrace();
//        }
//
//    }
//
//
//}
