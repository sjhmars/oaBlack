package com.kunkun.oaBlack.module.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kunkun.oaBlack.module.auth.enity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMapper extends BaseMapper<RoleEntity> {

}
