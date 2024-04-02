package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.module.personnelManagement.dao.MakeUpCheckDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.SupplementCheckEntity;
import org.springframework.security.core.Authentication;

public interface SupplementCheckService extends IService<SupplementCheckEntity> {

    SupplementCheckEntity addSupplementCheck(Authentication authentication, MakeUpCheckDao makeUpCheckDao);
}
