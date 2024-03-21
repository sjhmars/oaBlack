package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.AddDepartmentDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.DepartmentDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.PagesDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.DepartmentEnitly;
import com.kunkun.oaBlack.module.personnelManagement.vo.DepartmentTreeUserVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.DepartmentTreeVo;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DepartmentService extends IService<DepartmentEnitly> {
    ResultUtil addDepartment(AddDepartmentDao departmentDao,Authentication authentication);
    List<DepartmentTreeVo> getDepartmentTree(Authentication authentication);
    String getDepartmentName(Integer DepartmentId);
    String getOneName(Integer DepartmentId);
    List<DepartmentTreeUserVo> getDepartmentTreeUserVoTree();
    DepartmentEnitly updataHeadUserId(Integer UserId,Authentication authentication);
}
