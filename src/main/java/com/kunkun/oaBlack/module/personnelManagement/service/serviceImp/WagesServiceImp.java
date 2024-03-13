package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.dao.WagesDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.WagesEntity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.WagesMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.WagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WagesServiceImp extends ServiceImpl<WagesMapper, WagesEntity> implements WagesService {

    @Autowired
    private TokenStore jwtTokenStore;

    @Autowired
    private WagesMapper wagesMapper;

    @Override
    public WagesEntity addWages(WagesDao wagesDao, Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");
        WagesEntity wagesEntity = new WagesEntity();
        wagesEntity.setCreateTime(new Date());
        wagesEntity.setCreateUserId(userId);
        wagesEntity.setIsDelete(0);
        wagesEntity.setBasicSalary(wagesDao.getBasicSalary());
        wagesEntity.setLoseMoney(0);
        wagesEntity.setMealSupplement(wagesDao.getMealSupplement());
        wagesEntity.setPerformance(wagesDao.getPerformance());
        wagesEntity.setUserId(wagesDao.getUserId());
        if (wagesMapper.insert(wagesEntity)>0){
            return wagesEntity;
        }
        return null;
    }
}
