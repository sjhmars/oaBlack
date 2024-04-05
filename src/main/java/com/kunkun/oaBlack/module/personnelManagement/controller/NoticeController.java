package com.kunkun.oaBlack.module.personnelManagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.NoticeDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.NoticePageDao;
import com.kunkun.oaBlack.module.personnelManagement.emum.statusEmum;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import com.kunkun.oaBlack.module.personnelManagement.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("拒绝代办")
    @PostMapping("/refuse")
    public ResultUtil refuse(@RequestBody NoticeDao noticeDao){
        if (noticeService.update(new LambdaUpdateWrapper<NoticeEntity>()
                .eq(NoticeEntity::getNoticeId,noticeDao.getNoticeId())
                .set(NoticeEntity::getOperationStatus, statusEmum.AUDITINGFILE.getStatusCode())
        )){
            return ResultUtil.success("拒绝成功");
        }
        return ResultUtil.faile("拒绝失败");
    }


    @ApiOperation("审批代办列表")
    @PostMapping("/selectNoticeAuditingList")
    public ResultUtil selectNoticeAuditingList(@RequestBody NoticePageDao noticePageDao){
        return ResultUtil.success(noticeService.selectNoticeAuditingPage(noticePageDao));
    }

    @ApiOperation("申请代办列表")
    @PostMapping("/selectNoticeApplicationList")
    public ResultUtil selectNoticeApplicationList(@RequestBody NoticePageDao noticePageDao){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return ResultUtil.success(noticeService.selectNoticeApplicationPage(authentication,noticePageDao));
    }

    @ApiOperation("未查看代办事件数量")
    @GetMapping("/selectNumOfSendUser")
    public ResultUtil selectNumOfSendUser(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return ResultUtil.success(noticeService.selectNoReadNotice(authentication));
    }

    @ApiOperation("未查看申请代办事件数量")
    @GetMapping("/selectNumOfSendUserS")
    public ResultUtil selectNumOfSendUserS(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return ResultUtil.success(noticeService.selectNoReadNoticeD(authentication));
    }

    @ApiOperation("消除申请代办数量红点")
    @GetMapping("/clickS")
    public ResultUtil clickS(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return noticeService.clickS(authentication);
    }

    @ApiOperation("消除代办数量红点")
    @GetMapping("/clickD")
    public ResultUtil clickD(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return noticeService.clickD(authentication);
    }
}
