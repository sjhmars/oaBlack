package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.module.personnelManagement.enitly.CheckEntity;
import org.springframework.security.core.Authentication;

public interface CheckService extends IService<CheckEntity> {
    CheckEntity checkIn(Authentication authentication, String address);
    void balanceOneDay();
}
