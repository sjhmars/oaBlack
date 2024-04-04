package com.kunkun.oaBlack.module.personnelManagement.vo;

import com.kunkun.oaBlack.module.personnelManagement.enitly.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingListVo {

    private Integer roomId;

    private Integer roomSize;

    private String roomName;

    private int roomFloor;

    private String roomIdentifier;

    private List<BookEntity> bookEntities;
}
