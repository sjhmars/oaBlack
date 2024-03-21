package com.kunkun.oaBlack.module.personnelManagement.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WageNameDao {
    private Integer pageNumber;
    private Integer pageSize;
    private String name;
}
