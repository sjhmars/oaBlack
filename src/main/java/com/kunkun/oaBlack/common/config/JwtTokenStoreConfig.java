package com.kunkun.oaBlack.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class JwtTokenStoreConfig {
    private static final String JWT_KEY = "mortal_su";

    //    public static SecretKey generalKey(){
//        byte[] encodedKey = Base64.getDecoder().decode(AccessTokenConfig.JWT_KEY);
//        SecretKey key = new SecretKeySpec(encodedKey,0,encodedKey.length,"AES");
//        return key;
//    }
    @Bean
    public TokenStore JwtTokenStore() {
        return new JwtTokenStore(this.jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //配置JWT使用的秘钥
        jwtAccessTokenConverter.setSigningKey(JwtTokenStoreConfig.JWT_KEY);
        return jwtAccessTokenConverter;
    }
}

