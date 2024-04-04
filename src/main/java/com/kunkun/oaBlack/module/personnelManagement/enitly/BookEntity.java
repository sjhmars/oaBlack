package com.kunkun.oaBlack.module.personnelManagement.enitly;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kunkun.oaBlack.common.config.CustomDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("meeting_book")
public class BookEntity {

    @TableId(value = "book_id",type = IdType.AUTO)
    private Integer bookId;

    @TableField("book_start_time")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date bookStartTime;

    @TableField("book_end_time")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date bookEndTime;

    @TableField("create_user_id")
    private Integer createUserId;

    @TableField("create_user_name")
    private String createUserName;

    @TableField("status")
    private Integer status;

    @TableField("reviewer_user_id")
    private Integer reviewerUserId;

    @TableField("reviewer_user_name")
    private String reviewerUserName;

    @TableField("meeting_title")
    private String meetingTitle;

    @TableField("meeting_details")
    private String meetingDetails;

    @TableField("members_ids")
    private String membersIds;

    @TableField("room_id")
    private Integer roomId;
}
