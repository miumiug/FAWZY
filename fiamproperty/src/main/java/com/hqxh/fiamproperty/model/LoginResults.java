package com.hqxh.fiamproperty.model;

import java.io.Serializable;

/**
 * Created by admin on 2015/11/23.
 */
public class LoginResults implements Serializable {

    /**返回码**/
    private String errcode;
    private String result;
    private String errmsg;


    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    /**返回信息**/


    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }



    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
