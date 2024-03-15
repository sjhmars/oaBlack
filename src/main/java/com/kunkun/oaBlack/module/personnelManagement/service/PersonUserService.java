package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.AddUserDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.UpdateUserDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.vo.DUserVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.UserAndDepartmentVo;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PersonUserService extends IService<UserEnity> {
    UserEnity selectByIdMy(Integer userId);
    ResultUtil addUser(AddUserDao addUserDao, Authentication authentication);
    UserAndDepartmentVo selectUserById(Integer userId);
    List<DUserVo> selectByDepartmentId(Integer departmentId);
    UserEnity updateUserById(UpdateUserDao updateUserDao, Authentication authentication);
    List<UserAndDepartmentVo> selectAllPeople();
}
