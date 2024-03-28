package com.kunkun.oaBlack.module.personnelManagement.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckDao {

    private Integer pageNumber;

    private Integer pageSize;

    private Long startTime;

    private Long endTime;

}
