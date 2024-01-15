package com.kunkun.oaBlack.common.util;

import com.kunkun.oaBlack.module.auth.dao.LoginDao;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * JWT增强类
 */
@Component
public class JwtTokenEnhancerUtil implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        LoginDao loginDao = (LoginDao) oAuth2Authentication.getPrincipal();
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("id",loginDao.getId());
        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(objectMap);
        return oAuth2AccessToken;
    }
}
