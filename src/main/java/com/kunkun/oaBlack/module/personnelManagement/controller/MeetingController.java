package com.kunkun.oaBlack.module.personnelManagement.controller;

import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.MeetingDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.MeetingEntity;
import com.kunkun.oaBlack.module.personnelManagement.service.MeetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meeting")
@Api(tags = "会议模块")
public class MeetingController {
    private MeetingService meetingService;

    @ApiOperation("新增会议室")
    @PostMapping("/addMeeting")
    public ResultUtil addMeeting(@RequestBody MeetingDao meetingDao){
        MeetingEntity meetingEntity = meetingService.addMeeting(meetingDao);
        if (meetingEntity!=null){
            return ResultUtil.success("成功啦",meetingEntity);
        }
        return ResultUtil.faile("新增失败");
    }

    @ApiOperation("新增会议室")
    @PostMapping("/deleteMeeting")
    public ResultUtil deleteMeeting(@RequestBody Integer roomId){
        return meetingService.deleteMeeting(roomId);
    }
}
