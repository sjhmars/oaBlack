package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.MeetingDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.MeetingEntity;
import com.kunkun.oaBlack.module.personnelManagement.vo.MeetingAndBookVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.MeetingListVo;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MeetingService extends IService<MeetingEntity> {
    MeetingEntity addMeeting(MeetingDao meetingDao);

    ResultUtil deleteMeeting(Integer roomId);

    List<MeetingListVo> selectAllList();

    MeetingListVo selectAllListById(Integer roomId);

    List<MeetingListVo> selectAMyList(Authentication authentication);
}
