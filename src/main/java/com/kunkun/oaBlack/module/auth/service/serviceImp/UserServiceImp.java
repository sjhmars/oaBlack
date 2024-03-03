package com.kunkun.oaBlack.module.auth.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.auth.enity.UserEnity;
import com.kunkun.oaBlack.module.auth.mapper.UserMapper;
import com.kunkun.oaBlack.module.auth.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp extends ServiceImpl<UserMapper, UserEnity> implements UserService {

    @Cacheable(value = "user", key = "#userId")
    public UserEnity selectById(Integer userId){
        UserEnity user = getOne(new LambdaQueryWrapper<UserEnity>().eq(UserEnity::getUserId,userId));
        return user;
    }
}
