package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 需款计划
 */

public class R_PAYPLAN{
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
        private List<PAYPLAN> resultlist; //记录

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

        public List<PAYPLAN> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<PAYPLAN> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     *需款计划
     * **/
    public static class  PAYPLAN extends Entity{
        private String PAYPLANID;
        private String  TYPE; //类型
        private String  TOTALCOST;//需款金额
        private String  CONTRACTNUM;//合同号
        private String  CONTRACTDESC;//合同名称
        private String  PHASE;//付款阶段
        private String  VENDORNAME;//供应商名称
        private String  WONUM2;//出差申请
        private String  ISBOPAYPLAN;//借款需款
        private String  ENTERBYNAME;//申请人
        private String  ENTERDATE;//申请时间
        private String  DEPARTMENT;//部门
        private String  CREW;//科室
        private String  PAYPLANNUM;//需款计划
        private String  DESCRIPTION;//描述
        private String  MONTH;//年月
        private String  STATUSDESC;//状态

        public String getPAYPLANID() {
            return PAYPLANID;
        }

        public void setPAYPLANID(String PAYPLANID) {
            this.PAYPLANID = PAYPLANID;
        }

        public String getTYPE() {
            return TYPE;
        }

        public void setTYPE(String TYPE) {
            this.TYPE = TYPE;
        }

        public String getTOTALCOST() {
            return TOTALCOST;
        }

        public void setTOTALCOST(String TOTALCOST) {
            this.TOTALCOST = TOTALCOST;
        }

        public String getCONTRACTNUM() {
            return CONTRACTNUM;
        }

        public void setCONTRACTNUM(String CONTRACTNUM) {
            this.CONTRACTNUM = CONTRACTNUM;
        }

        public String getCONTRACTDESC() {
            return CONTRACTDESC;
        }

        public void setCONTRACTDESC(String CONTRACTDESC) {
            this.CONTRACTDESC = CONTRACTDESC;
        }

        public String getPHASE() {
            return PHASE;
        }

        public void setPHASE(String PHASE) {
            this.PHASE = PHASE;
        }

        public String getVENDORNAME() {
            return VENDORNAME;
        }

        public void setVENDORNAME(String VENDORNAME) {
            this.VENDORNAME = VENDORNAME;
        }

        public String getWONUM2() {
            return WONUM2;
        }

        public void setWONUM2(String WONUM2) {
            this.WONUM2 = WONUM2;
        }

        public String getISBOPAYPLAN() {
            return ISBOPAYPLAN;
        }

        public void setISBOPAYPLAN(String ISBOPAYPLAN) {
            this.ISBOPAYPLAN = ISBOPAYPLAN;
        }

        public String getENTERBYNAME() {
            return ENTERBYNAME;
        }

        public void setENTERBYNAME(String ENTERBYNAME) {
            this.ENTERBYNAME = ENTERBYNAME;
        }

        public String getENTERDATE() {
            return ENTERDATE;
        }

        public void setENTERDATE(String ENTERDATE) {
            this.ENTERDATE = ENTERDATE;
        }

        public String getDEPARTMENT() {
            return DEPARTMENT;
        }

        public void setDEPARTMENT(String DEPARTMENT) {
            this.DEPARTMENT = DEPARTMENT;
        }

        public String getCREW() {
            return CREW;
        }

        public void setCREW(String CREW) {
            this.CREW = CREW;
        }

        public String getPAYPLANNUM() {
            return PAYPLANNUM;
        }

        public void setPAYPLANNUM(String PAYPLANNUM) {
            this.PAYPLANNUM = PAYPLANNUM;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getMONTH() {
            return MONTH;
        }

        public void setMONTH(String MONTH) {
            this.MONTH = MONTH;
        }

        public String getSTATUSDESC() {
            return STATUSDESC;
        }

        public void setSTATUSDESC(String STATUSDESC) {
            this.STATUSDESC = STATUSDESC;
        }
    }



}
