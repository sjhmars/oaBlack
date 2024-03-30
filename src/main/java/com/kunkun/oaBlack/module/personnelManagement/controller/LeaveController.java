package com.kunkun.oaBlack.module.personnelManagement.controller;

import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.LeaveDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import com.kunkun.oaBlack.module.personnelManagement.service.LeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leave")
@Api("请假模块")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @ApiOperation("发起请假")
    @PostMapping("/addLeaveNotice")
    public ResultUtil addLeaveNotice(@RequestBody LeaveDao leaveDao){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        NoticeEntity noticeEntity = leaveService.addLeaveNotice(authentication,leaveDao);
        if (noticeEntity!=null)
            return ResultUtil.success("成功发起请假",noticeEntity);
        return ResultUtil.faile("发起失败");
    }
}
