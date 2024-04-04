package com.kunkun.oaBlack.module.personnelManagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kunkun.oaBlack.module.personnelManagement.enitly.MeetingEntity;
import com.kunkun.oaBlack.module.personnelManagement.vo.MeetingAndBookVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface MeetingMapper extends BaseMapper<MeetingEntity> {
   List<MeetingAndBookVo> searchAllMeetingAndBook(@Param("roomId")Integer roomId, @Param("startTime")Date startTime, @Param("endTime")Date endTime);
}
