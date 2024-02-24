package com.kunkun.oaBlack.common.config;

import com.kunkun.oaBlack.common.filter.MyLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) //开启方法级别权限控制
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private MyLoginFilter myLoginFilter;

    public void configure(ResourceServerSecurityConfigurer resources) throws Exception{
        resources
                // 无效登录信息使用authenticationEntryPoint拦截器
                .authenticationEntryPoint(authenticationEntryPoint)
                // 权限不足使用accessDeniedHandler拦截器
                .accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] webRelease = {
                "/swagger-ui.html",
                "/swagger-ui/*",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/webjars/**",
                "/auth/**",
                "/oauth/authorize?**",
                "/oauth/token?**"
        };

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(webRelease).permitAll() //许可中的地址全部通过
                .anyRequest().authenticated(); // 其他地址全部拦截
//        http.addFilterBefore(myLoginFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
    }

}
