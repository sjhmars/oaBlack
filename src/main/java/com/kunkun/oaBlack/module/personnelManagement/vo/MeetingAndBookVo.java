package com.kunkun.oaBlack.module.personnelManagement.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingAndBookVo {


    private Integer roomId;

    private Integer roomSize;

    private String roomName;

    private int roomFloor;

    private String roomIdentifier;

    private Integer bookId;

    private Date bookStartTime;

    private Date bookEndTime;

    private Integer createUserId;

    private String createUserName;

    private Integer status;

    private String reviewerUserName;

    private String meetingTitle;

    private String meetingDetails;

    private String membersIds;
}
