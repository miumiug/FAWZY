package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 结果集
 */

public class R_Wfassignemt {
    private String errcode; //返回状态
    private String errmsg; //消息
    private String userid; //用户名
    private ResultBean result;

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    /**结果集
     * curpage
     * resultlist
     * showcount
     * totalpage
     * totalresult
     * **/
    public static class ResultBean {
        private String curpage; //当前页
        private String showcount;//显示条数
        private String totalpage;//总共页数
        private String totalresult;//总共条数
        private List<Wfassignment> resultlist;

        public String getCurpage() {
            return curpage;
        }

        public void setCurpage(String curpage) {
            this.curpage = curpage;
        }


        public String getShowcount() {
            return showcount;
        }

        public void setShowcount(String showcount) {
            this.showcount = showcount;
        }

        public String getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(String totalpage) {
            this.totalpage = totalpage;
        }

        public String getTotalresult() {
            return totalresult;
        }

        public void setTotalresult(String totalresult) {
            this.totalresult = totalresult;
        }

        public List<Wfassignment> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<Wfassignment> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**待办任务
     *
     * **/
    public static class  Wfassignment{

        private String  APP; //应用程序
        private String  ASSIGNCODE;//人员
        private String  ASSIGNSTATUS;//状态
        private String  DESCRIPTION;//主题
        private String  DUEDATE;//日期
        private String  OWNERID;//唯一标识
        private String  OWNERNUM;//记录编号
        private String  OWNERTABLE;//对象名
        private String  PROCESSNAME;//流程
        private String  PROCESSREV;//版本
        private String  UDAPPNAME;//记录描述
        private String  WFASSIGNMENTID;


        public String getAPP() {
            return APP;
        }

        public void setAPP(String APP) {
            this.APP = APP;
        }

        public String getASSIGNCODE() {
            return ASSIGNCODE;
        }

        public void setASSIGNCODE(String ASSIGNCODE) {
            this.ASSIGNCODE = ASSIGNCODE;
        }

        public String getASSIGNSTATUS() {
            return ASSIGNSTATUS;
        }

        public void setASSIGNSTATUS(String ASSIGNSTATUS) {
            this.ASSIGNSTATUS = ASSIGNSTATUS;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getDUEDATE() {
            return DUEDATE;
        }

        public void setDUEDATE(String DUEDATE) {
            this.DUEDATE = DUEDATE;
        }

        public String getOWNERID() {
            return OWNERID;
        }

        public void setOWNERID(String OWNERID) {
            this.OWNERID = OWNERID;
        }

        public String getOWNERNUM() {
            return OWNERNUM;
        }

        public void setOWNERNUM(String OWNERNUM) {
            this.OWNERNUM = OWNERNUM;
        }

        public String getOWNERTABLE() {
            return OWNERTABLE;
        }

        public void setOWNERTABLE(String OWNERTABLE) {
            this.OWNERTABLE = OWNERTABLE;
        }

        public String getPROCESSNAME() {
            return PROCESSNAME;
        }

        public void setPROCESSNAME(String PROCESSNAME) {
            this.PROCESSNAME = PROCESSNAME;
        }

        public String getPROCESSREV() {
            return PROCESSREV;
        }

        public void setPROCESSREV(String PROCESSREV) {
            this.PROCESSREV = PROCESSREV;
        }

        public String getUDAPPNAME() {
            return UDAPPNAME;
        }

        public void setUDAPPNAME(String UDAPPNAME) {
            this.UDAPPNAME = UDAPPNAME;
        }

        public String getWFASSIGNMENTID() {
            return WFASSIGNMENTID;
        }

        public void setWFASSIGNMENTID(String WFASSIGNMENTID) {
            this.WFASSIGNMENTID = WFASSIGNMENTID;
        }


    }



}
