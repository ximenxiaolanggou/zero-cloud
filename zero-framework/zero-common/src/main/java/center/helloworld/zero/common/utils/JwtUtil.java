package center.helloworld.zero.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

/**
 * JWT 工具包
 */
public class JwtUtil {


    /**
     *
     * @param secret 秘钥
     * @param id id
     * @param payloads 载体信息
     * @param expireAt 过期日期
     * @return
     */
    public static String createToken(String secret, String id, Map<String, String> payloads, Date expireAt) {
        JWTCreator.Builder jwtBuilder = JWT.create();
        jwtBuilder.withJWTId(id);
        payloads.forEach((k,v) -> jwtBuilder.withClaim(k, v));
        if(expireAt != null) {
            jwtBuilder.withExpiresAt(expireAt);//指定令牌的过期时间
        }
        return jwtBuilder.sign(Algorithm.HMAC256(secret));
    }

    /**
     * 从token中获取载体信息
     * @param token
     * @return
     */
    public static DecodedJWT verify(String secret, String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }
}
