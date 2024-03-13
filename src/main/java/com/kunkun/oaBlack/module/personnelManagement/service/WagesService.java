package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.module.personnelManagement.dao.WagesDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.WagesEntity;
import org.springframework.security.core.Authentication;

public interface WagesService extends IService<WagesEntity> {
    WagesEntity addWages(WagesDao wagesDao, Authentication authentication);
}
