package com.kunkun.oaBlack.module.personnelManagement.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingDao {

    private Integer roomSize;

    private String roomName;

    private Integer roomFloor;

    private String roomIdentifier;
}
