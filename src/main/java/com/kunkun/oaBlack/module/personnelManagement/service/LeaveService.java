package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.module.personnelManagement.dao.LeaveDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.LeaveEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import org.springframework.security.core.Authentication;

public interface LeaveService extends IService<LeaveEntity> {
    NoticeEntity addLeaveNotice(Authentication authentication, LeaveDao leaveDao);
}
