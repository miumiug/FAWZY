package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 合同
 */

public class R_PURCHVIEW {
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
        private List<PURCHVIEW> resultlist; //记录

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

        public List<PURCHVIEW> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<PURCHVIEW> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     *合同
     * **/
    public static class  PURCHVIEW extends Entity{

        private String  CONTRACTID; //contractid
        private String  UDCONTRACTNUM; //国外合同号
        private String  DESCRIPTION;//合同名称
        private String  UDCLASS;//合同类别
        private String  REVISIONNUM;//版本
        private String  UDTYPE2;//开口/闭口
        private String  STATUSDESC;//状态
        private String  UDCONTENT;//主要内容（摘要）
        private String  UDDIRECTION;//收付方向
        private String  UDPAYTERM1;//支付方式
        private String  UDIPROWNER;//知识产权归属
        private String  VENDORNAME;//供应商
        private String  TOTALBASECOST;//合同金额（人民币）
        private String  CUTOTALCOST;//外币合同额
        private String  CUCURRENCY;//外币币种
        private String  CUPURAGENT;//合同提出人
        private String  UDRECORDDATE;//提出日期
        private String  UDREQUESTDEPT;//提出部门
        private String  UDREQUESTCREW;//提出科室
        private String  SIGNDATE;//签订日期
        private String  STARTDATE;//开始日期
        private String  ENDDATE;//结束日期
        private String  OWNER;//合同责任人
        private String  UDOWNERDEPT;//责任部门
        private String  UDOWNERCREW;//责任科室
        private String  CONTRACTNUM;//合同号
        private String  PAYEDCOST;//已付金额

        public String getCONTRACTID() {
            return CONTRACTID;
        }

        public void setCONTRACTID(String CONTRACTID) {
            this.CONTRACTID = CONTRACTID;
        }

        public String getUDCONTRACTNUM() {
            return UDCONTRACTNUM;
        }

        public void setUDCONTRACTNUM(String UDCONTRACTNUM) {
            this.UDCONTRACTNUM = UDCONTRACTNUM;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getUDCLASS() {
            return UDCLASS;
        }

        public void setUDCLASS(String UDCLASS) {
            this.UDCLASS = UDCLASS;
        }

        public String getREVISIONNUM() {
            return REVISIONNUM;
        }

        public void setREVISIONNUM(String REVISIONNUM) {
            this.REVISIONNUM = REVISIONNUM;
        }

        public String getUDTYPE2() {
            return UDTYPE2;
        }

        public void setUDTYPE2(String UDTYPE2) {
            this.UDTYPE2 = UDTYPE2;
        }

        public String getSTATUSDESC() {
            return STATUSDESC;
        }

        public void setSTATUSDESC(String STATUSDESC) {
            this.STATUSDESC = STATUSDESC;
        }

        public String getUDCONTENT() {
            return UDCONTENT;
        }

        public void setUDCONTENT(String UDCONTENT) {
            this.UDCONTENT = UDCONTENT;
        }

        public String getUDDIRECTION() {
            return UDDIRECTION;
        }

        public void setUDDIRECTION(String UDDIRECTION) {
            this.UDDIRECTION = UDDIRECTION;
        }

        public String getUDPAYTERM1() {
            return UDPAYTERM1;
        }

        public void setUDPAYTERM1(String UDPAYTERM1) {
            this.UDPAYTERM1 = UDPAYTERM1;
        }

        public String getUDIPROWNER() {
            return UDIPROWNER;
        }

        public void setUDIPROWNER(String UDIPROWNER) {
            this.UDIPROWNER = UDIPROWNER;
        }

        public String getVENDORNAME() {
            return VENDORNAME;
        }

        public void setVENDORNAME(String VENDORNAME) {
            this.VENDORNAME = VENDORNAME;
        }

        public String getTOTALBASECOST() {
            return TOTALBASECOST;
        }

        public void setTOTALBASECOST(String TOTALBASECOST) {
            this.TOTALBASECOST = TOTALBASECOST;
        }

        public String getCUTOTALCOST() {
            return CUTOTALCOST;
        }

        public void setCUTOTALCOST(String CUTOTALCOST) {
            this.CUTOTALCOST = CUTOTALCOST;
        }

        public String getCUCURRENCY() {
            return CUCURRENCY;
        }

        public void setCUCURRENCY(String CUCURRENCY) {
            this.CUCURRENCY = CUCURRENCY;
        }

        public String getCUPURAGENT() {
            return CUPURAGENT;
        }

        public void setCUPURAGENT(String CUPURAGENT) {
            this.CUPURAGENT = CUPURAGENT;
        }

        public String getUDRECORDDATE() {
            return UDRECORDDATE;
        }

        public void setUDRECORDDATE(String UDRECORDDATE) {
            this.UDRECORDDATE = UDRECORDDATE;
        }

        public String getUDREQUESTDEPT() {
            return UDREQUESTDEPT;
        }

        public void setUDREQUESTDEPT(String UDREQUESTDEPT) {
            this.UDREQUESTDEPT = UDREQUESTDEPT;
        }

        public String getUDREQUESTCREW() {
            return UDREQUESTCREW;
        }

        public void setUDREQUESTCREW(String UDREQUESTCREW) {
            this.UDREQUESTCREW = UDREQUESTCREW;
        }

        public String getSIGNDATE() {
            return SIGNDATE;
        }

        public void setSIGNDATE(String SIGNDATE) {
            this.SIGNDATE = SIGNDATE;
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

        public String getOWNER() {
            return OWNER;
        }

        public void setOWNER(String OWNER) {
            this.OWNER = OWNER;
        }

        public String getUDOWNERDEPT() {
            return UDOWNERDEPT;
        }

        public void setUDOWNERDEPT(String UDOWNERDEPT) {
            this.UDOWNERDEPT = UDOWNERDEPT;
        }

        public String getUDOWNERCREW() {
            return UDOWNERCREW;
        }

        public void setUDOWNERCREW(String UDOWNERCREW) {
            this.UDOWNERCREW = UDOWNERCREW;
        }

        public String getCONTRACTNUM() {
            return CONTRACTNUM;
        }

        public void setCONTRACTNUM(String CONTRACTNUM) {
            this.CONTRACTNUM = CONTRACTNUM;
        }

        public String getPAYEDCOST() {
            return PAYEDCOST;
        }

        public void setPAYEDCOST(String PAYEDCOST) {
            this.PAYEDCOST = PAYEDCOST;
        }
    }



}
