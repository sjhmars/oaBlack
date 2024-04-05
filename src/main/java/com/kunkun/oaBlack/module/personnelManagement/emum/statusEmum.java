package com.kunkun.oaBlack.module.personnelManagement.emum;

public enum statusEmum {

    SUCCESS(1,"通过"),
    FILE(0,"未通过"),
    AUDITINGFILE(2,"不通过");

    private final Integer statusCode;

    private final String statusName;


    statusEmum(Integer statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
