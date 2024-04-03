package com.kunkun.oaBlack.module.personnelManagement.controller;

import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.NoticeDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.NoticePageDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import com.kunkun.oaBlack.module.personnelManagement.service.NoticeService;
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
@RequestMapping("/notice")
@Api("代办模块")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @ApiOperation("通过代办")
    @PostMapping("/agree")
    public ResultUtil agree(@RequestBody NoticeDao noticeDao){
        NoticeEntity noticeEntity = noticeService.agree(noticeDao.getNoticeId(),noticeDao.getNoticeContent());
        if (noticeEntity!=null){
            return ResultUtil.success("通过成功",noticeEntity);
        }
        return ResultUtil.faile("原因看log");
    }

    @ApiOperation("审批代办")
    @PostMapping("/selectNoticeAuditingList")
    public ResultUtil selectNoticeAuditingList(@RequestBody NoticePageDao noticePageDao){
        return ResultUtil.success(noticeService.selectNoticeAuditingPage(noticePageDao));
    }

    @ApiOperation("申请代办")
    @PostMapping("/selectNoticeApplicationList")
    public ResultUtil selectNoticeApplicationList(@RequestBody NoticePageDao noticePageDao){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return ResultUtil.success(noticeService.selectNoticeApplicationPage(authentication,noticePageDao));
    }
}
