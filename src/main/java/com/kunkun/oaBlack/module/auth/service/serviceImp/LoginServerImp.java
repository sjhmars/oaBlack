package com.kunkun.oaBlack.module.auth.service.serviceImp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.common.util.BizException;
import com.kunkun.oaBlack.common.util.CodeUtil;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.auth.dao.UserDao;
import com.kunkun.oaBlack.module.auth.enity.UserEnity;
import com.kunkun.oaBlack.module.auth.mapper.UserMapper;
import com.kunkun.oaBlack.module.auth.service.LoginServer;
import com.kunkun.oaBlack.module.auth.service.UserService;
import com.kunkun.oaBlack.module.auth.vo.loginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;


@Service
public class LoginServerImp extends ServiceImpl<UserMapper, UserEnity> implements LoginServer {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenStore jwtTokenStore;

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
        Map<String,Object> resultMap;
        String accessToken;
        try{
            result = restTemplate.postForEntity("http://localhost:9999/oauth/token",entity,OAuth2AccessToken.class);
            accessToken = Objects.requireNonNull(result.getBody()).getValue();
            resultMap = result.getBody().getAdditionalInformation();
        }catch (HttpClientErrorException e){
            throw new BizException(CodeUtil.INTERNAL_SERVER_ERROR.getResultCode(),CodeUtil.INTERNAL_SERVER_ERROR.getResultMessage(),e);
        }
        if (result.getStatusCode()!= HttpStatus.OK){
            log.error("密码模式挂了",new RuntimeException());
            return ResultUtil.faile("登录失败,密码模式挂了");
        }
        UserEnity userEnity = userService.selectById((Integer) resultMap.get("userid"));
        loginVo login_vo = new loginVo();
        login_vo.setLastLoginAddress(userEnity.getLastLoginAddress());
        userEnity.setLastLoginAddress(userDao.getLoginAddress());
//        System.out.println(userEnity.getMobile());
        login_vo.setAccess_token(accessToken);
        login_vo.setUserName(userEnity.getUserName());
        login_vo.setNickname(userEnity.getNickname());
        login_vo.setRoleName(userEnity.getRoleName());
        if (userEnity.getLastLoginTime()!=null){
            login_vo.setLastLoginTime(userEnity.getLastLoginTime().getTime());
        }else {
            login_vo.setLastLoginAddress(null);
        }
        if (ObjectUtil.isNotEmpty(userService.updateLoginTimeAndAddress(userEnity))){
            log.debug("登录时间和登录地点更新成功");
        }else{
            log.warn("登录时间和登录地点更新失败");
        }
        return ResultUtil.success("登录成功",login_vo);
    }
    @Override
    public ResultUtil loginOut(Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        //SpringSecurity为我们提供的readAccessToken方法,参数为用户的令牌，返回值为封装好的OAuth2AccessToken对象
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        //在jwt方式中removeAccessToken是一个空实现方法，但是为了做到能够退出的功能，我们重写了这个方法，目的是在redis中删除掉
        //每一个jwt添加了自定义的唯一userid，这样配合userid的拦截器就能做到用户退出的功能了。
        jwtTokenStore.removeAccessToken(oAuth2AccessToken);
        return ResultUtil.success("退出成功");
    }
}
