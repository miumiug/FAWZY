package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 差旅报销单-交通费明细
 */

public class R_EXPENSELINE {
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
        private List<EXPENSELINE> resultlist; //记录

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

        public List<EXPENSELINE> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<EXPENSELINE> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     * 交通补助明细 /报销明细
     **/
    public static class EXPENSELINE extends Entity {

        private String ACTSTART; //时间从
        private String ACTFINISH; //时间到
        private String ADDRESSBEGIN;//地点从
        private String ADDRESSEND;//地点到
        private String TRANSPORT;//交通工具
        private String LINECOST;//金额／含税金额

        private String INVOICEDATE;//发票日期
        private String DESCRIPTION;//单据名称
        private String REMARK;//报销事项说明
        private String TAX;//税额


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

        public String getADDRESSBEGIN() {
            return ADDRESSBEGIN;
        }

        public void setADDRESSBEGIN(String ADDRESSBEGIN) {
            this.ADDRESSBEGIN = ADDRESSBEGIN;
        }

        public String getADDRESSEND() {
            return ADDRESSEND;
        }

        public void setADDRESSEND(String ADDRESSEND) {
            this.ADDRESSEND = ADDRESSEND;
        }

        public String getTRANSPORT() {
            return TRANSPORT;
        }

        public void setTRANSPORT(String TRANSPORT) {
            this.TRANSPORT = TRANSPORT;
        }

        public String getLINECOST() {
            return LINECOST;
        }

        public void setLINECOST(String LINECOST) {
            this.LINECOST = LINECOST;
        }

        public String getINVOICEDATE() {
            return INVOICEDATE;
        }

        public void setINVOICEDATE(String INVOICEDATE) {
            this.INVOICEDATE = INVOICEDATE;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getREMARK() {
            return REMARK;
        }

        public void setREMARK(String REMARK) {
            this.REMARK = REMARK;
        }

        public String getTAX() {
            return TAX;
        }

        public void setTAX(String TAX) {
            this.TAX = TAX;
        }
    }


}
