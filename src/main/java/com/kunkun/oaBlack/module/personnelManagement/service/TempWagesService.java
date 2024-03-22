package com.kunkun.oaBlack.module.personnelManagement.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.module.personnelManagement.dao.TempWagesDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.TempWagesEntity;
import org.springframework.security.core.Authentication;

public interface TempWagesService extends IService<TempWagesEntity> {
    TempWagesEntity setTempWages(Authentication authentication, TempWagesDao tempWagesDao);
}
