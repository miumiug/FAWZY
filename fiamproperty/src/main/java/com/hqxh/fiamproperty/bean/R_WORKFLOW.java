package com.hqxh.fiamproperty.bean;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 工作流返回结果
 */

public class R_WORKFLOW {
    private String errcode; //返回状态
    private String errmsg; //消息

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
}
