package com.bite.common.security.utils;

import com.bite.common.core.constants.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    /**
     * ⽣成令牌
     *
     * @param claims 数据
     * @param secret 密钥
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims, String secret)
    {
        String token =
                Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512,
                        secret).compact();
        return token;
    }
    /**
     * 从令牌中获取数据
     *
     * @param token 令牌
     * @param secret 密钥
     * @return 数据
     */
    public static Claims parseToken(String token, String secret) {
        return
                Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public static void main(String[] args) {
//        Map<String, Object> claims = new HashMap<String, Object>();
//        claims.put("uerId", 123456789L);
//
//        System.out.println(createToken(claims, "123456"));

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1ZXJJZCI6MTIzNDU2Nzg5fQ.poXV80U-0CfqYODYLwRxSwvv2gir8dJPksRABD1tZ_GczNvRCzPx91LMMrZrJ84Dz7ZIaU0EEtyohYyChSzQtg";
        Claims claims = parseToken(token, "123456");
        System.out.println(claims);

        //1.用户登陆成功之后，调用creatToken生成token令牌， 并发送给客户端
        //2.后续的所有请求，在调用具体的接口之前都要先通过token进行身份认证
        //3.用户使用系统的过程中我们需要进行适时的延长jwt的过期时间
    }


    public static String getUserKey(Claims claims) {
        Object value = claims.get(JwtConstants.LOGIN_USER_KEY);
        if (value != null) {
            return "";
        }

        return value.toString();
    }
}
