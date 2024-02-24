package com.kunkun.oaBlack.common.config;

import com.kunkun.oaBlack.common.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.concurrent.TimeUnit;

@Configuration
public class JwtTokenStoreConfig {
    private static final String JWT_KEY = "mortal_su";
    private static final String TOKEN_KEY = "access_token:";

    @Autowired
    private RedisCache redisCache;

    //    public static SecretKey generalKey(){
//        byte[] encodedKey = Base64.getDecoder().decode(AccessTokenConfig.JWT_KEY);
//        SecretKey key = new SecretKeySpec(encodedKey,0,encodedKey.length,"AES");
//        return key;
//    }
    @Bean
    public TokenStore JwtTokenStore() {
        return new JwtTokenStore(this.jwtAccessTokenConverter()){
            @Override
            public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication auth2Authentication){
                String tokenValue = token.getValue();
                if (token.getAdditionalInformation().containsKey("jti")){
                    String jti = token.getAdditionalInformation().get("jti").toString();
                    redisCache.setCacheObject(TOKEN_KEY+jti,tokenValue,token.getExpiresIn(), TimeUnit.SECONDS);
                }
            }

            @Override
            public void removeAccessToken(OAuth2AccessToken token) {
                if (token.getAdditionalInformation().containsKey("jti")){
                    String jti = token.getAdditionalInformation().get("jti").toString();
                    redisCache.deleteObject(TOKEN_KEY+jti);
                }
            }
        };
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //配置JWT使用的秘钥
        jwtAccessTokenConverter.setSigningKey(JwtTokenStoreConfig.JWT_KEY);
        return jwtAccessTokenConverter;
    }
}

