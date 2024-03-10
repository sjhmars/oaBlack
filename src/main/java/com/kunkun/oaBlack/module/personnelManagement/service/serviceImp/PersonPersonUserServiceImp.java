package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.AddUserDao;
import com.kunkun.oaBlack.module.personnelManagement.mapper.PersonUserMapper;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonPersonUserServiceImp extends ServiceImpl<PersonUserMapper, UserEnity> implements PersonUserService {

    @Autowired
    private PersonUserMapper personUserMapper;

    @Autowired
    private TokenStore jwtTokenStore;

    @Cacheable(value = "user", key = "#userId")
    @Override
    public UserEnity selectById(Integer userId) {
        return personUserMapper.selectById(userId);
    }

    @Override
    public ResultUtil addUser(AddUserDao addUserDao,Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");
        UserEnity userEnity = new UserEnity();
        userEnity.setCreateTime(new Date());
        UserEnity CreatUser =  selectById(userId);
        userEnity.setCreateBy(CreatUser.getUserName());
        userEnity.setUserName(addUserDao.getUserName());
        userEnity.setSex(addUserDao.getSex());
        userEnity.setBirth(addUserDao.getBirth());
        userEnity.setMobile(addUserDao.getMobile());
        userEnity.setWorkStatus(addUserDao.getWorkStatus());
        userEnity.setDepartmentId(addUserDao.getDepartmentId());
        userEnity.setPostId(addUserDao.getPostId());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passwordEncoder =  bCryptPasswordEncoder.encode(addUserDao.getUserPassword());
        userEnity.setUserPassword(passwordEncoder);
        try {
            if (personUserMapper.insert(userEnity)>0){
                return ResultUtil.success(userEnity);
            }
        }catch (Exception e){
            String errMsg = e.getMessage();
            if (StrUtil.isNotBlank(errMsg)&&errMsg.contains("user.one")){
                return ResultUtil.faile("用户id唯一");
            }
            if (StrUtil.isNotBlank(errMsg)&&errMsg.contains("user.phoneOne"))
                return ResultUtil.faile("用户手机不唯一");
        }
        return ResultUtil.faile("插入失败原因不详");
    }
}
