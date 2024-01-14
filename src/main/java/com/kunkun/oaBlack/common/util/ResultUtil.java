package com.kunkun.oaBlack.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.jdbc.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultUtil<T> {

    //成功状态
    private Boolean success;

    //状态码
    private Integer code;

    //返回信息
    private String msg;

    //返回数据
    private T data;

    public ResultUtil(BaseErrorInfoInterface errorInfo){
        this.code = errorInfo.getResultCode();
        this.msg = errorInfo.getResultMessage();

    }

    /**
     * 成功，但不返回data
     * @param <T>
     * @return
     */
    public static <T>ResultUtil<T> success(){
        return success(null);
    }

    /**
     * 注意泛型检查
     * @param data
     * @param <T>
     * @return
     */
    public static <T>ResultUtil<T> success(T data){
        ResultUtil<T> r  = new ResultUtil<>();
        r.setCode(CodeUtil.SUCCESS.getResultCode());
        r.setMsg(CodeUtil.SUCCESS.getResultMessage());
        r.setSuccess(true);
        r.setData(data);
        return r;
    }

    public static <T>ResultUtil<T> faile(BaseErrorInfoInterface errorInfo){
        ResultUtil<T> r = new ResultUtil<>();
        r.setCode(errorInfo.getResultCode());
        r.setMsg(errorInfo.getResultMessage());
        r.setSuccess(false);
        return r;
    }

    public static <T>ResultUtil<T> faile(T data){
        ResultUtil<T> r = new ResultUtil<>();
        r.setCode(CodeUtil.INTERNAL_SERVER_ERROR.getResultCode());
        r.setMsg(CodeUtil.INTERNAL_SERVER_ERROR.getResultMessage());
        r.setSuccess(false);
        r.setData(data);
        return r;
    }

    public static <T>ResultUtil<T> faile(Integer code,String msg){
        ResultUtil<T> r = new ResultUtil<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setSuccess(false);
        r.setData(null);
        return r;
    }

    public static <T>ResultUtil<T> faile(String msg){
        ResultUtil<T> r = new ResultUtil<>();
        r.setCode(-1);
        r.setMsg(msg);
        r.setSuccess(false);
        r.setData(null);
        return r;
    }

    @Override
    public String toString(){
        return JSONObject.toJSONString(this);
    }
}
