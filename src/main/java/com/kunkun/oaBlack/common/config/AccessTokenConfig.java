package com.kunkun.oaBlack.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class AccessTokenConfig {
    private static final String JWT_KEY = "mortal_su";

//    public static SecretKey generalKey(){
//        byte[] encodedKey = Base64.getDecoder().decode(AccessTokenConfig.JWT_KEY);
//        SecretKey key = new SecretKeySpec(encodedKey,0,encodedKey.length,"AES");
//        return key;
//    }
    @Bean
    @Primary
    public TokenStore tokenStore(){
        return new JwtTokenStore(this.accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(AccessTokenConfig.JWT_KEY);
        return converter;
    }
}
