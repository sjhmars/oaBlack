package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;

public interface PersonUserService extends IService<UserEnity> {
    UserEnity selectById(Integer userId);
}
