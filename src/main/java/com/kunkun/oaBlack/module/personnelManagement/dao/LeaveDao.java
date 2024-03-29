package com.kunkun.oaBlack.module.personnelManagement.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveDao {
    private long beginTime;
    private long endTime;
    private Integer reviewerUserId;
    private Integer leaveType;
    private String leaveDetails;
}
