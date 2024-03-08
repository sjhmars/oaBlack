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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
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

    @Autowired
    private TokenStore jwtTokenStore;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("=========进入sso前置拦截器========");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (ObjectUtil.isNull(authentication)){
            logger.info("没传进来吗");
            filterChain.doFilter(request,response);
            return;
        }

        OAuth2AuthenticationDetails oAuth2Authentication = null;
        if (authentication instanceof OAuth2Authentication) {
            if (ObjectUtil.isNotNull(authentication)){
                oAuth2Authentication = (OAuth2AuthenticationDetails) authentication.getDetails();
            }
        }

        String token = null;

        if (oAuth2Authentication != null){
            token = oAuth2Authentication.getTokenValue();
        }

        logger.info(token);

        String Id = null;
        try{
            if (StrUtil.isNotBlank(token)){
                OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(token);
                // 解析出userid
                Id = oAuth2AccessToken.getAdditionalInformation().get("userid").toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BizException("token非法");
        }

        System.out.println(Id);
        //查询是否已登录
        if (Id!=null && redisCache.getCacheObject(TOKEN_KEY+Id)==null){
            throw new BizException(CodeUtil.NO_AUTH.getResultMessage());
        }
        filterChain.doFilter(request,response);
    }

}
