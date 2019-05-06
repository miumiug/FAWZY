package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 付款验收
 */

public class R_PAYCHECK {
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
        private List<PAYCHECK> resultlist; //记录

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

        public List<PAYCHECK> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<PAYCHECK> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     *付款验收
     * **/
    public static class  PAYCHECK extends Entity{

        private String  PAYCHECKID; //PAYCHECKID
        private String  TOTALCOST; //验收金额
        private String  VENDORNAME;//供应商
        private String  FINCNTRLDESC;//项目名称
        private String  WONUM;//任务单
        private String  WORKORDERDESC;//任务单描述
        private String  ISFINALCHECK;//最后一次验收
        private String  PHASE;//验收阶段
        private String  PROJECTID;//费用号
        private String  CONTRACTDESC;//合同名称
        private String  PRNUM;//采购申请
        private String  PRDESC;//采购申请描述
        private String  CHECKE;//验收人
        private String  ENTERBY;//提单人
        private String  PC1;//交付物和验收内容
        private String  PC2;//验收结论/意见
        private String  PAYCHECKNUM;//验收单
        private String  DESCRIPTION;//描述
        private String  CHECKTYPE;//类型
        private String  STATUSDESC;//状态
        private String  CONTRACTNUM;//合同号
        private String  CHECKDATE;//验收时间
        private String  ENTERDATE;//提单日期
        private String  CUDEPT;//提单部门
        private String  CUCREW;//提单科室
        private String  WFPERSON1;//项目经理

        public String getPAYCHECKID() {
            return PAYCHECKID;
        }

        public void setPAYCHECKID(String PAYCHECKID) {
            this.PAYCHECKID = PAYCHECKID;
        }

        public String getTOTALCOST() {
            return TOTALCOST;
        }

        public void setTOTALCOST(String TOTALCOST) {
            this.TOTALCOST = TOTALCOST;
        }

        public String getVENDORNAME() {
            return VENDORNAME;
        }

        public void setVENDORNAME(String VENDORNAME) {
            this.VENDORNAME = VENDORNAME;
        }

        public String getFINCNTRLDESC() {
            return FINCNTRLDESC;
        }

        public void setFINCNTRLDESC(String FINCNTRLDESC) {
            this.FINCNTRLDESC = FINCNTRLDESC;
        }

        public String getWONUM() {
            return WONUM;
        }

        public void setWONUM(String WONUM) {
            this.WONUM = WONUM;
        }

        public String getWORKORDERDESC() {
            return WORKORDERDESC;
        }

        public void setWORKORDERDESC(String WORKORDERDESC) {
            this.WORKORDERDESC = WORKORDERDESC;
        }

        public String getISFINALCHECK() {
            return ISFINALCHECK;
        }

        public void setISFINALCHECK(String ISFINALCHECK) {
            this.ISFINALCHECK = ISFINALCHECK;
        }

        public String getPHASE() {
            return PHASE;
        }

        public void setPHASE(String PHASE) {
            this.PHASE = PHASE;
        }

        public String getPROJECTID() {
            return PROJECTID;
        }

        public void setPROJECTID(String PROJECTID) {
            this.PROJECTID = PROJECTID;
        }

        public String getCONTRACTDESC() {
            return CONTRACTDESC;
        }

        public void setCONTRACTDESC(String CONTRACTDESC) {
            this.CONTRACTDESC = CONTRACTDESC;
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

        public String getCHECKE() {
            return CHECKE;
        }

        public void setCHECKE(String CHECKE) {
            this.CHECKE = CHECKE;
        }

        public String getENTERBY() {
            return ENTERBY;
        }

        public void setENTERBY(String ENTERBY) {
            this.ENTERBY = ENTERBY;
        }

        public String getPC1() {
            return PC1;
        }

        public void setPC1(String PC1) {
            this.PC1 = PC1;
        }

        public String getPC2() {
            return PC2;
        }

        public void setPC2(String PC2) {
            this.PC2 = PC2;
        }

        public String getPAYCHECKNUM() {
            return PAYCHECKNUM;
        }

        public void setPAYCHECKNUM(String PAYCHECKNUM) {
            this.PAYCHECKNUM = PAYCHECKNUM;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getCHECKTYPE() {
            return CHECKTYPE;
        }

        public void setCHECKTYPE(String CHECKTYPE) {
            this.CHECKTYPE = CHECKTYPE;
        }

        public String getSTATUSDESC() {
            return STATUSDESC;
        }

        public void setSTATUSDESC(String STATUSDESC) {
            this.STATUSDESC = STATUSDESC;
        }

        public String getCONTRACTNUM() {
            return CONTRACTNUM;
        }

        public void setCONTRACTNUM(String CONTRACTNUM) {
            this.CONTRACTNUM = CONTRACTNUM;
        }

        public String getCHECKDATE() {
            return CHECKDATE;
        }

        public void setCHECKDATE(String CHECKDATE) {
            this.CHECKDATE = CHECKDATE;
        }

        public String getENTERDATE() {
            return ENTERDATE;
        }

        public void setENTERDATE(String ENTERDATE) {
            this.ENTERDATE = ENTERDATE;
        }

        public String getCUDEPT() {
            return CUDEPT;
        }

        public void setCUDEPT(String CUDEPT) {
            this.CUDEPT = CUDEPT;
        }

        public String getCUCREW() {
            return CUCREW;
        }

        public void setCUCREW(String CUCREW) {
            this.CUCREW = CUCREW;
        }

        public String getWFPERSON1() {
            return WFPERSON1;
        }

        public void setWFPERSON1(String WFPERSON1) {
            this.WFPERSON1 = WFPERSON1;
        }
    }



}
