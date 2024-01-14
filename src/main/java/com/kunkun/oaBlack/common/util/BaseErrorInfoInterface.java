package com.kunkun.oaBlack.common.util;

public interface BaseErrorInfoInterface {

    /**
     * 错误码
     * @return
     */
    Integer getResultCode();

    /**
     * 错误描述
     * @return
     */
    String getResultMessage();
}
