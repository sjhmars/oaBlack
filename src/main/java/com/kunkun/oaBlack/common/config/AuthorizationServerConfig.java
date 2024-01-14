package com.kunkun.oaBlack.common.config;

import com.kunkun.oaBlack.module.auth.service.serviceImp.ClientDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;



    @Autowired
    private ClientDetailsServiceImp clientDetailsServiceImp;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsServiceImp); // 接入处理
//        clients.inMemory()
//                .withClient("client_sjh")
//                .secret("hello")
//                .authorizedGrantTypes("authorization_code")
//                .redirectUris("https://www.baidu.com")
//                .scopes("webapp");
    }
}
