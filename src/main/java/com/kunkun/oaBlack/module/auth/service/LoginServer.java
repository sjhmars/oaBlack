package com.kunkun.oaBlack.module.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.auth.dao.UserDao;
import com.kunkun.oaBlack.module.auth.enity.UserEnity;
import org.springframework.security.core.Authentication;

public interface LoginServer extends IService<UserEnity> {
    ResultUtil login(UserDao userDao);
    ResultUtil loginOut(Authentication authentication);
}
