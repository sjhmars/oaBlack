package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.module.personnelManagement.dao.NoticePageDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import com.kunkun.oaBlack.module.personnelManagement.vo.NoticeVo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface NoticeService extends IService<NoticeEntity> {
    NoticeEntity agree(Integer noticeId,String noticeContent);
    IPage<NoticeVo> selectNoticeAuditingPage(NoticePageDao noticePageDao);

    IPage<NoticeVo> selectNoticeApplicationPage(Authentication authentication,NoticePageDao noticePageDao);

    Integer selectNoReadNotice(Authentication authentication);
}
