package ynu.jackielin.dssys_backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${spring.security.jwt.key}")
    String key;

    @Value("${spring.security.jwt.expire}")
    int expire;

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
                .withClaim("name", username)
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
}
