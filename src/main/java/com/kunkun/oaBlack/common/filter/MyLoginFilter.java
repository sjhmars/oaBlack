package com.kunkun.oaBlack.common.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.kunkun.oaBlack.common.util.BizException;
import com.kunkun.oaBlack.common.util.CodeUtil;
import com.kunkun.oaBlack.common.util.RedisCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

        String bearerToken = request.getHeader("Authorization");
        if (StrUtil.isBlank(bearerToken)){
            logger.info("没传进来吗");
            filterChain.doFilter(request,response);
            return;
        }

        logger.info(bearerToken);
        String token = bearerToken.split(" ")[1];
        logger.info(token);

        // 解析出userid
        Integer Id = null;
        try{
            if (token.length()>30){
                Claims jwt = Jwts.parser()
                        .setSigningKey("mortal_su".getBytes(StandardCharsets.UTF_8))
                        .parseClaimsJws(token)
                        .getBody();
                logger.info(jwt);
                Id = (Integer) jwt.get("userid");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        System.out.println(Id);

        //查询是否已登录
        if (redisCache.getCacheObject(TOKEN_KEY+":"+Id)!=null){
            throw new RuntimeException(CodeUtil.NO_AUTH.getResultMessage());
        }
        filterChain.doFilter(request,response);
    }

}
