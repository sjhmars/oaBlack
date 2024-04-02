package com.kunkun.oaBlack.module.personnelManagement.enitly;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("SupplementCheckEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplementCheckEntity {

    @TableId(value = "supplement_id",type = IdType.AUTO)
    private Integer supplementId;

    @TableField("check_time")
    private LocalDateTime checkTime;

    @TableField("reason_content")
    private String reasonContent;

    @TableField("check_id")
    private Integer checkId;

    @TableField("create_user_id")
    private Integer createUserId;

    @TableField("create_user_name")
    private String createUserName;

    @TableField("reviewer_user_id")
    private Integer reviewerUserId;

    @TableField("reviewer_user_name")
    private String reviewerUserName;

    @TableField("status")
    private Integer status;
}
