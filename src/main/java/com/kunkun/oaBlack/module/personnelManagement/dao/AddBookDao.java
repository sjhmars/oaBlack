package com.kunkun.oaBlack.module.personnelManagement.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBookDao {

    private long bookStartTime;

    private long bookEndTime;

    private Integer reviewerUserId;

    private String meetingTitle;

    private String meetingDetails;

    private Integer roomId;

    private String membersIds;
}
