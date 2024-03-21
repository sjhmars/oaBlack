package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.module.personnelManagement.dao.PagesDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.WageNameDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.WagesDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.WagesEntity;
import com.kunkun.oaBlack.module.personnelManagement.vo.AllWageVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.MyWageVo;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface WagesService extends IService<WagesEntity> {
    WagesEntity addWages(WagesDao wagesDao, Authentication authentication);
    IPage<MyWageVo> getMyWages(Authentication authentication, PagesDao pagesDao);
    IPage<WagesEntity> selectByName(WageNameDao wageNameDao);
    IPage<AllWageVo> selectAllWages(PagesDao pagesDao);
}
