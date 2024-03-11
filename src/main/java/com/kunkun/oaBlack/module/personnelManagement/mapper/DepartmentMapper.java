package com.kunkun.oaBlack.module.personnelManagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kunkun.oaBlack.module.personnelManagement.enitly.DepartmentEnitly;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentMapper extends BaseMapper<DepartmentEnitly> {
    String selectDepartmentName(@Param("role_id")Integer roleId);
    List<DepartmentEnitly> selectAll();
}
