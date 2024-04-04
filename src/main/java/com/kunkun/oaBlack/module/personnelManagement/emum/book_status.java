package com.kunkun.oaBlack.module.personnelManagement.emum;

public enum book_status {

    SUCCESS(1,"预约成功"),
    FILE(0,"预约未通过"),
    AUDITINGFILE(2,"取消预约");

    private final Integer statusCode;

    private final String statusName;

    book_status(Integer statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusName() {
        return statusName;
    }
}
