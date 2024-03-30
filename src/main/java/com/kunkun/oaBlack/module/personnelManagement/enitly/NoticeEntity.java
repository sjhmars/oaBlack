package com.kunkun.oaBlack.module.personnelManagement.enitly;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_notice")
public class NoticeEntity {
    @TableId(value = "notice_id", type = IdType.AUTO)
    private Integer noticeId;

    @TableField(value = "notice_content")
    private String noticeContent;

    @TableField(value = "notice_title")
    private String noticeTitle;

    @TableField(value = "notice_type")
    private Integer noticeType;

    @TableField(value = "send_user_id")
    private Integer sendUserId;

    @TableField(value = "recipient_user_id")
    private Integer recipientUserId;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "is_delete")
    private Integer isDelete;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "operation_status")
    private Integer operationStatus;

    @TableField(value = "entity_id")
    private Integer entityId;

    @TableField("end_time")
    private Date endTime;
}
