package com.xiaobitipao.dungbeetle.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Jwt Token 处理
 */
@Component
public class JwtToken {

    // 默认权限组
    private static final Integer defaultScope = 8;

    // jwt 的 secret
    private static String jwtKey;

    // token 过期时间
    private static Integer expiredTimeIn;

    // 成员变量的注入直接在成员变量定义的位置即可
    // 静态成员变量的注入需要通过 set 方法注入
    @Value("${dungbeetle.security.jwt-key}")
    public void setJwtKey(String jwtKey) {
        JwtToken.jwtKey = jwtKey;
    }

    @Value("${dungbeetle.security.token-expired-time-in}")
    public void setExpiredTimeIn(Integer expiredTimeIn) {
        JwtToken.expiredTimeIn = expiredTimeIn;
    }

    /**
     * 获取 token 中的用户自定义信息
     */
    public static Optional<Map<String, Claim>> getClaims(String token) {
        DecodedJWT decodedJWT;
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            decodedJWT = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            // 这里没有区分令牌过期和无效
            // 过期和无效都认为失败
            return Optional.empty();
        }
        return Optional.of(decodedJWT.getClaims());
    }

    /**
     * token 校验
     */
    public static Boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    /**
     * 创建默认 scope 的 token
     */
    public static String makeToken(Long uid) {
        return JwtToken.getToken(uid, JwtToken.defaultScope);
    }

    /**
     * 创建指定 scope 的 token
     */
    public static String makeToken(Long uid, Integer scope) {
        return JwtToken.getToken(uid, scope);
    }

    /**
     * 获取 token
     */
    private static String getToken(Long uid, Integer scope) {
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
        Map<String, Date> expiredIssues = JwtToken.calculateExpiredIssues();
        return JWT.create()
                .withClaim("uid", uid)
                .withClaim("scope", scope)
                .withExpiresAt(expiredIssues.get("expiredTime"))
                .withIssuedAt(expiredIssues.get("now"))
                .sign(algorithm);
    }

    /**
     * 计算过期时间
     */
    private static Map<String, Date> calculateExpiredIssues() {
        Map<String, Date> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.SECOND, JwtToken.expiredTimeIn);
        map.put("now", now);
        map.put("expiredTime", calendar.getTime());
        return map;
    }
}
