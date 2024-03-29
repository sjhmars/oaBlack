package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.dao.LeaveDao;
import com.kunkun.oaBlack.module.personnelManagement.emum.leave_status;
import com.kunkun.oaBlack.module.personnelManagement.enitly.LeaveEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.LeaveMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.LeaveService;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LeaveServiceImp extends ServiceImpl<LeaveMapper, LeaveEntity> implements LeaveService {

    @Autowired
    private TokenStore jwtTokenStore;

    @Autowired
    private PersonUserService personUserService;

    @Override
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


        NoticeEntity noticeEntity = new NoticeEntity();


        return null;
    }
}
