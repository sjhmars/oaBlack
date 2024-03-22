package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.enitly.CheckEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.CheckMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.CheckService;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CheckServiceImp extends ServiceImpl<CheckMapper, CheckEntity> implements CheckService {

    @Autowired
    private PersonUserService personUserService;

    @Autowired
    private CheckMapper checkMapper;

    @Autowired
    private TokenStore jwtTokenStore;

    @Scheduled(cron = "0 0 1 * * 1-5")
    public void doSomething() {
        //首先查询出所有的员工
        List<UserEnity> userEnityList = personUserService.list();

        Calendar calendarNew = Calendar.getInstance();
        calendarNew.setTime(new Date());
        calendarNew.set(Calendar.HOUR_OF_DAY,0);
        calendarNew.set(Calendar.MINUTE,0);
        calendarNew.set(Calendar.SECOND,0);
        Date thisDateZro = calendarNew.getTime();


        userEnityList.forEach( userEnity ->{
            Integer id = userEnity.getUserId();
            //创建一个考勤实体
            CheckEntity checkEntity = new CheckEntity();
            //设置参数
            checkEntity.setUserId(id);
            checkEntity.setThisDate(thisDateZro);
            checkEntity.setUserName(userEnity.getUserName());
            //查询当前用户是否在请假中
//            Leaves leaves = leavesService.queryIsLeaves(id, LocalDateTime.now());
//            if (ObjectUtil.isNotEmpty(leaves)){
//                attendanceRecords.setLeaveStatus(leaves.getLeaveType());
//            }else {
//                attendanceRecords.setLeaveStatus("正常");
//            }
//            //进行保存
//            add(attendanceRecords);
            checkMapper.insert(checkEntity);
        });
    }


    @Override
    public CheckEntity checkIn(Authentication authentication, String address) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");

        //设置今天0点（定时器启动失败时保底）
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Date thisDate = calendar.getTime();

        CheckEntity check =  checkMapper.selectOne(new LambdaQueryWrapper<CheckEntity>().eq(CheckEntity::getUserId,userId).eq(CheckEntity::getThisDate,thisDate));
        CheckEntity newCheck = new CheckEntity();
        if (check == null){
            newCheck.setThisDate(thisDate);
            newCheck.setAddress(address);
            newCheck.setCheckStartTime(new Date());
        }
        return null;
    }
}
