package com.bite.common.security.service;


import cn.hutool.core.lang.UUID;
import com.bite.common.core.constants.CacheConstants;
import com.bite.common.core.constants.JwtConstants;
import com.bite.common.core.enums.UserIdentity;
import com.bite.common.redis.service.RedisService;
import com.bite.common.security.domain.LoginUser;
import com.bite.common.security.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//操作用户登录token的方法
@Service
public class TokenService {

    @Autowired
    private RedisService redisService;

    public String creatToken(Long userId, String secret, Integer identity){
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
        String key = CacheConstants.LOGIN_TOKEN_KEY + userKey;
        //String key = "logintoken:" + sysUser.getUserId();
        LoginUser loginUser = new LoginUser();
        loginUser.setIdentity(identity);
        redisService.setCacheObject(key, loginUser, CacheConstants.EXP, TimeUnit.MINUTES);

        return token;

    }
}
