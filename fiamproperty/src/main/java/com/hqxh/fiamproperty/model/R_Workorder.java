package com.hqxh.fiamproperty.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 结果集
 */

public class R_Workorder {
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
        private List<Workorder> resultlist; //记录

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

        public List<Workorder> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<Workorder> resultlist) {
            this.resultlist = resultlist;
        }
    }

    /**
     * 国内出差申请
     * 任务单
     **/
    public static class Workorder implements Cloneable, Serializable {
        //国内出差申请
        private String UDTRV2A; //其它团组名称
        private String UDREMARK3;//出访国家或地区／试验项目、标准、方法及方案
        private String UDKSREMARK;//途经国家或地区
        private String RDCHEAD;//中心分管领导
        private String UDTRV2;//出国团组名称
        private String PHONENUM;//电话
        private String CUDEPT;//申请部门／执行部门
        private String CUCREW;//申请科室／执行科室
        private String FINCNTRLDESC;//项目名称
        private String UDCONTACT;//联系人及电话
        private String STATUS;//状态
        private String TEAMLEADER;//团长姓名
        private String UDREMARK1;//出国目的／试验目的／出差原因
        private String UDTARGSTARTDATE;//开始时间／建议完成时间
        private String UDTARGCOMPDATE;//结束时间／要求完成时间
        private String UDADDRESS1;//拟出访路线／目的地
        private String UDESTDUR3;//出访国家停留(天)
        private String UDQTY1;//途径国家容留(天)／样品数量/申请数量
        private String UDSUMMARY6;//预期成果
        private String UDTARGSTARTDATE2;//出国时间
        private String REPORTDATE;//申请日期／提单日期
        private String REPORTEDBY;//申请人
        private String UDREMARK2;//邀请接待单位／样品名称
        private String UDREMARK4;//出国工作任务／车辆及总成基本信息
        private String DESCRIPTION;//描述／名称
        private String WONUM;//申请单／任务单
        private String PROJECTID;//费用号
        private String UDESTTOTALCOST;//出差费用预算(人民币)／费用概算合计
        private String WORKTYPE;//类型
        private String UDTRANSPORT3;//乘坐飞机
        private String UDTRVCOST2;//其它费用
        private String UDTRVCOST1;//差旅费用
        private String UDTRVCOST3;//交通费
        private String UDHASMEETING;//已发差旅平台？
        private String PARENT; //父任务单
        private String PARENTDESC; //父任务单描述
        private String UDTRV1;//团队负责人编号
        private String RDCHEADNAME;//院分管领导姓名

        //试验任务单
        private String PROJECTDESC;//项目名称
        private String PM;//项目经理
        private String UDTOTYPE2;//试验类型
        private String SUPERVISOR;//子项目经理
        private String REPORTEDBYNAME;//提单人
        private String REPORTEDBYPHONE;//提单人电话
        private String UDTOQUESTION;//问题描述
        private String UDTODESC;//任务单描述
        private String STATUSDESC;//状态
        private String REPORTEDBYCUDEPT;//提单部门
        private String REPORTEDBYCUCREW;//提单科室
        private String OWNER;//执行人代码／子项目负责人代码
        private String OWNERNAME;//执行人姓名／子项目负责人姓名
        private String ACTFINISH;//任务完成时间
        //试制任务单
        private String UDPAYTHISYEAR;//本年度是否结算
        private String UDTOTYPE3;//试制类型
        private String CUQUANTITY;//试制数量
        private String UDSZDATE1;//任务开始时间
        private String UDMEETINGNUM;//会议编号
        private String UDMEETINGNAME;//会议名称
        //物资领料单
        private String STORELOC;//库房
        private String ESTMATCOST;//总费用
        private String SCHEDFINISH;//结算完成时间
        // 调件任务单
        private String UDADDITIONAL;//是否补录

        //燃油申请单
        private String UDTOOIL1;//燃油类型及标号
        private String CUUNIT;//单位
        //其它任务单
        private String UDTOTYPE1;//类型
        private String WORKORDERID;//唯一ID
        private String DEPTNUM;//部门编码
        private String HISTORYFLAG;

        public String getHISTORYFLAG() {
            return HISTORYFLAG;
        }

        public void setHISTORYFLAG(String HISTORYFLAG) {
            this.HISTORYFLAG = HISTORYFLAG;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public String getUDTRV1() {
            return UDTRV1;
        }

        public void setUDTRV1(String UDTRV1) {
            this.UDTRV1 = UDTRV1;
        }

        public String getRDCHEADNAME() {
            return RDCHEADNAME;
        }

        public void setRDCHEADNAME(String RDCHEADNAME) {
            this.RDCHEADNAME = RDCHEADNAME;
        }

        public String getPARENT() {
            return PARENT;
        }

        public void setPARENT(String PARENT) {
            this.PARENT = PARENT;
        }

        public String getPARENTDESC() {
            return PARENTDESC;
        }

        public void setPARENTDESC(String PARENTDESC) {
            this.PARENTDESC = PARENTDESC;
        }

        public String getUDTRVCOST3() {
            return UDTRVCOST3;
        }

        public void setUDTRVCOST3(String UDTRVCOST3) {
            this.UDTRVCOST3 = UDTRVCOST3;
        }

        public String getUDHASMEETING() {
            return UDHASMEETING;
        }

        public void setUDHASMEETING(String UDHASMEETING) {
            this.UDHASMEETING = UDHASMEETING;
        }

        public String getDEPTNUM() {
            return DEPTNUM;
        }

        public void setDEPTNUM(String DEPTNUM) {
            this.DEPTNUM = DEPTNUM;
        }

        public String getUDTRV2A() {
            return UDTRV2A;
        }

        public void setUDTRV2A(String UDTRV2A) {
            this.UDTRV2A = UDTRV2A;
        }

        public String getUDREMARK3() {
            return UDREMARK3;
        }

        public void setUDREMARK3(String UDREMARK3) {
            this.UDREMARK3 = UDREMARK3;
        }

        public String getUDKSREMARK() {
            return UDKSREMARK;
        }

        public void setUDKSREMARK(String UDKSREMARK) {
            this.UDKSREMARK = UDKSREMARK;
        }

        public String getRDCHEAD() {
            return RDCHEAD;
        }

        public void setRDCHEAD(String RDCHEAD) {
            this.RDCHEAD = RDCHEAD;
        }

        public String getUDTRV2() {
            return UDTRV2;
        }

        public void setUDTRV2(String UDTRV2) {
            this.UDTRV2 = UDTRV2;
        }

        public String getPHONENUM() {
            return PHONENUM;
        }

        public void setPHONENUM(String PHONENUM) {
            this.PHONENUM = PHONENUM;
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

        public String getFINCNTRLDESC() {
            return FINCNTRLDESC;
        }

        public void setFINCNTRLDESC(String FINCNTRLDESC) {
            this.FINCNTRLDESC = FINCNTRLDESC;
        }

        public String getUDCONTACT() {
            return UDCONTACT;
        }

        public void setUDCONTACT(String UDCONTACT) {
            this.UDCONTACT = UDCONTACT;
        }

        public String getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }

        public String getTEAMLEADER() {
            return TEAMLEADER;
        }

        public void setTEAMLEADER(String TEAMLEADER) {
            this.TEAMLEADER = TEAMLEADER;
        }

        public String getUDREMARK1() {
            return UDREMARK1;
        }

        public void setUDREMARK1(String UDREMARK1) {
            this.UDREMARK1 = UDREMARK1;
        }

        public String getUDTARGSTARTDATE() {
            return UDTARGSTARTDATE;
        }

        public void setUDTARGSTARTDATE(String UDTARGSTARTDATE) {
            this.UDTARGSTARTDATE = UDTARGSTARTDATE;
        }

        public String getUDTARGCOMPDATE() {
            return UDTARGCOMPDATE;
        }

        public void setUDTARGCOMPDATE(String UDTARGCOMPDATE) {
            this.UDTARGCOMPDATE = UDTARGCOMPDATE;
        }

        public String getUDADDRESS1() {
            return UDADDRESS1;
        }

        public void setUDADDRESS1(String UDADDRESS1) {
            this.UDADDRESS1 = UDADDRESS1;
        }

        public String getUDESTDUR3() {
            return UDESTDUR3;
        }

        public void setUDESTDUR3(String UDESTDUR3) {
            this.UDESTDUR3 = UDESTDUR3;
        }

        public String getUDQTY1() {
            return UDQTY1;
        }

        public void setUDQTY1(String UDQTY1) {
            this.UDQTY1 = UDQTY1;
        }

        public String getUDSUMMARY6() {
            return UDSUMMARY6;
        }

        public void setUDSUMMARY6(String UDSUMMARY6) {
            this.UDSUMMARY6 = UDSUMMARY6;
        }

        public String getUDTARGSTARTDATE2() {
            return UDTARGSTARTDATE2;
        }

        public void setUDTARGSTARTDATE2(String UDTARGSTARTDATE2) {
            this.UDTARGSTARTDATE2 = UDTARGSTARTDATE2;
        }

        public String getREPORTDATE() {
            return REPORTDATE;
        }

        public void setREPORTDATE(String REPORTDATE) {
            this.REPORTDATE = REPORTDATE;
        }

        public String getREPORTEDBY() {
            return REPORTEDBY;
        }

        public void setREPORTEDBY(String REPORTEDBY) {
            this.REPORTEDBY = REPORTEDBY;
        }

        public String getUDREMARK2() {
            return UDREMARK2;
        }

        public void setUDREMARK2(String UDREMARK2) {
            this.UDREMARK2 = UDREMARK2;
        }

        public String getUDREMARK4() {
            return UDREMARK4;
        }

        public void setUDREMARK4(String UDREMARK4) {
            this.UDREMARK4 = UDREMARK4;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getWONUM() {
            return WONUM;
        }

        public void setWONUM(String WONUM) {
            this.WONUM = WONUM;
        }

        public String getPROJECTID() {
            return PROJECTID;
        }

        public void setPROJECTID(String PROJECTID) {
            this.PROJECTID = PROJECTID;
        }

        public String getUDESTTOTALCOST() {
            return UDESTTOTALCOST;
        }

        public void setUDESTTOTALCOST(String UDESTTOTALCOST) {
            this.UDESTTOTALCOST = UDESTTOTALCOST;
        }

        public String getPROJECTDESC() {
            return PROJECTDESC;
        }

        public void setPROJECTDESC(String PROJECTDESC) {
            this.PROJECTDESC = PROJECTDESC;
        }

        public String getPM() {
            return PM;
        }

        public void setPM(String PM) {
            this.PM = PM;
        }

        public String getUDTOTYPE2() {
            return UDTOTYPE2;
        }

        public void setUDTOTYPE2(String UDTOTYPE2) {
            this.UDTOTYPE2 = UDTOTYPE2;
        }

        public String getSUPERVISOR() {
            return SUPERVISOR;
        }

        public void setSUPERVISOR(String SUPERVISOR) {
            this.SUPERVISOR = SUPERVISOR;
        }

        public String getREPORTEDBYNAME() {
            return REPORTEDBYNAME;
        }

        public void setREPORTEDBYNAME(String REPORTEDBYNAME) {
            this.REPORTEDBYNAME = REPORTEDBYNAME;
        }

        public String getREPORTEDBYPHONE() {
            return REPORTEDBYPHONE;
        }

        public void setREPORTEDBYPHONE(String REPORTEDBYPHONE) {
            this.REPORTEDBYPHONE = REPORTEDBYPHONE;
        }

        public String getUDTOQUESTION() {
            return UDTOQUESTION;
        }

        public void setUDTOQUESTION(String UDTOQUESTION) {
            this.UDTOQUESTION = UDTOQUESTION;
        }

        public String getUDTODESC() {
            return UDTODESC;
        }

        public void setUDTODESC(String UDTODESC) {
            this.UDTODESC = UDTODESC;
        }

        public String getSTATUSDESC() {
            return STATUSDESC;
        }

        public void setSTATUSDESC(String STATUSDESC) {
            this.STATUSDESC = STATUSDESC;
        }

        public String getREPORTEDBYCUDEPT() {
            return REPORTEDBYCUDEPT;
        }

        public void setREPORTEDBYCUDEPT(String REPORTEDBYCUDEPT) {
            this.REPORTEDBYCUDEPT = REPORTEDBYCUDEPT;
        }

        public String getREPORTEDBYCUCREW() {
            return REPORTEDBYCUCREW;
        }

        public void setREPORTEDBYCUCREW(String REPORTEDBYCUCREW) {
            this.REPORTEDBYCUCREW = REPORTEDBYCUCREW;
        }

        public String getOWNER() {
            return OWNER;
        }

        public void setOWNER(String OWNER) {
            this.OWNER = OWNER;
        }

        public String getOWNERNAME() {
            return OWNERNAME;
        }

        public void setOWNERNAME(String OWNERNAME) {
            this.OWNERNAME = OWNERNAME;
        }

        public String getUDPAYTHISYEAR() {
            return UDPAYTHISYEAR;
        }

        public void setUDPAYTHISYEAR(String UDPAYTHISYEAR) {
            this.UDPAYTHISYEAR = UDPAYTHISYEAR;
        }

        public String getUDTOTYPE3() {
            return UDTOTYPE3;
        }

        public void setUDTOTYPE3(String UDTOTYPE3) {
            this.UDTOTYPE3 = UDTOTYPE3;
        }

        public String getCUQUANTITY() {
            return CUQUANTITY;
        }

        public void setCUQUANTITY(String CUQUANTITY) {
            this.CUQUANTITY = CUQUANTITY;
        }

        public String getSTORELOC() {
            return STORELOC;
        }

        public void setSTORELOC(String STORELOC) {
            this.STORELOC = STORELOC;
        }

        public String getUDTOOIL1() {
            return UDTOOIL1;
        }

        public void setUDTOOIL1(String UDTOOIL1) {
            this.UDTOOIL1 = UDTOOIL1;
        }

        public String getCUUNIT() {
            return CUUNIT;
        }

        public void setCUUNIT(String CUUNIT) {
            this.CUUNIT = CUUNIT;
        }

        public String getUDTOTYPE1() {
            return UDTOTYPE1;
        }

        public void setUDTOTYPE1(String UDTOTYPE1) {
            this.UDTOTYPE1 = UDTOTYPE1;
        }

        public String getWORKTYPE() {
            return WORKTYPE;
        }

        public void setWORKTYPE(String WORKTYPE) {
            this.WORKTYPE = WORKTYPE;
        }

        public String getUDTRANSPORT3() {
            return UDTRANSPORT3;
        }

        public void setUDTRANSPORT3(String UDTRANSPORT3) {
            this.UDTRANSPORT3 = UDTRANSPORT3;
        }

        public String getUDTRVCOST2() {
            return UDTRVCOST2;
        }

        public void setUDTRVCOST2(String UDTRVCOST2) {
            this.UDTRVCOST2 = UDTRVCOST2;
        }

        public String getUDTRVCOST1() {
            return UDTRVCOST1;
        }

        public void setUDTRVCOST1(String UDTRVCOST1) {
            this.UDTRVCOST1 = UDTRVCOST1;
        }

        public String getWORKORDERID() {
            return WORKORDERID;
        }

        public void setWORKORDERID(String WORKORDERID) {
            this.WORKORDERID = WORKORDERID;
        }

        public String getACTFINISH() {
            return ACTFINISH;
        }

        public void setACTFINISH(String ACTFINISH) {
            this.ACTFINISH = ACTFINISH;
        }

        public String getUDSZDATE1() {
            return UDSZDATE1;
        }

        public void setUDSZDATE1(String UDSZDATE1) {
            this.UDSZDATE1 = UDSZDATE1;
        }

        public String getESTMATCOST() {
            return ESTMATCOST;
        }

        public void setESTMATCOST(String ESTMATCOST) {
            this.ESTMATCOST = ESTMATCOST;
        }

        public String getSCHEDFINISH() {
            return SCHEDFINISH;
        }

        public void setSCHEDFINISH(String SCHEDFINISH) {
            this.SCHEDFINISH = SCHEDFINISH;
        }

        public String getUDADDITIONAL() {
            return UDADDITIONAL;
        }

        public void setUDADDITIONAL(String UDADDITIONAL) {
            this.UDADDITIONAL = UDADDITIONAL;
        }

        public String getUDMEETINGNUM() {
            return UDMEETINGNUM;
        }

        public void setUDMEETINGNUM(String UDMEETINGNUM) {
            this.UDMEETINGNUM = UDMEETINGNUM;
        }

        public String getUDMEETINGNAME() {
            return UDMEETINGNAME;
        }

        public void setUDMEETINGNAME(String UDMEETINGNAME) {
            this.UDMEETINGNAME = UDMEETINGNAME;
        }
    }


}
