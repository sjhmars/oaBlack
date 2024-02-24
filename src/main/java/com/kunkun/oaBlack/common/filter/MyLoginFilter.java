package com.kunkun.oaBlack.common.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.kunkun.oaBlack.common.util.BizException;
import com.kunkun.oaBlack.common.util.CodeUtil;
import com.kunkun.oaBlack.common.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录拦截类，需要用到auth模块中的loginUser
 */
@Component
public class MyLoginFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    private final String TOKEN_KEY ="access_token:";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("=========进入sso前置拦截器========");

        String jti = request.getHeader("jti");
        if (StrUtil.isBlank(jti)){
            filterChain.doFilter(request,response);
            return;
        }
        if (ObjectUtil.isNull(redisCache.getCacheObject(TOKEN_KEY+jti))){
            throw new BizException(CodeUtil.NO_AUTH.getResultCode(),CodeUtil.NO_AUTH.getResultMessage());
        }
        filterChain.doFilter(request,response);
    }

}
