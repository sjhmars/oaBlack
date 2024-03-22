package com.kunkun.oaBlack.module.personnelManagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.vo.DUserVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PersonUserMapper extends BaseMapper<UserEnity> {
    UserVo selectUserVoById(@Param("userId")Integer userId);
    List<DUserVo> selectAllByDepartmentId(@Param("departmentId") Integer departmentId);
    List<UserVo> selectAllUser();
}