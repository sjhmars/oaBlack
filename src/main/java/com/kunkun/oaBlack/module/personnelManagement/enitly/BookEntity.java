package com.kunkun.oaBlack.module.personnelManagement.enitly;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_Meeting")
public class BookEntity {

    @TableId("book_id")
    private Integer bookId;

    @TableField("book_start_time")
    private Date bookStartTime;

    @TableField("book_end_time")
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
}
