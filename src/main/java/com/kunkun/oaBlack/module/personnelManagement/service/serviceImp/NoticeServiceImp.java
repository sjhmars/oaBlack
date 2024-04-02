package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.emum.NoticeType;
import com.kunkun.oaBlack.module.personnelManagement.emum.statusEmum;
import com.kunkun.oaBlack.module.personnelManagement.enitly.LeaveEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.SupplementCheckEntity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.NoticeMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.CheckService;
import com.kunkun.oaBlack.module.personnelManagement.service.LeaveService;
import com.kunkun.oaBlack.module.personnelManagement.service.NoticeService;
import com.kunkun.oaBlack.module.personnelManagement.service.SupplementCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class NoticeServiceImp extends ServiceImpl<NoticeMapper, NoticeEntity> implements NoticeService {

    protected static final Logger logger = LoggerFactory.getLogger(NoticeServiceImp.class);

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private SupplementCheckService supplementCheckService;

    @Autowired
    private CheckService checkService;

    @Override
    @Transactional
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
            if (noticeEntity.getNoticeType().equals(NoticeType.holidayReview.getTypeCode())){
                LeaveEntity leaveEntity = leaveService.getById(noticeEntity.getEntityId());
                leaveEntity.setStatus(statusEmum.SUCCESS.getStatusCode());
                if (leaveService.update(leaveEntity,new LambdaUpdateWrapper<LeaveEntity>().eq(LeaveEntity::getLeaveId,leaveEntity.getLeaveId()))){
                    logger.info("请假成功");
                }
                else {
                    return null;
                }
            }
            if (noticeEntity.getNoticeType().equals(NoticeType.cardReplacement.getTypeCode())){
                SupplementCheckEntity supplementCheckEntity = supplementCheckService.getById(noticeEntity.getEntityId());
                supplementCheckEntity.setStatus(statusEmum.SUCCESS.getStatusCode());
                if (supplementCheckService.update(supplementCheckEntity,new LambdaUpdateWrapper<SupplementCheckEntity>().eq(SupplementCheckEntity::getSupplementId,supplementCheckEntity.getSupplementId()))){
                    checkService.makeUpCheckIn(supplementCheckEntity);
                    logger.info("补卡成功");
                }
                else {
                    return null;
                }
            }
            noticeEntity.setOperationStatus(statusEmum.SUCCESS.getStatusCode());
            noticeEntity.setNoticeContent(noticeContent);
            row = noticeMapper.update(noticeEntity,new LambdaUpdateWrapper<NoticeEntity>()
                    .eq(NoticeEntity::getNoticeId,noticeId)
                    .eq(NoticeEntity::getIsDelete,0)
            );
            if (row>0){
                logger.info("审批通过成功");
                return noticeEntity;
            }
        }
        return null;
    }
}
