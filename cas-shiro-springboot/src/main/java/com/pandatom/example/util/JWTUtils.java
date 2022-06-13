package com.pandatom.example.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**

 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）,默认：{"alg": "HS512","typ": "JWT"}
 * payload的格式 设置：（用户信息、创建时间、生成时间）
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 */

public class JWTUtils {

    public static final String USER_LOGIN_TOKEN = "USER_LOGIN_TOKEN";

    public final static int EXPIRE_TIME = 1; /**  单位 年 */


    public static String createToken(String userName,String time,String secret) {
        //设置令牌的过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.YEAR, EXPIRE_TIME);
        //创建JWT builder
        JWTCreator.Builder builder = JWT.create();

        Map<String, Object> header = new HashMap();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        builder.withHeader(header);

        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return builder.withClaim("username", userName).withClaim("time",time).withExpiresAt(date).sign(algorithm);
    }

    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String getTime(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("time").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }



    /**
     * 验证token
     * @param token
     */
    public static String validateToken(String token,String secret){
        try {
            return JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (TokenExpiredException e){
            throw new ApiException("token已经过期");
        } catch (Exception e){
            throw new ApiException("token验证失败");
        }
    }

    /**
     * 检查token是否需要更新
     * @param token
     * @return
     */
    public static boolean isNeedUpdate(String token,String secret){
        //获取token过期时间
        Date expiresAt = null;
        try {
            expiresAt = JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token)
                    .getExpiresAt();
        } catch (TokenExpiredException e){
            return true;
        } catch (Exception e){
            throw new ApiException("token验证失败");
        }
        //如果剩余过期时间少于过期时常的一般时 需要更新
        return (expiresAt.getTime()-System.currentTimeMillis()) < (EXPIRE_TIME>>1);
    }
}


