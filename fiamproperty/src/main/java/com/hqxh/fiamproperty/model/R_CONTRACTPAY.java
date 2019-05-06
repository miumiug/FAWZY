package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 * 合同-付款计划
 */

public class R_CONTRACTPAY {
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
        private List<CONTRACTPAY> resultlist; //记录

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

        public List<CONTRACTPAY> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<CONTRACTPAY> resultlist) {
            this.resultlist = resultlist;
        }
    }

    /*
    付款计划
    */
    public static class CONTRACTPAY extends Entity {

        private String CONPAYLINENUM;//第几次
        private String TERM;//付款条件
        private String DUEDATE;//付款日期
        private String LINECOST;//付款金额(人民币)
        private String PAYMENTPERCENT;//付款比例
        private String ISADVANCE;//预付款

        public String getCONPAYLINENUM() {
            return CONPAYLINENUM;
        }

        public void setCONPAYLINENUM(String CONPAYLINENUM) {
            this.CONPAYLINENUM = CONPAYLINENUM;
        }

        public String getTERM() {
            return TERM;
        }

        public void setTERM(String TERM) {
            this.TERM = TERM;
        }

        public String getDUEDATE() {
            return DUEDATE;
        }

        public void setDUEDATE(String DUEDATE) {
            this.DUEDATE = DUEDATE;
        }

        public String getLINECOST() {
            return LINECOST;
        }

        public void setLINECOST(String LINECOST) {
            this.LINECOST = LINECOST;
        }

        public String getPAYMENTPERCENT() {
            return PAYMENTPERCENT;
        }

        public void setPAYMENTPERCENT(String PAYMENTPERCENT) {
            this.PAYMENTPERCENT = PAYMENTPERCENT;
        }

        public String getISADVANCE() {
            return ISADVANCE;
        }

        public void setISADVANCE(String ISADVANCE) {
            this.ISADVANCE = ISADVANCE;
        }
    }
}
