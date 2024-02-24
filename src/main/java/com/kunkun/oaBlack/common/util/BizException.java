package com.kunkun.oaBlack.common.util;

public class BizException extends RuntimeException {

    protected  Integer code;
    protected String  msg;

    public BizException() {
        super();
    }

    public BizException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface.getResultCode().toString());
        this.code = errorInfoInterface.getResultCode();
        this.msg = errorInfoInterface.getResultMessage();
    }

    public BizException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface.getResultCode().toString(), cause);
        this.code = errorInfoInterface.getResultCode();
        this.msg = errorInfoInterface.getResultMessage();
    }

    public BizException(String errorMsg) {
        super(errorMsg);
        this.msg = errorMsg;
    }

    public BizException(Integer errorCode, String errorMsg) {
        super(errorCode.toString());
        this.code = errorCode;
        this.msg = errorMsg;
    }

    public BizException(Integer errorCode, String errorMsg, Throwable cause) {
        super(errorCode.toString(), cause);
        this.code = errorCode;
        this.msg = errorMsg;
    }


    public Integer getErrorCode() {
        return code;
    }

    public void setErrorCode(Integer errorCode) {
        this.code = errorCode;
    }

    public String getErrorMsg() {
        return msg;
    }

    public void setErrorMsg(String errorMsg) {
        this.msg = errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
