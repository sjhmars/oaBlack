package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.enitly.PostEnity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.PostMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PostServiceImp extends ServiceImpl<PostMapper, PostEnity> implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private TokenStore jwtTokenStore;

    @Override
    public PostEnity addPost(String postName, Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");
        PostEnity postEnity = new PostEnity();
        postEnity.setCreateTime(new Date());
        postEnity.setPostName(postName);
        postEnity.setPostStatus(1);
        postEnity.setCreateByUser(userId);
        if (postMapper.insert(postEnity)>0){
            return postEnity;
        }
        return null;
    }
}
