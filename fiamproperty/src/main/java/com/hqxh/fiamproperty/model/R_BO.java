package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 借款单
 */

public class R_BO {
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
        private List<BO> resultlist; //记录

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

        public List<BO> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<BO> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     * 需款计划
     **/
    public static class BO extends Entity {

        private String BOID; //BOID
        private String DEPARTMENT; //部门
        private String CONTRACTDESCR;//合同名称
        private String ADDRESS1;//地址
        private String BANKACCOUNT;//银行账号
        private String TYPE;//借款类别
        private String ENTERDATE;//借款时间
        private String PAYVENDOR;//收款单位
        private String ENTERBY;//借款人
        private String CREW;//科室
        private String PROJECTID;//费用号
        private String CONTRACTNUM;//合同号
        private String PRNUM;//采购申请
        private String PRDESC;//采购申请描述
        private String FINCNTRLDESC;//项目名称
        private String BANK;//开户行
        private String DESCRIPTION;//借款事由
        private String APPLYCOST;//申请金额
        private String TOTALCOST;//借款金额
        private String STATUSDESC;//状态
        private String BONUM;//借款单

        public String getBOID() {
            return BOID;
        }

        public void setBOID(String BOID) {
            this.BOID = BOID;
        }

        public String getDEPARTMENT() {
            return DEPARTMENT;
        }

        public void setDEPARTMENT(String DEPARTMENT) {
            this.DEPARTMENT = DEPARTMENT;
        }

        public String getCONTRACTDESCR() {
            return CONTRACTDESCR;
        }

        public void setCONTRACTDESCR(String CONTRACTDESCR) {
            this.CONTRACTDESCR = CONTRACTDESCR;
        }

        public String getADDRESS1() {
            return ADDRESS1;
        }

        public void setADDRESS1(String ADDRESS1) {
            this.ADDRESS1 = ADDRESS1;
        }

        public String getBANKACCOUNT() {
            return BANKACCOUNT;
        }

        public void setBANKACCOUNT(String BANKACCOUNT) {
            this.BANKACCOUNT = BANKACCOUNT;
        }

        public String getTYPE() {
            return TYPE;
        }

        public void setTYPE(String TYPE) {
            this.TYPE = TYPE;
        }

        public String getENTERDATE() {
            return ENTERDATE;
        }

        public void setENTERDATE(String ENTERDATE) {
            this.ENTERDATE = ENTERDATE;
        }

        public String getPAYVENDOR() {
            return PAYVENDOR;
        }

        public void setPAYVENDOR(String PAYVENDOR) {
            this.PAYVENDOR = PAYVENDOR;
        }

        public String getENTERBY() {
            return ENTERBY;
        }

        public void setENTERBY(String ENTERBY) {
            this.ENTERBY = ENTERBY;
        }

        public String getCREW() {
            return CREW;
        }

        public void setCREW(String CREW) {
            this.CREW = CREW;
        }

        public String getPROJECTID() {
            return PROJECTID;
        }

        public void setPROJECTID(String PROJECTID) {
            this.PROJECTID = PROJECTID;
        }

        public String getCONTRACTNUM() {
            return CONTRACTNUM;
        }

        public void setCONTRACTNUM(String CONTRACTNUM) {
            this.CONTRACTNUM = CONTRACTNUM;
        }

        public String getPRNUM() {
            return PRNUM;
        }

        public void setPRNUM(String PRNUM) {
            this.PRNUM = PRNUM;
        }

        public String getPRDESC() {
            return PRDESC;
        }

        public void setPRDESC(String PRDESC) {
            this.PRDESC = PRDESC;
        }

        public String getFINCNTRLDESC() {
            return FINCNTRLDESC;
        }

        public void setFINCNTRLDESC(String FINCNTRLDESC) {
            this.FINCNTRLDESC = FINCNTRLDESC;
        }

        public String getBANK() {
            return BANK;
        }

        public void setBANK(String BANK) {
            this.BANK = BANK;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getAPPLYCOST() {
            return APPLYCOST;
        }

        public void setAPPLYCOST(String APPLYCOST) {
            this.APPLYCOST = APPLYCOST;
        }

        public String getTOTALCOST() {
            return TOTALCOST;
        }

        public void setTOTALCOST(String TOTALCOST) {
            this.TOTALCOST = TOTALCOST;
        }

        public String getSTATUSDESC() {
            return STATUSDESC;
        }

        public void setSTATUSDESC(String STATUSDESC) {
            this.STATUSDESC = STATUSDESC;
        }

        public String getBONUM() {
            return BONUM;
        }

        public void setBONUM(String BONUM) {
            this.BONUM = BONUM;
        }
    }


}
