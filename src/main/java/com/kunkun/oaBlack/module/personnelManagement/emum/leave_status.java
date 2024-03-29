package com.kunkun.oaBlack.module.personnelManagement.emum;

public enum leave_status {
    PersonalLeave(0,"事假"),
    AnnualLeave(1,"年假"),
    SickLeave(2,"病假");

    private final Integer leaveStatusNum;

    private final String leaveStatusName;

    leave_status(Integer leaveStatusNum, String leaveStatusName){
        this.leaveStatusNum = leaveStatusNum;
        this.leaveStatusName = leaveStatusName;
    }

    public Integer getLeaveStatusNum() {
        return leaveStatusNum;
    }

    public String getLeaveStatusName() {
        return leaveStatusName;
    }

    public static String getValue(Integer leaveStatusNum){
        for (leave_status anEnum : leave_status.values()) {
            if (leaveStatusNum.equals(anEnum.leaveStatusNum)){
                return leave_status.getValue(leaveStatusNum);
            }
        }
        return null;
    }
}
