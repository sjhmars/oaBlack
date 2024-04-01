package com.kunkun.oaBlack.module.personnelManagement.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakeUpCheckDao {
    private long checkStartTime;
    private long checkEndTime;
    private Integer checkId;
}
