package com.hqxh.fiamproperty.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\7\5 0005.
 */

public class R_APPROVESS {
    private String errcode; //返回状态
    private String errmsg; //消息
    private ArrayList<Result> result;

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

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public static class Result {
        private int actionid;
        private String instruction;
        private int ispositive;

        public int getActionid() {
            return actionid;
        }

        public void setActionid(int actionid) {
            this.actionid = actionid;
        }

        public String getInstruction() {
            return instruction;
        }

        public void setInstruction(String instruction) {
            this.instruction = instruction;
        }

        public int getIspositive() {
            return ispositive;
        }

        public void setIspositive(int ispositive) {
            this.ispositive = ispositive;
        }
    }
}
