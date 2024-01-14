package com.kunkun.oaBlack.common.util;

public class BizException extends RuntimeException {

    protected  Integer code;
    protected String  msg;

    public BizException(){
        super();
    }

    public BizException(BaseErrorInfoInterface errorInfo){
        super(errorInfo.getResultCode().toString());
        this.code = errorInfo.getResultCode();
        this.msg = errorInfo.getResultMessage();
    }

    public BizException(BaseErrorInfoInterface baseErrorInfoInterface, Throwable cause){
        super(baseErrorInfoInterface.getResultCode().toString(),cause);
        this.msg = baseErrorInfoInterface.getResultMessage();
        this.code = baseErrorInfoInterface.getResultCode();
    }

    public BizException(String msg){
        super(msg);
        this.msg = msg;
    }

    public BizException(Integer code,String msg){
        super(code.toString());
        this.code = code;
        this.msg = msg;
    }
    public BizException(Integer errorCode, String errorMsg, Throwable cause) {
        super(errorCode.toString(), cause);
        this.code = errorCode;
        this.msg = errorMsg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
