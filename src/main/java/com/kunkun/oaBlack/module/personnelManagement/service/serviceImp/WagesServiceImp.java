package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.dao.PagesDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.WageNameDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.WagesDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.WagesEntity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.WagesMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import com.kunkun.oaBlack.module.personnelManagement.service.WagesService;
import com.kunkun.oaBlack.module.personnelManagement.vo.MyWageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WagesServiceImp extends ServiceImpl<WagesMapper, WagesEntity> implements WagesService {

    @Autowired
    private TokenStore jwtTokenStore;

    @Autowired
    private WagesMapper wagesMapper;

    @Autowired
    private PersonUserService personUserService;

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
        UserEnity userEnity = personUserService.selectByIdMy(wagesDao.getUserId());
        wagesEntity.setUserName(userEnity.getUserName());
        if (wagesMapper.insert(wagesEntity)>0){
            return wagesEntity;
        }
        return null;
    }

    @Override
    public MyWageVo getMyWages(Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");
        WagesEntity wagesEntitys = wagesMapper.selectOne(new LambdaUpdateWrapper<WagesEntity>().eq(WagesEntity::getUserId,userId));
        MyWageVo wageVo = new MyWageVo();
        if(wagesEntitys!=null){
            wageVo.setBasicSalary(wagesEntitys.getBasicSalary());
            wageVo.setCreateTime(wagesEntitys.getCreateTime().getTime());
            wageVo.setLoseMoney(wagesEntitys.getLoseMoney());
            wageVo.setMealSupplement(wagesEntitys.getMealSupplement());
            wageVo.setPerformance(wagesEntitys.getPerformance());
            wageVo.setUserId(wagesEntitys.getUserId());
            wageVo.setUserName(wagesEntitys.getUserName());
            wageVo.setWagesId(wagesEntitys.getWagesId());
            wageVo.setTotal(wagesEntitys.getBasicSalary()+wagesEntitys.getMealSupplement()+wagesEntitys.getPerformance());
            return wageVo;
        }
        return null;
    }

    @Override
    public IPage<WagesEntity> selectByName(WageNameDao pagesDao) {
        if (pagesDao.getPageSize() == null){
            pagesDao.setPageSize(10);
        }
        Page<WagesEntity> wagesEntityPage = new Page<>(pagesDao.getPageNumber(), pagesDao.getPageSize());
        IPage<WagesEntity> wagesEntityIPage = wagesMapper.selectPage(wagesEntityPage, new LambdaUpdateWrapper<WagesEntity>().like(WagesEntity::getUserName,pagesDao.getName()));
        return wagesEntityIPage;
    }
}
