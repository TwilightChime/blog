package com.twilightchime.blog.utils;

//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
import com.twilightchime.blog.vo.UserDetailVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    private static final long EXPIRE_TIME= 10*60*60*1000;
    private static final String TOKEN_SECRET="ChillChime";  //密钥盐
    private static final String secret="myblog-jwt-secret-key-for-chime-personal-blog-2026";

    public String generateToken(String userName, Long userId, List<String> userType){
        return Jwts.builder()
                .subject(userName)
                .claim("userId", userId)
                .claim("roles", userType)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public Claims parseToken(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

//    /**
//     * 签名生成
//     * @param user
//     * @return
//     */
//    public static String sign(UserDetailVO user){
//        String token = null;
//        try {
//            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
//            token = JWT.create()
//                    .withIssuer("auth0")
//                    .withClaim("userId", user.getId().toString())
//                    .withClaim("userType", user.getType())
//                    .withExpiresAt(expiresAt)
//                    // 使用了HMAC256加密算法。
//                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return token;
//    }
//
//    /**
//     * 签名验证
//     * @param token
//     * @return
//     */
//    public static boolean verify(String token){
//        try {
//            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
//            DecodedJWT jwt = verifier.verify(token);
//            System.out.println("认证通过：");
//            System.out.println("userId: " + jwt.getClaim("userId").asString());
//            System.out.println("userType: " + jwt.getClaim("userType").asString());
//            System.out.println("过期时间：      " + jwt.getExpiresAt());
//            return true;
//        } catch (Exception e){
//            return false;
//        }
//    }
//
//    /**
//     * 管理员认证
//     * @param token
//     * @return
//     */
//    public static boolean adminVerify(String token){
//        try {
////            verify(token);
//            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
//            DecodedJWT jwt = verifier.verify(token);
//            if ( "1".equals(jwt.getClaim("userType").asString())){
//                System.out.println("管理员认证通过");
//                return true;
//            }
//            return false;
//        } catch (Exception e){
//            return false;
//        }
//    }
}
