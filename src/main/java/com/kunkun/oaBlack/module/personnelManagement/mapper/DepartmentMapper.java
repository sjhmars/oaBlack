package com.kunkun.oaBlack.module.personnelManagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kunkun.oaBlack.module.personnelManagement.enitly.DepartmentEnitly;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentMapper extends BaseMapper<DepartmentEnitly> {
    List<DepartmentEnitly> selectAll();
    List<DepartmentEnitly> selectPageAll(IPage page);
}
