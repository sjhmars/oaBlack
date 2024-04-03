package com.kunkun.oaBlack.module.personnelManagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kunkun.oaBlack.module.personnelManagement.enitly.BookEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BookMapper extends BaseMapper<BookEntity> {
}
