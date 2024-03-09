package com.kunkun.oaBlack.module.auth.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.auth.enity.UserEnity;
import com.kunkun.oaBlack.module.auth.mapper.UserMapper;
import com.kunkun.oaBlack.module.auth.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImp extends ServiceImpl<UserMapper, UserEnity> implements UserService {

//    @Cacheable(value = "user", key = "#userId")
    @Override
    public UserEnity selectById(Integer userId){
        return getOne(new LambdaQueryWrapper<UserEnity>().eq(UserEnity::getUserId,userId));
    }

//    @CachePut(value = "user", key = "#userEnity.userId",unless="#result==null")
    @Override
    public UserEnity updateLoginTimeAndAddress(UserEnity userEnity) {
        Date myNowTime = new Date();
        userEnity.setUpdateId(userEnity.getUserId());
        userEnity.setUpdateTime(myNowTime);
        userEnity.setLastLoginTime(myNowTime);
        if (update(userEnity,new LambdaUpdateWrapper<UserEnity>().eq(UserEnity::getUserId,userEnity.getUserId()))){
            return userEnity;
        }
        return null;
    }
}
