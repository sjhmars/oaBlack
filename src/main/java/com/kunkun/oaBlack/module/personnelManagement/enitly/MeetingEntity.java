package com.kunkun.oaBlack.module.personnelManagement.enitly;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingEntity {

    @TableId(value = "room_id")
    private Integer roomId;

    @TableField("room_size")
    private Integer roomSize;

    @TableField("room_name")
    private String roomName;

    @TableField("room_floor")
    private int roomFloor;

    @TableField("room_identifier")
    private String roomIdentifier;

    @TableField("is_delete")
    private Integer isDelete;
}
