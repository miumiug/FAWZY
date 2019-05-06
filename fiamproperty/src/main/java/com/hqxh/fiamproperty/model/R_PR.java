package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 采购申请
 */

public class R_PR {
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
        private List<PR> resultlist; //记录

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

        public List<PR> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<PR> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     * 技术采购，试制采购
     **/
    public static class PR extends Entity {
        private String PRID;
        private String ASSIGNERNAME; //执行人姓名
        private String DESCRIPTION;//描述
        private String PR6;//总预算（外币）
        private String UDCURRENCYCODE;//货币
        private String UDDATE1;//开始日期
        private String UDDATE2;//结束日期
        private String RDCHEAD;//中心分管领导代码
        private String UDLXTYPE;//立项类型
        private String REQBYPHONE;//申请人电话
        private String CUDEPT;//申请部门
        private String CUCREW;//申请科室
        private String PRNUM;//采购申请
        private String PM;//项目经理
        private String UDASSIGNER;//执行人代码
        private String REQBYPERSON;//申请人
        private String PROJECTID;//费用号
        private String PROJECTDESC;//项目名称
        private String UDREMARK6;//通过本次采购自身能力提升内容
        private String UDREMARK4;//适用范围
        private String UDREMARK2;//项目内容
        private String UDREMARK3;//项目预期目标
        private String CUTYPE;//类型
        private String STATUSDESC;//状态
        private String TOTALCOST;//总预算（人民币）
        private String RDCHEADNAME;//中心分管领导姓名
        private String UDREMARK1;//立项必要性和原因
        private String ISSUEDATE;//申请日期
        private String PCOWNER;//执行人
        private String UDREMARK;//备注
        private String UDPAYTHISYEAR;//本年度结算
        private String OWNERPERSON;//预算员／子项目经理
        private String CUSAMPLETYPE;//样品分类
        private String NEWVENDOR;//指定的供应商
        private String CONTACT;//联系人
        private String PHONENUM;//供应商电话
        private String SERVICETYPE;//类型
        private String WONUM;//任务单

        //试制采购申请单
        private String VENDOR; //供应商
        private String VENDORNAME; //供应商名称
        private String RDCHEADPERSON; //中心分管领导名称
        //物资采购申请单

        private String UDDATE3; //结算完成日期

        public String getPRID() {
            return PRID;
        }

        public void setPRID(String PRID) {
            this.PRID = PRID;
        }

        public String getSERVICETYPE() {
            return SERVICETYPE;
        }

        public void setSERVICETYPE(String SERVICETYPE) {
            this.SERVICETYPE = SERVICETYPE;
        }


        public String getASSIGNERNAME() {
            return ASSIGNERNAME;
        }

        public void setASSIGNERNAME(String ASSIGNERNAME) {
            this.ASSIGNERNAME = ASSIGNERNAME;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getPR6() {
            return PR6;
        }

        public void setPR6(String PR6) {
            this.PR6 = PR6;
        }

        public String getUDCURRENCYCODE() {
            return UDCURRENCYCODE;
        }

        public void setUDCURRENCYCODE(String UDCURRENCYCODE) {
            this.UDCURRENCYCODE = UDCURRENCYCODE;
        }

        public String getUDDATE1() {
            return UDDATE1;
        }

        public void setUDDATE1(String UDDATE1) {
            this.UDDATE1 = UDDATE1;
        }

        public String getUDDATE2() {
            return UDDATE2;
        }

        public void setUDDATE2(String UDDATE2) {
            this.UDDATE2 = UDDATE2;
        }

        public String getRDCHEAD() {
            return RDCHEAD;
        }

        public void setRDCHEAD(String RDCHEAD) {
            this.RDCHEAD = RDCHEAD;
        }

        public String getUDLXTYPE() {
            return UDLXTYPE;
        }

        public void setUDLXTYPE(String UDLXTYPE) {
            this.UDLXTYPE = UDLXTYPE;
        }

        public String getREQBYPHONE() {
            return REQBYPHONE;
        }

        public void setREQBYPHONE(String REQBYPHONE) {
            this.REQBYPHONE = REQBYPHONE;
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

        public String getPRNUM() {
            return PRNUM;
        }

        public void setPRNUM(String PRNUM) {
            this.PRNUM = PRNUM;
        }

        public String getPM() {
            return PM;
        }

        public void setPM(String PM) {
            this.PM = PM;
        }

        public String getUDASSIGNER() {
            return UDASSIGNER;
        }

        public void setUDASSIGNER(String UDASSIGNER) {
            this.UDASSIGNER = UDASSIGNER;
        }

        public String getREQBYPERSON() {
            return REQBYPERSON;
        }

        public void setREQBYPERSON(String REQBYPERSON) {
            this.REQBYPERSON = REQBYPERSON;
        }

        public String getPROJECTID() {
            return PROJECTID;
        }

        public void setPROJECTID(String PROJECTID) {
            this.PROJECTID = PROJECTID;
        }

        public String getPROJECTDESC() {
            return PROJECTDESC;
        }

        public void setPROJECTDESC(String PROJECTDESC) {
            this.PROJECTDESC = PROJECTDESC;
        }

        public String getUDREMARK6() {
            return UDREMARK6;
        }

        public void setUDREMARK6(String UDREMARK6) {
            this.UDREMARK6 = UDREMARK6;
        }

        public String getUDREMARK4() {
            return UDREMARK4;
        }

        public void setUDREMARK4(String UDREMARK4) {
            this.UDREMARK4 = UDREMARK4;
        }

        public String getUDREMARK2() {
            return UDREMARK2;
        }

        public void setUDREMARK2(String UDREMARK2) {
            this.UDREMARK2 = UDREMARK2;
        }

        public String getUDREMARK3() {
            return UDREMARK3;
        }

        public void setUDREMARK3(String UDREMARK3) {
            this.UDREMARK3 = UDREMARK3;
        }

        public String getCUTYPE() {
            return CUTYPE;
        }

        public void setCUTYPE(String CUTYPE) {
            this.CUTYPE = CUTYPE;
        }

        public String getSTATUSDESC() {
            return STATUSDESC;
        }

        public void setSTATUSDESC(String STATUSDESC) {
            this.STATUSDESC = STATUSDESC;
        }

        public String getTOTALCOST() {
            return TOTALCOST;
        }

        public void setTOTALCOST(String TOTALCOST) {
            this.TOTALCOST = TOTALCOST;
        }

        public String getRDCHEADNAME() {
            return RDCHEADNAME;
        }

        public void setRDCHEADNAME(String RDCHEADNAME) {
            this.RDCHEADNAME = RDCHEADNAME;
        }

        public String getUDREMARK1() {
            return UDREMARK1;
        }

        public void setUDREMARK1(String UDREMARK1) {
            this.UDREMARK1 = UDREMARK1;
        }

        public String getISSUEDATE() {
            return ISSUEDATE;
        }

        public void setISSUEDATE(String ISSUEDATE) {
            this.ISSUEDATE = ISSUEDATE;
        }

        public String getPCOWNER() {
            return PCOWNER;
        }

        public void setPCOWNER(String PCOWNER) {
            this.PCOWNER = PCOWNER;
        }

        public String getUDREMARK() {
            return UDREMARK;
        }

        public void setUDREMARK(String UDREMARK) {
            this.UDREMARK = UDREMARK;
        }

        public String getUDPAYTHISYEAR() {
            return UDPAYTHISYEAR;
        }

        public void setUDPAYTHISYEAR(String UDPAYTHISYEAR) {
            this.UDPAYTHISYEAR = UDPAYTHISYEAR;
        }

        public String getOWNERPERSON() {
            return OWNERPERSON;
        }

        public void setOWNERPERSON(String OWNERPERSON) {
            this.OWNERPERSON = OWNERPERSON;
        }

        public String getCUSAMPLETYPE() {
            return CUSAMPLETYPE;
        }

        public void setCUSAMPLETYPE(String CUSAMPLETYPE) {
            this.CUSAMPLETYPE = CUSAMPLETYPE;
        }

        public String getNEWVENDOR() {
            return NEWVENDOR;
        }

        public void setNEWVENDOR(String NEWVENDOR) {
            this.NEWVENDOR = NEWVENDOR;
        }

        public String getCONTACT() {
            return CONTACT;
        }

        public void setCONTACT(String CONTACT) {
            this.CONTACT = CONTACT;
        }

        public String getPHONENUM() {
            return PHONENUM;
        }

        public void setPHONENUM(String PHONENUM) {
            this.PHONENUM = PHONENUM;
        }

        public String getWONUM() {
            return WONUM;
        }

        public void setWONUM(String WONUM) {
            this.WONUM = WONUM;
        }

        public String getVENDOR() {
            return VENDOR;
        }

        public void setVENDOR(String VENDOR) {
            this.VENDOR = VENDOR;
        }

        public String getVENDORNAME() {
            return VENDORNAME;
        }

        public void setVENDORNAME(String VENDORNAME) {
            this.VENDORNAME = VENDORNAME;
        }

        public String getRDCHEADPERSON() {
            return RDCHEADPERSON;
        }

        public void setRDCHEADPERSON(String RDCHEADPERSON) {
            this.RDCHEADPERSON = RDCHEADPERSON;
        }

        public String getUDDATE3() {
            return UDDATE3;
        }

        public void setUDDATE3(String UDDATE3) {
            this.UDDATE3 = UDDATE3;
        }
    }


}
