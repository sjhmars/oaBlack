package com.kunkun.oaBlack.module.personnelManagement.emum;

public enum NoticeType {
    inform(0,"通知"),
    holidayReview(1,"假期审核"),
    meetingApplication(2,"会议室审核");

    private final Integer TypeCode;

    private final String TypeName;


    NoticeType(Integer typeCode, String typeName) {
        TypeCode = typeCode;
        TypeName = typeName;
    }

    public Integer getTypeCode() {
        return TypeCode;
    }

    public String getTypeName() {
        return TypeName;
    }
}
