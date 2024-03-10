package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.AddUserDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import org.springframework.security.core.Authentication;

public interface PersonUserService extends IService<UserEnity> {
    UserEnity selectByIdMy(Integer userId);
    ResultUtil addUser(AddUserDao addUserDao, Authentication authentication);
}
