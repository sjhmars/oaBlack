package com.kunkun.oaBlack.module.auth.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.common.util.BizException;
import com.kunkun.oaBlack.common.util.CodeUtil;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.auth.dao.UserDao;
import com.kunkun.oaBlack.module.auth.enity.UserEnity;
import com.kunkun.oaBlack.module.auth.mapper.UserMapper;
import com.kunkun.oaBlack.module.auth.service.LoginServer;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class LoginServerImp extends ServiceImpl<UserMapper, UserEnity> implements LoginServer {

    public ResultUtil login(UserDao userDao){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,Object> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("username",userDao.getUserMobile());
        paramsMap.add("password",userDao.getUserPassword());
        paramsMap.add("grant_type","password");

        HttpEntity<MultiValueMap<String,Object>> entity = new HttpEntity<>(paramsMap,headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(
                new BasicAuthenticationInterceptor("client_sjh","hello")
        );

        ResponseEntity<OAuth2AccessToken> result;

        try{
            result = restTemplate.postForEntity("http://localhost:9999/oauth/token",entity,OAuth2AccessToken.class);
        }catch (HttpClientErrorException e){
            throw new BizException(CodeUtil.INTERNAL_SERVER_ERROR.getResultCode(),CodeUtil.INTERNAL_SERVER_ERROR.getResultMessage(),e);
        }
        if (result.getStatusCode()!= HttpStatus.OK){
            return ResultUtil.faile("登录失败");
        }
        return ResultUtil.success("登录成功",result);
    }
}
