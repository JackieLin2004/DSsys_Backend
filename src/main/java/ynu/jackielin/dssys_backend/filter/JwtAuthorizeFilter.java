package ynu.jackielin.dssys_backend.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ynu.jackielin.dssys_backend.utils.JwtUtils;

import java.io.IOException;

/**
 * JWT 授权过滤器：用于解析 JWT 并在 Spring Security 中设置用户认证信息
 * 该过滤器在每次 HTTP 请求时运行一次。
 */
@Component
public class JwtAuthorizeFilter extends OncePerRequestFilter {

    @Resource
    JwtUtils utils;

    /**
     * 过滤器的核心方法，用于解析请求中的 JWT，并设置用户的认证信息
     *
     * @param request     HTTP 请求对象
     * @param response    HTTP 响应对象
     * @param filterChain 过滤器链，用于将请求传递给下一个过滤器
     * @throws ServletException 如果过滤器处理失败
     * @throws IOException      如果处理请求或响应时出现 IO 异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        DecodedJWT jwt = utils.resolveJwt(authorization);
        if (jwt != null) {
            UserDetails user = utils.toUser(jwt);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.setAttribute("id", utils.toId(jwt));
        }
        filterChain.doFilter(request, response);
    }
}
