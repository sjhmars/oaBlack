package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.enitly.CheckEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.CheckMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.CheckService;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class CheckServiceImp extends ServiceImpl<CheckMapper, CheckEntity> implements CheckService {

    @Autowired
    private PersonUserService personUserService;

    @Scheduled(cron = "0 0 2 * * 1-5")
    public void doSomething() {
        //首先查询出所有的员工
        List<UserEnity> userEnityList = personUserService.list();
        userEnityList.forEach( userEnity ->{
            Integer id = userEnity.getUserId();
            //创建一个考勤实体
            CheckEntity checkEntity = new CheckEntity();
            //设置参数
            checkEntity.setUserId(id);
            checkEntity.setThisDate(new Date());
            //查询当前用户是否在请假中
//            Leaves leaves = leavesService.queryIsLeaves(id, LocalDateTime.now());
//            if (ObjectUtil.isNotEmpty(leaves)){
//                attendanceRecords.setLeaveStatus(leaves.getLeaveType());
//            }else {
//                attendanceRecords.setLeaveStatus("正常");
//            }
//            //进行保存
//            add(attendanceRecords);
        });
    }
}
