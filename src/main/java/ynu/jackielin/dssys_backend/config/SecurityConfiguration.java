package ynu.jackielin.dssys_backend.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import ynu.jackielin.dssys_backend.entity.RestBean;

import java.io.IOException;

/**
 * 安全配置类，用于配置 Spring Security 的核心安全功能
 */
@Configuration
public class SecurityConfiguration {

    /**
     * 配置安全过滤链，定义了接口的权限控制规则、登录、登出逻辑以及会话管理策略
     *
     * @param http HttpSecurity 对象，提供配置 Spring Security 的能力
     * @return SecurityFilterChain 安全过滤链
     * @throws Exception 如果配置出现问题抛出异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 定义无需认证的公共路径
        var publicPaths = new String[]{"/doc/**", "/auth/**", "/captcha/**"};
        return http
                // 配置接口访问权限
                .authorizeHttpRequests(conf -> conf
                        .requestMatchers(publicPaths).permitAll() // 公共路径无需认证
                        .anyRequest().authenticated() // 其他路径需要认证
                )
                // 配置表单登录
                .formLogin(conf -> conf
                        .loginProcessingUrl("/auth/login") // 登录接口路径
                        .failureHandler(this::onAuthenticationFailure) // 登录失败处理
                        .successHandler(this::onAuthenticationSuccess) // 登录成功处理
                )
                // 配置登出逻辑
                .logout(conf -> conf
                        .logoutUrl("/auth/logout") // 登出接口路径
                        .logoutSuccessHandler(this::onLogoutSuccess) // 登出成功处理
                )
                // 禁用 CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // 配置无状态会话管理策略
                .sessionManagement(conf -> conf
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    /**
     * 登录成功后的处理方法
     *
     * @param request        HttpServletRequest 对象
     * @param response       HttpServletResponse 对象
     * @param authentication Authentication 对象，包含用户的认证信息
     * @throws IOException   如果 IO 出现异常
     * @throws ServletException 如果 Servlet 出现异常
     */
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(RestBean.success().asJsonString());
    }

    /**
     * 登录失败后的处理方法
     *
     * @param request   HttpServletRequest 对象
     * @param response  HttpServletResponse 对象
     * @param exception AuthenticationException 异常，包含失败的具体原因
     * @throws IOException 如果 IO 出现异常
     * @throws ServletException 如果 Servlet 出现异常
     */
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(RestBean.failure(401, exception.getMessage()).asJsonString());
    }

    /**
     * 登出成功后的处理方法
     *
     * @param request        HttpServletRequest 对象
     * @param response       HttpServletResponse 对象
     * @param authentication Authentication 对象，包含用户的认证信息
     * @throws IOException   如果 IO 出现异常
     * @throws ServletException 如果 Servlet 出现异常
     */
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
    }
}
