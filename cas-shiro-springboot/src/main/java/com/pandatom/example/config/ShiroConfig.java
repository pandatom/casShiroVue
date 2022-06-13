package com.pandatom.example.config;

import com.pandatom.example.config.session.ShiroSessionListener;
import com.pandatom.example.expection.GlobalExceptionResolver;
import com.pandatom.example.interceptor.JwtFilter;
import com.pandatom.example.util.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @author: changxiong
 * @date: 2022年03月29日 11:09 AM
 */
@Configuration
public class ShiroConfig {


    /**
     *加密设置
     * @return
     */
//    @Bean
//    public HashedCredentialsMatcher getHashedCredentialsMatcher(){
//        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
//        //matcher就是用来指定加密规则
//        //加密方式
//        matcher.setHashAlgorithmName("md5");
//        //hash次数
//        matcher.setHashIterations(1);	//此处的循环次数要与用户注册是密码加密次数一致
//        return matcher;
//    }
    //自定义Realm
    @Bean
//    public MyRealm getMyRealm( HashedCredentialsMatcher matcher){
    public MyRealm getMyRealm(){
        MyRealm myRealm = new MyRealm();
        // 添加密码设置
//        myRealm.setCredentialsMatcher(matcher);

        myRealm.setCachingEnabled(true);
        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        myRealm.setAuthenticationCachingEnabled(true);
        //缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
        myRealm.setAuthenticationCacheName("authenticationCache");
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
        myRealm.setAuthorizationCachingEnabled(true);
        //缓存AuthorizationInfo信息的缓存名称  在ehcache-shiro.xml中有对应缓存的配置
        myRealm.setAuthorizationCacheName("authorizationCache");

        return myRealm;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(MyRealm myRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);

        //shiro加入ehCache
//        securityManager.setCacheManager(getEhCacheManager());
        //配置redis缓存
        securityManager.setCacheManager(redisCacheManager());
        // shiro加入session
//        securityManager.setSessionManager(getDefaultWebSessionManager());
        //配置自定义session管理，使用ehcache 或redis
        securityManager.setSessionManager(sessionManager());

        //设置remember管理器
//        securityManager.setRememberMeManager(cookieRememberMeManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        //过滤器就是shiro就行权限校验的核心，进行认证和授权是需要SecurityManager的
        filter.setSecurityManager(securityManager);

        Map<String,String> filterMap = new HashMap<>();
        filterMap.put("/index.html","anon");
        filterMap.put("/login","anon");
        filterMap.put("/logout","anon");
        filterMap.put("/register","anon");
        filterMap.put("/validateLogin","anon");
//        filterMap.put("/layui/**","anon");
//        filterMap.put("/**","authc");

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> jwtMap = new HashMap<String, Filter>(1);
        //如果cloudServer为空 则说明是单体 需要加载跨域配置【微服务跨域切换】
        jwtMap.put("jwt", new JwtFilter());
        filter.setFilters(jwtMap);
        // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        filterMap.put("/**", "jwt");

        filter.setFilterChainDefinitionMap(filterMap);
        filter.setLoginUrl("/login.html");
        //设置未授权访问的页面路径（）
        filter.setUnauthorizedUrl("/login.html");
        return filter;
    }

    /**
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


    // session配置  stater
    /**
     * 配置session监听
     * @return
     */
    @Bean("sessionListener")
    public ShiroSessionListener sessionListener(){
        ShiroSessionListener sessionListener = new ShiroSessionListener();
        return sessionListener;
    }
    /**
     * 配置会话ID生成器
     * @return
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
     * MemorySessionDAO 直接在内存中进行会话维护
     * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     * @return
     */
    @Bean
    public SessionDAO sessionDAO(){
//        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO=new EnterpriseCacheSessionDAO();
//        //使用ehCacheManager
//        enterpriseCacheSessionDAO.setCacheManager(getEhCacheManager());
//        //设置session缓存的名字 默认为 shiro-activeSessionCache
//        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
//        //sessionId生成器
//        enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator());
//        return enterpriseCacheSessionDAO;

        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        //session在redis中的保存时间,最好大于session会话超时时间
        redisSessionDAO.setExpire(12000);
        return redisSessionDAO;

    }

    /**
     * 配置保存sessionId的cookie
     * 注意：这里的SessionIdCookie 是新建的一个SimpleCookie对象,不是之前整合记住我的那个rememberMeCookie 如果配错了，
     * 就会出现session经典问题：每次请求都是一个新的session 并且后台报以下异常，解析的时候报错.因为记住我cookie是加密的用户信息,
     * 所以报解密错误
     * org.apache.shiro.crypto.CryptoException: Unable to execute 'doFinal' with cipher instance [javax.crypto.Cipher@461df537].
     * @return
     */
    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie(){
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    /**
     * 配置会话管理器，设定会话超时及保存
     * @return
     */
    /**
     * 配置会话管理器，设定会话超时及保存
     * @return
     */
    @Bean("sessionManager")
    public SessionManager sessionManager() {

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        //配置监听
        listeners.add(sessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionDAO(sessionDAO());
//        sessionManager.setCacheManager(getEhCacheManager());
        sessionManager.setCacheManager(redisCacheManager());


        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
//        sessionManager.setGlobalSessionTimeout(10000);
        sessionManager.setGlobalSessionTimeout(10000*6*30);
        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        //暂时设置为 5秒 用来测试
//        sessionManager.setSessionValidationInterval(5000);

        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);

        return sessionManager;

    }

    // session配置  end -------


    // 整合redis 配置  stater -------
    /**
     * shiro缓存管理器;
     * 需要添加到securityManager中
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存
        redisCacheManager.setPrincipalIdFieldName("username");
        //用户权限信息缓存时间
        redisCacheManager.setExpire(200000);
        return redisCacheManager;
    }

    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("127.0.0.1");
        redisManager.setPort(6379);
//        redisManager.setPassword("123456");
        return redisManager;
    }


    // 整合redis 配置  end -------


    //    @Bean
//    public ShiroDialect getShiroDialect(){
//        return new ShiroDialect();
//    }



    /**
     * 配置Shiro生命周期处理器
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 缓存
     * @return
     */
//    @Bean
//    public EhCacheManager getEhCacheManager(){
//        EhCacheManager ehCacheManager = new EhCacheManager();
//        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
//        return ehCacheManager;
//    }

//    @Bean
//    public DefaultWebSessionManager getDefaultWebSessionManager(){
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        System.out.println("----------"+sessionManager.getGlobalSessionTimeout()); // 1800000
//        //配置sessionManager
//        sessionManager.setGlobalSessionTimeout(5*60*1000);
//        return sessionManager;
//    }

//    @Bean
//    public CookieRememberMeManager cookieRememberMeManager(){
//        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
//
//        //cookie必须设置name
//        SimpleCookie cookie = new SimpleCookie("rememberMe");
//        cookie.setMaxAge(30*24*60*60);
//
//        rememberMeManager.setCookie(cookie);
//        return  rememberMeManager;
//    }

    @Bean(name = "exceptionHandler")
    public HandlerExceptionResolver handlerExceptionResolver(){
        return new GlobalExceptionResolver();
    }



}
