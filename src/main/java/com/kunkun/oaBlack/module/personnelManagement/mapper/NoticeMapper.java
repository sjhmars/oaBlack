package com.kunkun.oaBlack.module.personnelManagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface NoticeMapper extends BaseMapper<NoticeEntity> {
    Integer selectNoReadNum(@Param("userId")Integer userId);
    Integer selectNoReadNumD(@Param("userId")Integer userId);
}
