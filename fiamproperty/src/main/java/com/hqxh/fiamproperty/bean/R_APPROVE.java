package com.hqxh.fiamproperty.bean;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 工作流返回结果
 */

public class R_APPROVE {
    private String errcode; //返回状态
    private String errmsg; //消息
    private List<Result> result;

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

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public static class Result {
        private String instruction;
        private String ispositive;

        public String getInstruction() {
            return instruction;
        }

        public void setInstruction(String instruction) {
            this.instruction = instruction;
        }

        public String getIspositive() {
            return ispositive;
        }

        public void setIspositive(String ispositive) {
            this.ispositive = ispositive;
        }
    }
}
