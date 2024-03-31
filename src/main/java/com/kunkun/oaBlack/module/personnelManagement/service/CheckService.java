package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.module.personnelManagement.dao.CheckDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.CheckEntity;
import org.springframework.security.core.Authentication;

public interface CheckService extends IService<CheckEntity> {
    CheckEntity checkIn(Authentication authentication, String address);
    void balanceOneDay();
    void doSomething();
    IPage<CheckEntity> seleAllCheck(CheckDao checkDao);
    IPage<CheckEntity> seleCheckById(CheckDao checkDao);
    IPage<CheckEntity> selectMyCheck(CheckDao checkDao,Authentication authentication);
}
