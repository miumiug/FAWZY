package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 差旅报销单
 */

public class R_EXPENSE {
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
        private List<EXPENSE> resultlist; //记录

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

        public List<EXPENSE> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<EXPENSE> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     * 差旅报销单
     **/
    public static class EXPENSE extends Entity {

        private String EXPENSEID; //EXPENSEID
        private String EXPENSENUM; //报销单
        private String DESCRIPTION;//报销事由
        private String WFTYPE;//报销类别／类型
        private String WONUM2;//出差申请
        private String DEPARTMENT;//部门
        private String CREW;//科室
        private String ACTSTART;//开始日期
        private String ACTFINISH;//结束日期
        private String ACTDAYS;//出差天数
        private String PAYVENDOR;//收款单位
        private String BANKACCOUNT;//账号
        private String BOCOST;//借款金额
        private String PROJECTID;//费用号
        private String FINCNTRLDESC;//项目名称
        private String PRIMARYPHONE;//联系电话
        private String COST6;//补助合计
        private String BANK;//开户行
        private String ADDRESS1;//收款单位地址
        private String STATUSDESC;//状态
        private String WORKORDER2DESC;//出差申请描述
        private String TOTALCOST;//报销金额
        private String ENTERBY;//报销人
        private String ENTERDATE;//报销日期

        //备用金报销
        private String CONTRACTVENDOR;//供应商
        private String WONUM;//任务单
        private String CONTRACTNUM;//合同号
        private String CONTRACTDESC;//合同名称
        private String WORKORDERDESC;//任务单名称
        private String PRNUM;//采购申请
        private String PRDESC;//采购申请描述
        private String TAX;//税额
        private String PAYCHECKCOST;//验收金额


        public String getEXPENSEID() {
            return EXPENSEID;
        }

        public void setEXPENSEID(String EXPENSEID) {
            this.EXPENSEID = EXPENSEID;
        }

        public String getEXPENSENUM() {
            return EXPENSENUM;
        }

        public void setEXPENSENUM(String EXPENSENUM) {
            this.EXPENSENUM = EXPENSENUM;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getWFTYPE() {
            return WFTYPE;
        }

        public void setWFTYPE(String WFTYPE) {
            this.WFTYPE = WFTYPE;
        }

        public String getWONUM2() {
            return WONUM2;
        }

        public void setWONUM2(String WONUM2) {
            this.WONUM2 = WONUM2;
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

        public String getACTSTART() {
            return ACTSTART;
        }

        public void setACTSTART(String ACTSTART) {
            this.ACTSTART = ACTSTART;
        }

        public String getACTFINISH() {
            return ACTFINISH;
        }

        public void setACTFINISH(String ACTFINISH) {
            this.ACTFINISH = ACTFINISH;
        }

        public String getACTDAYS() {
            return ACTDAYS;
        }

        public void setACTDAYS(String ACTDAYS) {
            this.ACTDAYS = ACTDAYS;
        }

        public String getPAYVENDOR() {
            return PAYVENDOR;
        }

        public void setPAYVENDOR(String PAYVENDOR) {
            this.PAYVENDOR = PAYVENDOR;
        }

        public String getBANKACCOUNT() {
            return BANKACCOUNT;
        }

        public void setBANKACCOUNT(String BANKACCOUNT) {
            this.BANKACCOUNT = BANKACCOUNT;
        }

        public String getBOCOST() {
            return BOCOST;
        }

        public void setBOCOST(String BOCOST) {
            this.BOCOST = BOCOST;
        }

        public String getPROJECTID() {
            return PROJECTID;
        }

        public void setPROJECTID(String PROJECTID) {
            this.PROJECTID = PROJECTID;
        }

        public String getFINCNTRLDESC() {
            return FINCNTRLDESC;
        }

        public void setFINCNTRLDESC(String FINCNTRLDESC) {
            this.FINCNTRLDESC = FINCNTRLDESC;
        }

        public String getPRIMARYPHONE() {
            return PRIMARYPHONE;
        }

        public void setPRIMARYPHONE(String PRIMARYPHONE) {
            this.PRIMARYPHONE = PRIMARYPHONE;
        }

        public String getCOST6() {
            return COST6;
        }

        public void setCOST6(String COST6) {
            this.COST6 = COST6;
        }

        public String getBANK() {
            return BANK;
        }

        public void setBANK(String BANK) {
            this.BANK = BANK;
        }

        public String getADDRESS1() {
            return ADDRESS1;
        }

        public void setADDRESS1(String ADDRESS1) {
            this.ADDRESS1 = ADDRESS1;
        }

        public String getSTATUSDESC() {
            return STATUSDESC;
        }

        public void setSTATUSDESC(String STATUSDESC) {
            this.STATUSDESC = STATUSDESC;
        }

        public String getWORKORDER2DESC() {
            return WORKORDER2DESC;
        }

        public void setWORKORDER2DESC(String WORKORDER2DESC) {
            this.WORKORDER2DESC = WORKORDER2DESC;
        }

        public String getTOTALCOST() {
            return TOTALCOST;
        }

        public void setTOTALCOST(String TOTALCOST) {
            this.TOTALCOST = TOTALCOST;
        }

        public String getENTERBY() {
            return ENTERBY;
        }

        public void setENTERBY(String ENTERBY) {
            this.ENTERBY = ENTERBY;
        }

        public String getENTERDATE() {
            return ENTERDATE;
        }

        public void setENTERDATE(String ENTERDATE) {
            this.ENTERDATE = ENTERDATE;
        }

        public String getCONTRACTVENDOR() {
            return CONTRACTVENDOR;
        }

        public void setCONTRACTVENDOR(String CONTRACTVENDOR) {
            this.CONTRACTVENDOR = CONTRACTVENDOR;
        }

        public String getWONUM() {
            return WONUM;
        }

        public void setWONUM(String WONUM) {
            this.WONUM = WONUM;
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

        public String getWORKORDERDESC() {
            return WORKORDERDESC;
        }

        public void setWORKORDERDESC(String WORKORDERDESC) {
            this.WORKORDERDESC = WORKORDERDESC;
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

        public String getTAX() {
            return TAX;
        }

        public void setTAX(String TAX) {
            this.TAX = TAX;
        }

        public String getPAYCHECKCOST() {
            return PAYCHECKCOST;
        }

        public void setPAYCHECKCOST(String PAYCHECKCOST) {
            this.PAYCHECKCOST = PAYCHECKCOST;
        }
    }


}
