package com.kunkun.oaBlack.common.util;

public enum CodeUtil implements BaseErrorInfoInterface {
    SUCCESS(2000,"你请求成功了！好棒"),
    NO_PERMISSION(4000,"你没有权限"),
    NO_AUTH(6000,"不是哥们，你根本没登录"),
    INTERNAL_SERVER_ERROR(5000,"这个问题找mortal"),
    NOT_FOUND(4004, "嘿嘿！前端的事不关我的事，或许可能后端资源丢了？？？");
    //自定义枚举


    private final Integer code;
    private final String message;

    //枚举类构造方法
    CodeUtil(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getResultCode() {
        return null;
    }

    @Override
    public String getResultMessage() {
        return null;
    }
}
