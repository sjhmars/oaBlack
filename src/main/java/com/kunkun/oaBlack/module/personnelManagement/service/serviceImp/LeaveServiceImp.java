package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.dao.LeaveDao;
import com.kunkun.oaBlack.module.personnelManagement.emum.NoticeType;
import com.kunkun.oaBlack.module.personnelManagement.emum.leave_status;
import com.kunkun.oaBlack.module.personnelManagement.emum.statusEmum;
import com.kunkun.oaBlack.module.personnelManagement.enitly.LeaveEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.LeaveMapper;
import com.kunkun.oaBlack.module.personnelManagement.mapper.NoticeMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.LeaveService;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class LeaveServiceImp extends ServiceImpl<LeaveMapper, LeaveEntity> implements LeaveService {

    protected static final Logger logger = LoggerFactory.getLogger(LeaveServiceImp.class);

    @Autowired
    private TokenStore jwtTokenStore;

    @Autowired
    private PersonUserService personUserService;

    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    @Transactional
    public NoticeEntity addLeaveNotice(Authentication authentication, LeaveDao leaveDao) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");
        UserEnity userEnity = personUserService.selectByIdMy(userId);
        UserEnity ruserEnity = personUserService.selectByIdMy(leaveDao.getReviewerUserId());


        LeaveEntity leaveEntity = new LeaveEntity();
        leaveEntity.setBeginTime(new Date(leaveDao.getBeginTime()));
        leaveEntity.setEndTime(new Date(leaveDao.getEndTime()));
        leaveEntity.setCreateUserId(userId);
        leaveEntity.setCreateUserName(userEnity.getUserName());
        leaveEntity.setLeaveType(leaveDao.getLeaveType());
        leaveEntity.setLeaveDetails(leaveDao.getLeaveDetails());
        leaveEntity.setReviewerUserId(leaveDao.getReviewerUserId());
        leaveEntity.setReviewerUserName(ruserEnity.getUserName());
        leaveEntity.setStatus(statusEmum.FILE.getStatusCode());
        leaveEntity.setCreateTime(new Date());

        //计算请假天数
        double difference = (double) (leaveDao.getEndTime() - leaveDao.getBeginTime()) / (24 * 60 * 60 * 1000);
        float roundedDifference = (float) (Math.round(difference * 10) / 10.0);
        leaveEntity.setDay(roundedDifference);

        int leaveRow = leaveMapper.insert(leaveEntity);
        if(leaveRow>0){
            logger.info("发起请假成功");
        }

        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setNoticeTitle(NoticeType.holidayReview.getTypeName());
        noticeEntity.setCreateTime(new Date());
        noticeEntity.setNoticeType(NoticeType.holidayReview.getTypeCode());
        noticeEntity.setSendUserId(userId);
        noticeEntity.setRecipientUserId(leaveDao.getReviewerUserId());
        noticeEntity.setStatus(statusEmum.FILE.getStatusCode());
        noticeEntity.setIsDelete(0);
        noticeEntity.setOperationStatus(statusEmum.FILE.getStatusCode());
        noticeEntity.setEntityId(leaveEntity.getLeaveId());
        noticeEntity.setEndTime(new Date(leaveDao.getEndTime()));

        int noticeRow = noticeMapper.insert(noticeEntity);
        if(noticeRow>0){
            logger.info("添加假条成功");
        }
        if (leaveRow>0 && noticeRow>0){
            return noticeEntity;
        }
        return null;
    }
}
