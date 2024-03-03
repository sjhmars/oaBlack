package com.kunkun.oaBlack.module.auth.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.module.auth.enity.UserEnity;

import java.util.Date;

public interface UserService extends IService<UserEnity> {
    UserEnity selectById(Integer userId);
    UserEnity updateLoginTimeAndAddress(UserEnity userEnity);
}
