package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
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
import com.kunkun.oaBlack.module.personnelManagement.vo.AllWageVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.MyWageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public IPage<MyWageVo> getMyWages(Authentication authentication,PagesDao pagesDao) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");
        if (pagesDao.getPageSize() == null){
            pagesDao.setPageSize(10);
        }
        Page<WagesEntity> page = new Page<>(pagesDao.getPageNumber(),pagesDao.getPageSize());
        Page<WagesEntity> wagesEntityPage = wagesMapper.selectPage(page,new LambdaQueryWrapper<WagesEntity>().eq(WagesEntity::getUserId,userId));
        List<WagesEntity> wagesEntities = wagesEntityPage.getRecords();
        List<MyWageVo> wageVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(wagesEntities)){
            wageVos = wagesEntities.stream().map(wagesEntity -> {
                MyWageVo wageVo = new MyWageVo();
                wageVo.setBasicSalary(wagesEntity.getBasicSalary());
                wageVo.setCreateTime(wagesEntity.getCreateTime().getTime());
                wageVo.setLoseMoney(wagesEntity.getLoseMoney());
                wageVo.setMealSupplement(wagesEntity.getMealSupplement());
                wageVo.setPerformance(wagesEntity.getPerformance());
                wageVo.setUserId(wagesEntity.getUserId());
                wageVo.setUserName(wagesEntity.getUserName());
                wageVo.setWagesId(wagesEntity.getWagesId());
                wageVo.setTotal(wagesEntity.getBasicSalary()+wagesEntity.getMealSupplement()+wagesEntity.getPerformance());
                return wageVo;
            }).collect(Collectors.toList());
            IPage<MyWageVo> myWageVoIPage = new Page<>();
            myWageVoIPage.setTotal(wagesEntityPage.getTotal());
            myWageVoIPage.setSize(wagesEntityPage.getSize());
            myWageVoIPage.setPages(wagesEntityPage.getPages());
            myWageVoIPage.setRecords(wageVos);
            myWageVoIPage.setCurrent(wagesEntityPage.getCurrent());
            return myWageVoIPage;
        }
        return null;
    }

    @Override
    public IPage<WagesEntity> selectByName(WageNameDao pagesDao) {
        if (pagesDao.getPageSize() == null){
            pagesDao.setPageSize(10);
        }
        Page<WagesEntity> wagesEntityPage = new Page<>(pagesDao.getPageNumber(), pagesDao.getPageSize());
        IPage<WagesEntity> wagesEntityIPage = wagesMapper.selectPage(wagesEntityPage, new LambdaQueryWrapper<WagesEntity>().like(WagesEntity::getUserName,pagesDao.getName()));
        return wagesEntityIPage;
    }

    @Override
    public IPage<AllWageVo> selectAllWages(PagesDao pagesDao) {
        if (pagesDao.getPageSize() == null){
            pagesDao.setPageSize(10);
        }
        Page<AllWageVo> page = new Page<AllWageVo>(pagesDao.getPageNumber(),pagesDao.getPageSize());
        IPage<AllWageVo> allWageVoIPage = wagesMapper.selectAllPage(page);
        return allWageVoIPage;
    }
}
