package com.leepc.chat.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class TokenUtils {


    public static String createToken(Integer uid, String username, long ttlMillis) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        String key = Constant.JWT_SECRET;
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .claim("uid", uid)
                .claim("username", username)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static Integer getUserId(String token) {
        return (Integer)parseJWT(token).get("uid");
    }

    public static String getUsername(String token) {
        return (String)parseJWT(token).get("username");
    }

    public static Claims parseJWT(String jwt) {
        String key = Constant.JWT_SECRET;
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static boolean verify(String token) {
        if (parseJWT(token) != null){
            return true;
        }
        return false;
    }

}
