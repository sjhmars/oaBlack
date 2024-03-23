package com.kunkun.oaBlack.module.personnelManagement.emum;

public enum check_status {
    Holiday(0,"放假"),
    Absenteeism(1,"旷工"),
    Early_departure(2,"早退"),
    Late(3,"迟到");


    private final Integer statusNum;

    private final String statusName;

    check_status(Integer statusNum,String statusName){
        this.statusNum = statusNum;
        this.statusName = statusName;
    }

    public Integer getStatusNum() {
        return statusNum;
    }

    public String getStatusName() {
        return statusName;
    }
}
