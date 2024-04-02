package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import org.springframework.security.core.Authentication;

public interface NoticeService extends IService<NoticeEntity> {
    NoticeEntity agree(Integer noticeId,String noticeContent);
}
