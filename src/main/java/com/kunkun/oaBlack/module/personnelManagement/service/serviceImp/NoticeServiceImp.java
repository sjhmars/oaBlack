package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.emum.NoticeType;
import com.kunkun.oaBlack.module.personnelManagement.emum.statusEmum;
import com.kunkun.oaBlack.module.personnelManagement.enitly.LeaveEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.NoticeMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.LeaveService;
import com.kunkun.oaBlack.module.personnelManagement.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NoticeServiceImp extends ServiceImpl<NoticeMapper, NoticeEntity> implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private LeaveService leaveService;

    @Override
    public NoticeEntity agree(Integer noticeId,String noticeContent) {
        NoticeEntity noticeEntity = noticeMapper.selectById(noticeId);
        Date thisDate = new Date();
        int row;
        if (thisDate.getTime()>noticeEntity.getEndTime().getTime()){
            noticeEntity.setOperationStatus(statusEmum.FILE.getStatusCode());
            noticeEntity.setNoticeContent("已过期，无法同意");
            row = noticeMapper.update(noticeEntity,new LambdaUpdateWrapper<NoticeEntity>()
                    .eq(NoticeEntity::getNoticeId,noticeId)
                    .eq(NoticeEntity::getIsDelete,0)
            );
            if (row>0)
                return noticeEntity;
        }
        if (thisDate.getTime()<noticeEntity.getEndTime().getTime()){
            noticeEntity.setOperationStatus(statusEmum.SUCCESS.getStatusCode());
            noticeEntity.setNoticeContent(noticeContent);
            if (noticeEntity.getNoticeType().equals(NoticeType.holidayReview.getTypeCode())){
                LeaveEntity leaveEntity = leaveService.getById(noticeEntity.getEntityId());

            }
            row = noticeMapper.update(noticeEntity,new LambdaUpdateWrapper<NoticeEntity>()
                    .eq(NoticeEntity::getNoticeId,noticeId)
                    .eq(NoticeEntity::getIsDelete,0)
            );
            if (row>0)
                return noticeEntity;
        }
        return null;
    }
}
