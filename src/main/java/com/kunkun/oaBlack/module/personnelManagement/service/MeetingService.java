package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.MeetingDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.MeetingEntity;

public interface MeetingService extends IService<MeetingEntity> {
    MeetingEntity addMeeting(MeetingDao meetingDao);

    ResultUtil deleteMeeting(Integer roomId);
}
