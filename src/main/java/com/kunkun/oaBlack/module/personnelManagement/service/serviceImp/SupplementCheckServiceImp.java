package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.dao.MakeUpCheckDao;
import com.kunkun.oaBlack.module.personnelManagement.emum.NoticeType;
import com.kunkun.oaBlack.module.personnelManagement.emum.statusEmum;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.SupplementCheckEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.NoticeMapper;
import com.kunkun.oaBlack.module.personnelManagement.mapper.SupplementCheckMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import com.kunkun.oaBlack.module.personnelManagement.service.SupplementCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

@Service
public class SupplementCheckServiceImp extends ServiceImpl<SupplementCheckMapper, SupplementCheckEntity> implements SupplementCheckService {
    protected static final Logger logger = LoggerFactory.getLogger(CheckServiceImp.class);

    @Autowired
    private PersonUserService personUserService;

    @Autowired
    private SupplementCheckMapper supplementCheckMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private TokenStore jwtTokenStore;

    @Override
    @Transactional
    public SupplementCheckEntity addSupplementCheck(Authentication authentication, MakeUpCheckDao makeUpCheckDao) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");
        UserEnity cUser = personUserService.selectByIdMy(userId);
        UserEnity rUser = personUserService.selectByIdMy(makeUpCheckDao.getReviewerUserId());
        SupplementCheckEntity supplementCheckEntity = new SupplementCheckEntity();
        if (ObjectUtil.isNotNull(makeUpCheckDao.getCheckStartTime()) || makeUpCheckDao.getCheckStartTime() != 0){
            LocalDateTime startTime = Instant.ofEpochMilli(makeUpCheckDao.getCheckStartTime()).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
            supplementCheckEntity.setCheckStartTime(startTime);
        }else if (ObjectUtil.isNotNull(makeUpCheckDao.getCheckEndTime())){
            LocalDateTime endTime = Instant.ofEpochMilli(makeUpCheckDao.getCheckEndTime()).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
            supplementCheckEntity.setCheckEndTime(endTime);
        }
        supplementCheckEntity.setCheckId(makeUpCheckDao.getCheckId());
        supplementCheckEntity.setStatus(statusEmum.FILE.getStatusCode());
        supplementCheckEntity.setCreateUserId(userId);
        supplementCheckEntity.setCreateUserName(cUser.getUserName());
        supplementCheckEntity.setReviewerUserId(makeUpCheckDao.getReviewerUserId());
        supplementCheckEntity.setReviewerUserName(rUser.getUserName());
        supplementCheckEntity.setReasonContent(makeUpCheckDao.getReasonContent());
        int row = supplementCheckMapper.insert(supplementCheckEntity);

        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setNoticeTitle(NoticeType.cardReplacement.getTypeName());
        noticeEntity.setCreateTime(new Date());
        noticeEntity.setNoticeType(NoticeType.cardReplacement.getTypeCode());
        noticeEntity.setSendUserId(userId);
        noticeEntity.setRecipientUserId(supplementCheckEntity.getReviewerUserId());
        noticeEntity.setStatus(statusEmum.FILE.getStatusCode());
        noticeEntity.setIsDelete(0);
        noticeEntity.setOperationStatus(statusEmum.FILE.getStatusCode());
        noticeEntity.setEntityId(supplementCheckEntity.getSupplementId());
        Calendar calendar = Calendar.getInstance();  //获取当前时间
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  //设置为当前月份的最后一天
        Date lastDayOfMonth = calendar.getTime();
        noticeEntity.setEndTime(lastDayOfMonth);

        int noticeRow = noticeMapper.insert(noticeEntity);
        if(noticeRow>0){
            logger.info("添加补卡代办成功");
        }

        if (row>0){
            logger.info("创建补卡记录");
        }
        if (noticeRow>0&&row>0){
            return supplementCheckEntity;
        }
        return null;
    }
}
