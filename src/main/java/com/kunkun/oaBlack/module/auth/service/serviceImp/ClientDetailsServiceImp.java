package com.kunkun.oaBlack.module.auth.service.serviceImp;

//import org.springframework.security.oauth2.provider.ClientDetails;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.ClientRegistrationException;
//import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class ClientDetailsServiceImp implements ClientDetailsService {
    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        BaseClientDetails clientDetails = new BaseClientDetails(); //系统默认实现的ClientDetails子类
        clientDetails.setClientId("client_sjh"); // 此为demo正常应该是随机生成
        clientDetails.setClientSecret("hello"); // 客户端密码需要加密处理。
        clientDetails.setAuthorizedGrantTypes(Arrays.asList("authorization_code","password"));
        clientDetails.setScope(Arrays.asList("all")); // 配置放行所有权限
        clientDetails.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); //一天有效期
        // clientDetails.setAccessTokenValiditySeconds(3000);
        clientDetails.setAutoApproveScopes(clientDetails.getScope()); // 自动处理授权访问
        Set<String> redirectSet = new HashSet<>();
        redirectSet.addAll(Arrays.asList("http://localhost:9999/oauth/test"));
        clientDetails.setRegisteredRedirectUri(redirectSet); // 配置已认证返回路径
        return clientDetails;
    }
}
