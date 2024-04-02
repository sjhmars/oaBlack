package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.dao.MakeUpCheckDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.SupplementCheckEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.SupplementCheckMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import com.kunkun.oaBlack.module.personnelManagement.service.SupplementCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

@Service
public class SupplementCheckServiceImp extends ServiceImpl<SupplementCheckMapper, SupplementCheckEntity> implements SupplementCheckService {

    @Autowired
    private PersonUserService personUserService;

    @Autowired
    private TokenStore jwtTokenStore;

    @Override
    public SupplementCheckEntity addSupplementCheck(Authentication authentication, MakeUpCheckDao makeUpCheckDao) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");
        UserEnity cUser = personUserService.selectByIdMy(userId);
        UserEnity rUser = personUserService.selectByIdMy(makeUpCheckDao.getReviewerUserId());


        return null;
    }
}
