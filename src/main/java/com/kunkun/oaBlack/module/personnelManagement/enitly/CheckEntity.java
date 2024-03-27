package com.kunkun.oaBlack.module.personnelManagement.enitly;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kunkun.oaBlack.common.util.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_check")
public class CheckEntity {

    @TableId(type = IdType.AUTO,value = "check_id")
    private Integer checkId;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "this_date")
    @JSONField(serializeUsing = LocalDateTimeSerializer.class)
    private LocalDateTime thisDate;

    @TableField(value = "check_start_time")
    @JSONField(serializeUsing = LocalDateTimeSerializer.class)
    private LocalDateTime checkStartTime;

    @TableField(value = "check_end_time")
    @JSONField(serializeUsing = LocalDateTimeSerializer.class)
    private LocalDateTime checkEndTime;

    @TableField(value = "late_time")
    private String lateTime;

    @TableField("early_time")
    private String earlyTime;

    @TableField("leave_status")
    private Integer leaveStatus;

    @TableField("address")
    private String address;
}
