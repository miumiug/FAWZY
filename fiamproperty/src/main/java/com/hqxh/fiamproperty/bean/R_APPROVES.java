package com.hqxh.fiamproperty.bean;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 工作流返回结果
 */

public class R_APPROVES {
    private String errcode; //返回状态
    private String errmsg; //消息
    private List<String> result;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

}
