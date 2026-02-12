package com.bite.common.security.service;


import cn.hutool.core.lang.UUID;
import com.bite.common.core.constants.CacheConstants;
import com.bite.common.core.constants.JwtConstants;
import com.bite.common.redis.service.RedisService;
import com.bite.common.core.domain.LoginUser;
import com.bite.common.core.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//操作用户登录token的方法
@Service
@Slf4j
public class TokenService {

    @Autowired
    private RedisService redisService;

    public String creatToken(Long userId, String secret, Integer identity, String nickName){
        Map<String, Object> claims = new HashMap<>();
        String userKey = UUID.fastUUID().toString();
        claims.put(JwtConstants.LOGIN_USER_ID, userId);
        claims.put(JwtConstants.LOGIN_USER_KEY, userKey);
        String token = JwtUtils.createToken(claims, secret);
        //第三方机制中存放敏感信息

        //身份认证具体还要存储哪些信息 redis 表明用户身份的字段 identity 1表示普通用户   2表示管理员用户  对象

        //使用什么样的数据结构
        //key 必须保证唯一   便于维护 统一前缀：logintoken:userId  userId是通过雪花算法生成的
        //自增  管理员 c端用户
        String tokenKey = getTokenKey(userKey);
        //String key = "logintoken:" + sysUser.getUserId();
        LoginUser loginUser = new LoginUser();
        loginUser.setIdentity(identity);
        loginUser.setNickName(nickName);
        redisService.setCacheObject(tokenKey, loginUser, CacheConstants.EXP, TimeUnit.MINUTES);

        return token;

    }


    //延长token 的有效时间 就是延长redis中存储用户敏感信息的有效时间    操作redis，通过token拿到唯一标识

    //在身份认证通过之后再调用，在调用controller之前  在拦截器中调用
    public void extendToken(String token, String secret){
//        Claims claims;
//
//        try {
//            claims = JwtUtils.parseToken(token, secret); //获取令牌中信息 解析payload中信息  存储着用户的唯一标识信息
//            if (claims == null) {
//                log.error("解析token：{}, 出现异常", token);
//                return;
//            }
//        } catch (Exception e) {
//            log.error("解析token：{}, 出现异常", token, e);
//            return;
//        }
//        String userKey = JwtUtils.getUserKey(claims); //获取jwt中的key
        String userKey = getUserKey(token, secret);
        if(userKey == null){
            return;
        }
        String tokenKey = getTokenKey(userKey);

        //720min  12小时      剩余  180min  的时候进行延长
        Long expire = redisService.getExpire(tokenKey, TimeUnit.MINUTES);
        if(expire != null && expire < CacheConstants.REFRESH_TIME){
            redisService.expire(tokenKey, CacheConstants.EXP, TimeUnit.MINUTES);
        }
    }

    private String getUserKey(String token, String secret) {
        Claims claims;

        try {
            claims = JwtUtils.parseToken(token, secret); //获取令牌中信息 解析payload中信息  存储着用户的唯一标识信息
            if (claims == null) {
                log.error("解析token：{}, 出现异常", token);
                return null;
            }
        } catch (Exception e) {
            log.error("解析token：{}, 出现异常", token, e);
            return null;
        }
        return JwtUtils.getUserKey(claims); //获取jwt中的key
    }

    private String getTokenKey(String userKey){
        return CacheConstants.LOGIN_TOKEN_KEY + userKey;
    }

    public LoginUser getLoginUser(String token, String secret) {
        String userKey = getUserKey(token, secret);
        if(userKey == null){
            return null;
        }
        return redisService.getCacheObject(getTokenKey(userKey), LoginUser.class);
    }

    public boolean deleteLoginUser(String token, String secret) {
        String userKey = getUserKey(token, secret);
        if(userKey == null){
            return false;
        }
        return redisService.deleteObject(getTokenKey(userKey));
    }
}
