package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.AddDepartmentDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.DepartmentDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.DepartmentEnitly;
import org.springframework.security.core.Authentication;

public interface DepartmentService extends IService<DepartmentEnitly> {
    ResultUtil addDepartment(AddDepartmentDao departmentDao,Authentication authentication);
}
