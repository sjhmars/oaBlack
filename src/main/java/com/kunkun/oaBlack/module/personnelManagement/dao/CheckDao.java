package com.kunkun.oaBlack.module.personnelManagement.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckDao {

    private Integer PageNumber;

    private Integer PageSize;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
