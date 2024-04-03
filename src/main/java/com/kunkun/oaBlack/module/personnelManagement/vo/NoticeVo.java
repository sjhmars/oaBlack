package com.kunkun.oaBlack.module.personnelManagement.vo;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeVo {

    private Integer noticeId;

    private String noticeContent;

    private String noticeTitle;

    private Integer noticeType;

    private Date createTime;

    private Integer operationStatus;

    private Integer entityId;

    private Date endTime;

    private Object entity;
}
