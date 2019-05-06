package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 差旅报销单-外出试验日志(补助明细)
 */

public class R_OUTTESTLINE {
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

    /**
     * 结果集
     * curpage
     * resultlist
     * showcount
     * totalpage
     * totalresult
     **/
    public static class ResultBean {
        private String curpage; //当前页
        private String showcount;//显示条数
        private String totalpage;//总共页数
        private String totalresult;//总共条数
        private List<OUTTESTLINE> resultlist; //记录

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

        public List<OUTTESTLINE> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<OUTTESTLINE> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     * 差旅报销单-外出试验日志(补助明细)
     **/
    public static class OUTTESTLINE extends Entity {
        private String LINENUM; //行
        private String PEGIONTYPE;//地区类别
        private String PERSONTYPE;//人员类别
        private String STAYNAME;//住宿人员
        private String STARTDATE;//入住日期
        private String ENDDATE;//离开日期
        private String STAYDAYS;//补助天数
        private String LINECOST;//补助总额
        private String STANDARD;//补助标准
        private String PLACE;//地点

        public String getLINENUM() {
            return LINENUM;
        }

        public void setLINENUM(String LINENUM) {
            this.LINENUM = LINENUM;
        }

        public String getPEGIONTYPE() {
            return PEGIONTYPE;
        }

        public void setPEGIONTYPE(String PEGIONTYPE) {
            this.PEGIONTYPE = PEGIONTYPE;
        }

        public String getPERSONTYPE() {
            return PERSONTYPE;
        }

        public void setPERSONTYPE(String PERSONTYPE) {
            this.PERSONTYPE = PERSONTYPE;
        }

        public String getSTAYNAME() {
            return STAYNAME;
        }

        public void setSTAYNAME(String STAYNAME) {
            this.STAYNAME = STAYNAME;
        }

        public String getSTARTDATE() {
            return STARTDATE;
        }

        public void setSTARTDATE(String STARTDATE) {
            this.STARTDATE = STARTDATE;
        }

        public String getENDDATE() {
            return ENDDATE;
        }

        public void setENDDATE(String ENDDATE) {
            this.ENDDATE = ENDDATE;
        }

        public String getSTAYDAYS() {
            return STAYDAYS;
        }

        public void setSTAYDAYS(String STAYDAYS) {
            this.STAYDAYS = STAYDAYS;
        }

        public String getLINECOST() {
            return LINECOST;
        }

        public void setLINECOST(String LINECOST) {
            this.LINECOST = LINECOST;
        }

        public String getSTANDARD() {
            return STANDARD;
        }

        public void setSTANDARD(String STANDARD) {
            this.STANDARD = STANDARD;
        }

        public String getPLACE() {
            return PLACE;
        }

        public void setPLACE(String PLACE) {
            this.PLACE = PLACE;
        }
    }


}
