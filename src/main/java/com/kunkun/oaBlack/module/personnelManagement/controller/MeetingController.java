package com.kunkun.oaBlack.module.personnelManagement.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.AddBookDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.MeetingDao;
import com.kunkun.oaBlack.module.personnelManagement.emum.book_status;
import com.kunkun.oaBlack.module.personnelManagement.enitly.BookEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.MeetingEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import com.kunkun.oaBlack.module.personnelManagement.service.BookService;
import com.kunkun.oaBlack.module.personnelManagement.service.MeetingService;
import com.kunkun.oaBlack.module.personnelManagement.service.NoticeService;
import com.kunkun.oaBlack.module.personnelManagement.vo.MeetingListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meeting")
@Api(tags = "会议模块")
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private BookService bookService;



    @ApiOperation("新增会议室")
    @PostMapping("/addMeetingRoom")
    public ResultUtil addMeeting(@RequestBody MeetingDao meetingDao){
        MeetingEntity meetingEntity = meetingService.addMeeting(meetingDao);
        if (meetingEntity!=null){
            return ResultUtil.success("成功啦",meetingEntity);
        }
        return ResultUtil.faile("新增失败");
    }

    @ApiOperation("新增会议室")
    @PostMapping("/deleteMeetingRoom")
    public ResultUtil deleteMeeting(@RequestBody Integer roomId){
        return meetingService.deleteMeeting(roomId);
    }


    @ApiOperation("预约会议")
    @PostMapping("/subscribeMeeting")
    public ResultUtil subscribeMeeting(@RequestBody AddBookDao addBookDao){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();

        NoticeEntity noticeEntity = bookService.addBook(addBookDao,authentication);
        if (noticeEntity != null){
            return ResultUtil.success("发起预约成功",noticeEntity);
        }
        return ResultUtil.faile("发起预约失败");
    }

    @ApiOperation("会议室列表")
    @GetMapping("/selectMeetingRoomList")
    public ResultUtil selectMeetingRoomList(){
        List<MeetingEntity> meetingEntities = meetingService.list();
        return ResultUtil.success("查询成功",meetingEntities);
    }

    @ApiOperation("会议列表")
    @GetMapping("/selectMeetingList")
    public ResultUtil selectMeetingList(){
        List<MeetingListVo> meetingListVos = meetingService.selectAllList();
        return ResultUtil.success("查询成功",meetingListVos);
    }

    @ApiOperation("我预约的会议列表")
    @GetMapping("/selectMyMeetingList")
    public ResultUtil selectMyMeetingList(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        List<MeetingListVo> meetingListVos = meetingService.selectAMyList(authentication);
        return ResultUtil.success("查询成功",meetingListVos);
    }

    @ApiOperation("取消预约")
    @PostMapping("/cancelReservation")
    public ResultUtil cancelReservation(Integer bookId){
        if (bookService.update(new LambdaUpdateWrapper<BookEntity>()
                .eq(BookEntity::getBookId,bookId)
                .set(BookEntity::getStatus, book_status.AUDITINGFILE.getStatusCode())
        )){
            return ResultUtil.success("取消成功");
        }
        return ResultUtil.faile("取消失败");
    }
}
