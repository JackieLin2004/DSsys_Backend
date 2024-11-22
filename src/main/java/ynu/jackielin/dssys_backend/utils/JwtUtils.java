package ynu.jackielin.dssys_backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${spring.security.jwt.key}")
    String key;

    @Value("${spring.security.jwt.expire}")
    int expire;

    /**
     * 解析并验证 JWT Token
     *
     * @param headerToken 带有 "Bearer " 前缀的 Token 字符串
     * @return 如果验证成功，返回解析后的 DecodedJWT 对象；如果验证失败或过期，返回 null
     */
    public DecodedJWT resolveJwt(String headerToken) {
        String token = this.convertToken(headerToken);
        if (token == null || token.isEmpty()) return null;
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            Date expiresAt = verify.getExpiresAt();
            return new Date().after(expiresAt) ? null : verify;
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    /**
     * 创建 JWT Token
     *
     * @param userDetails 用户详情对象，包含用户的权限信息
     * @param id 用户 ID，作为 Token 的一个自定义声明
     * @param username 用户名，作为 Token 的一个自定义声明
     * @return 生成的 JWT 字符串
     */
    public String createJwt(UserDetails userDetails, int id, String username) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        Date expire = this.expireTime();
        return JWT.create()
                .withClaim("id", id)
                .withClaim("username", username)
                .withClaim("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .withExpiresAt(expire)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    /**
     * 计算 JWT 的过期时间
     *
     * @return 过期时间的 Date 对象
     */
    public Date expireTime() {
        Calendar calendar = Calendar.getInstance();
        // 在当前时间基础上增加过期天数（单位：小时）
        calendar.add(Calendar.HOUR, expire * 24);
        return calendar.getTime();
    }

    /**
     * 将解析后的 JWT 转换为 UserDetails 对象
     *
     * @param jwt DecodedJWT 对象，包含已解析的 Token 信息
     * @return 用户详情对象，包含用户名、权限等信息
     */
    public UserDetails toUser(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        return User
                .withUsername(claims.get("username").asString())
                .password("******")
                .authorities(claims.get("authorities").asArray(String.class))
                .build();
    }

    /**
     * 从解析后的 JWT 中提取用户 ID
     *
     * @param jwt DecodedJWT 对象，包含已解析的 Token 信息
     * @return 用户 ID
     */
    public Integer toId(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        return claims.get("id").asInt();
    }

    /**
     * 转换带有 "Bearer " 前缀的 Token 字符串为实际的 Token
     *
     * @param headerToken 带有 "Bearer " 前缀的 Token 字符串
     * @return 去除前缀后的 Token 字符串；如果格式错误，返回 null
     */
    public String convertToken(String headerToken) {
        if (headerToken == null || !headerToken.startsWith("Bearer "))
            return null;
        return headerToken.substring(7);
    }
}
