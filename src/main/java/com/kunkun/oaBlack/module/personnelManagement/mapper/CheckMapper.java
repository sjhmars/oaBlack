package com.kunkun.oaBlack.module.personnelManagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kunkun.oaBlack.module.personnelManagement.enitly.CheckEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CheckMapper extends BaseMapper<CheckEntity> {
}
