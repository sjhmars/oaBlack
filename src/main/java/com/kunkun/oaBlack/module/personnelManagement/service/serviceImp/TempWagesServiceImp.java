package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.dao.TempWagesDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.TempWagesEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.TempWagesMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import com.kunkun.oaBlack.module.personnelManagement.service.TempWagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TempWagesServiceImp extends ServiceImpl<TempWagesMapper, TempWagesEntity> implements TempWagesService {

    @Autowired
    private TokenStore jwtTokenStore;

    @Autowired
    private TempWagesMapper tempWagesMapper;

    @Autowired
    private PersonUserService personUserService;

    @Override
    @Transactional
    public TempWagesEntity setTempWages(Authentication authentication, TempWagesDao tempWagesDao) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");

        TempWagesEntity tempWagesEntity = new TempWagesEntity();
        tempWagesEntity.setBasicSalary(tempWagesDao.getBasicSalary());
        tempWagesEntity.setCreateUserId(userId);
        tempWagesEntity.setMealSupplement(tempWagesDao.getMealSupplement());
        tempWagesEntity.setIsDelete(0);
        tempWagesEntity.setPerformance(tempWagesDao.getPerformance());
        tempWagesEntity.setUserId(tempWagesDao.getUserId());
        UserEnity userEnity = personUserService.selectByIdMy(tempWagesDao.getUserId());
        tempWagesEntity.setUserName(userEnity.getUserName());
        tempWagesEntity.setUpdateTime(new Date());

        tempWagesMapper.insert(tempWagesEntity);
        return tempWagesEntity;
    }

    @Override
    @Transactional
    public TempWagesEntity updateAllById(Authentication authentication, TempWagesDao tempWagesDao) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");

        TempWagesEntity tempWagesEntity = tempWagesMapper.selectOne(new LambdaUpdateWrapper<TempWagesEntity>()
                .eq(TempWagesEntity::getTempWagesId,tempWagesDao.getTempWagesId())
                .eq(TempWagesEntity::getIsDelete,0));
        if (tempWagesDao.getBasicSalary()!=null){
            tempWagesEntity.setBasicSalary(tempWagesDao.getBasicSalary());
        }
        if (tempWagesDao.getMealSupplement()!=null){
            tempWagesEntity.setMealSupplement(tempWagesDao.getMealSupplement());
        }
        if (tempWagesDao.getPerformance()!=null){
            tempWagesEntity.setPerformance(tempWagesDao.getPerformance());
        }
        tempWagesEntity.setUpdateByUserId(userId);
        tempWagesEntity.setUpdateTime(new Date());
        int rows = tempWagesMapper.update(tempWagesEntity,new LambdaUpdateWrapper<TempWagesEntity>().eq(TempWagesEntity::getTempWagesId,tempWagesDao.getTempWagesId()));
        if (rows>0){
            return tempWagesEntity;
        }
        return null;
    }
}
