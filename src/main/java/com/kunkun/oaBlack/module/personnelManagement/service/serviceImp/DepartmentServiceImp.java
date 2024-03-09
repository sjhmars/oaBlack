package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.dao.AddDepartmentDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.DepartmentEnitly;
import com.kunkun.oaBlack.module.personnelManagement.mapper.DepartmentMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.DepartmentService;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class DepartmentServiceImp extends ServiceImpl<DepartmentMapper, DepartmentEnitly> implements DepartmentService{

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private PersonUserService personUserService;

    @Autowired
    private TokenStore jwtTokenStore;

    @Override
    @Transactional
    public ResultUtil addDepartment(AddDepartmentDao departmentDao,Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");

        DepartmentEnitly departmentEnitly = new DepartmentEnitly();
        departmentEnitly.setCreateTime(new Date());
        departmentEnitly.setDepartmentName(departmentDao.getDepartmentName());
        departmentEnitly.setSuperiorDepartment(departmentDao.getSuperiorDepartment());
        departmentEnitly.setUserId(departmentDao.getUserId());
        departmentEnitly.setIsDelete(0);
        departmentMapper.insert(departmentEnitly);

        if (updateUserResponsibleDepartmentIds(departmentDao.getUserId(),departmentEnitly.getDepartmentId(),userId)!=null){
            return ResultUtil.success("新增部门成功",departmentEnitly);
        }
        return ResultUtil.faile("更新失败");
    }

    @CachePut(value = "user", key = "#userId",unless="#result==null")
    public UserEnity updateUserResponsibleDepartmentIds(Integer userId,Integer departmentId,Integer change_userId){
        UserEnity userEnityold = personUserService.selectById(userId);
        String departmentIds = userEnityold.getDepartmentIds();
        departmentIds = departmentIds+","+departmentId;
        userEnityold.setDepartmentIds(departmentIds);
        userEnityold.setUpdateId(change_userId);
        userEnityold.setUpdateTime(new Date());
        if (personUserService.update(userEnityold,new LambdaUpdateWrapper<UserEnity>().eq(UserEnity::getUserId,userEnityold.getUserId()))){
            return userEnityold;
        }
        return null;
    }
}
