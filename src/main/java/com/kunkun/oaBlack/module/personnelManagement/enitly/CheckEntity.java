package com.kunkun.oaBlack.module.personnelManagement.enitly;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kunkun.oaBlack.common.config.LocalDateTimeConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    @JsonSerialize(using = LocalDateTimeConfig.LocalDateTimeSerializer.class)
    private LocalDateTime thisDate;

    @TableField(value = "check_start_time")
    @JsonSerialize(using = LocalDateTimeConfig.LocalDateTimeSerializer.class)
    private LocalDateTime checkStartTime;

    @TableField(value = "check_end_time")
    @JsonSerialize(using = LocalDateTimeConfig.LocalDateTimeSerializer.class)
    private LocalDateTime checkEndTime;

    @TableField(value = "late_time")
    private String lateTime;

    @TableField(value ="early_time")
    private String earlyTime;

    @TableField(value ="leave_status")
    private Integer leaveStatus;

    @TableField(value ="address")
    private String address;

    @TableField(value ="other_status")
    private Integer otherStatus;
}
