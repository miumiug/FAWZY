package com.hqxh.fiamproperty.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 出差人
 */

public class R_PERSONRELATION {
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
        private List<PERSONRELATION> resultlist; //记录

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

        public List<PERSONRELATION> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<PERSONRELATION> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     * 出差人
     **/
    public static class PERSONRELATION implements Cloneable, Serializable {

        /**
         * ACTUALCOST : 0.0,notreadonly
         * CLASS : EXPENSE,notreadonly
         * CUCREW : 底盘试验室,notreadonly
         * CUDEPT : 中重型车车型部,notreadonly
         * DISPLAYNAME : 艾正冬,notreadonly
         * ENDDATE : ,notreadonly
         * IFMESSAGE : ,notreadonly
         * ISONLINE : 0,notreadonly
         * LINECOST : 2550.0,notreadonly
         * NAME : 艾正冬,notreadonly
         * PERSONID : AIZHENGDONG,notreadonly
         * PERSONRELATIONID : 212522,readonly
         * PERSONTYPE : 技术管理人员,notreadonly
         * PLACEA : ,notreadonly
         * PLACEZ : ,notreadonly
         * STARTDATE : ,notreadonly
         * TITLE : ,notreadonly
         * TRANSPORT : ,notreadonly
         * TRVCOST2 : 480.0,notreadonly
         * TRVCOST3 : 0.0,notreadonly
         * TRVCOST4 : 0.0,notreadonly
         * TRVCOST5 : 0.0,notreadonly
         * TRVCOST6 : 0.0,notreadonly
         * TRVCOST7 : 0.0,notreadonly
         * TRVCOST8 : 0.0,notreadonly
         * TRVCOST9 : 0.0,notreadonly
         * TRVDAYS : 0,notreadonly
         * WONUM : ,notreadonly
         */

        private String ACTUALCOST;
        private String CLASS;
        private String CUCREW;
        private String CUDEPT;
        private String DISPLAYNAME;
        private String ENDDATE;
        private String IFMESSAGE;
        private String ISONLINE;
        private String LINECOST;
        private String NAME;
        private String PERSONID;
        private String PERSONRELATIONID;
        private String PERSONTYPE;
        private String PLACEA;
        private String PLACEZ;
        private String STARTDATE;
        private String TITLE;
        private String TRANSPORT;
        private String TRVCOST2;
        private String TRVCOST3;
        private String TRVCOST4;
        private String TRVCOST5;
        private String TRVCOST6;
        private String TRVCOST7;
        private String TRVCOST8;
        private String TRVCOST9;
        private String TRVDAYS;
        private String WONUM;

        /*差旅报销*/
        private String PERSONNAME;
        private String TRVCOST1;

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public String getPERSONNAME() {
            return PERSONNAME;
        }

        public void setPERSONNAME(String PERSONNAME) {
            this.PERSONNAME = PERSONNAME;
        }

        public String getTRVCOST1() {
            return TRVCOST1;
        }

        public void setTRVCOST1(String TRVCOST1) {
            this.TRVCOST1 = TRVCOST1;
        }

        public String getACTUALCOST() {
            return ACTUALCOST;
        }

        public void setACTUALCOST(String ACTUALCOST) {
            this.ACTUALCOST = ACTUALCOST;
        }

        public String getCLASS() {
            return CLASS;
        }

        public void setCLASS(String CLASS) {
            this.CLASS = CLASS;
        }

        public String getCUCREW() {
            return CUCREW;
        }

        public void setCUCREW(String CUCREW) {
            this.CUCREW = CUCREW;
        }

        public String getCUDEPT() {
            return CUDEPT;
        }

        public void setCUDEPT(String CUDEPT) {
            this.CUDEPT = CUDEPT;
        }

        public String getDISPLAYNAME() {
            return DISPLAYNAME;
        }

        public void setDISPLAYNAME(String DISPLAYNAME) {
            this.DISPLAYNAME = DISPLAYNAME;
        }

        public String getENDDATE() {
            return ENDDATE;
        }

        public void setENDDATE(String ENDDATE) {
            this.ENDDATE = ENDDATE;
        }

        public String getIFMESSAGE() {
            return IFMESSAGE;
        }

        public void setIFMESSAGE(String IFMESSAGE) {
            this.IFMESSAGE = IFMESSAGE;
        }

        public String getISONLINE() {
            return ISONLINE;
        }

        public void setISONLINE(String ISONLINE) {
            this.ISONLINE = ISONLINE;
        }

        public String getLINECOST() {
            return LINECOST;
        }

        public void setLINECOST(String LINECOST) {
            this.LINECOST = LINECOST;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getPERSONID() {
            return PERSONID;
        }

        public void setPERSONID(String PERSONID) {
            this.PERSONID = PERSONID;
        }

        public String getPERSONRELATIONID() {
            return PERSONRELATIONID;
        }

        public void setPERSONRELATIONID(String PERSONRELATIONID) {
            this.PERSONRELATIONID = PERSONRELATIONID;
        }

        public String getPERSONTYPE() {
            return PERSONTYPE;
        }

        public void setPERSONTYPE(String PERSONTYPE) {
            this.PERSONTYPE = PERSONTYPE;
        }

        public String getPLACEA() {
            return PLACEA;
        }

        public void setPLACEA(String PLACEA) {
            this.PLACEA = PLACEA;
        }

        public String getPLACEZ() {
            return PLACEZ;
        }

        public void setPLACEZ(String PLACEZ) {
            this.PLACEZ = PLACEZ;
        }

        public String getSTARTDATE() {
            return STARTDATE;
        }

        public void setSTARTDATE(String STARTDATE) {
            this.STARTDATE = STARTDATE;
        }

        public String getTITLE() {
            return TITLE;
        }

        public void setTITLE(String TITLE) {
            this.TITLE = TITLE;
        }

        public String getTRANSPORT() {
            return TRANSPORT;
        }

        public void setTRANSPORT(String TRANSPORT) {
            this.TRANSPORT = TRANSPORT;
        }

        public String getTRVCOST2() {
            return TRVCOST2;
        }

        public void setTRVCOST2(String TRVCOST2) {
            this.TRVCOST2 = TRVCOST2;
        }

        public String getTRVCOST3() {
            return TRVCOST3;
        }

        public void setTRVCOST3(String TRVCOST3) {
            this.TRVCOST3 = TRVCOST3;
        }

        public String getTRVCOST4() {
            return TRVCOST4;
        }

        public void setTRVCOST4(String TRVCOST4) {
            this.TRVCOST4 = TRVCOST4;
        }

        public String getTRVCOST5() {
            return TRVCOST5;
        }

        public void setTRVCOST5(String TRVCOST5) {
            this.TRVCOST5 = TRVCOST5;
        }

        public String getTRVCOST6() {
            return TRVCOST6;
        }

        public void setTRVCOST6(String TRVCOST6) {
            this.TRVCOST6 = TRVCOST6;
        }

        public String getTRVCOST7() {
            return TRVCOST7;
        }

        public void setTRVCOST7(String TRVCOST7) {
            this.TRVCOST7 = TRVCOST7;
        }

        public String getTRVCOST8() {
            return TRVCOST8;
        }

        public void setTRVCOST8(String TRVCOST8) {
            this.TRVCOST8 = TRVCOST8;
        }

        public String getTRVCOST9() {
            return TRVCOST9;
        }

        public void setTRVCOST9(String TRVCOST9) {
            this.TRVCOST9 = TRVCOST9;
        }

        public String getTRVDAYS() {
            return TRVDAYS;
        }

        public void setTRVDAYS(String TRVDAYS) {
            this.TRVDAYS = TRVDAYS;
        }

        public String getWONUM() {
            return WONUM;
        }

        public void setWONUM(String WONUM) {
            this.WONUM = WONUM;
        }
    }


}
