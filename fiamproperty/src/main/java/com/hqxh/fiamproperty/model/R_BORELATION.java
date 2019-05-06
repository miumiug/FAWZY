package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 差旅报销单-借款单
 */

public class R_BORELATION {
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
        private List<BORELATION> resultlist; //记录

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

        public List<BORELATION> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<BORELATION> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     * 差旅报销单-借款单
     **/
    public static class BORELATION extends Entity {

        private String BOENTERDATE; //借款时间
        private String BONUM;//借款单
        private String BODESC;//借款事由
        private String BOTOTALCOST;//借款金额

        public String getBOENTERDATE() {
            return BOENTERDATE;
        }

        public void setBOENTERDATE(String BOENTERDATE) {
            this.BOENTERDATE = BOENTERDATE;
        }

        public String getBONUM() {
            return BONUM;
        }

        public void setBONUM(String BONUM) {
            this.BONUM = BONUM;
        }

        public String getBODESC() {
            return BODESC;
        }

        public void setBODESC(String BODESC) {
            this.BODESC = BODESC;
        }

        public String getBOTOTALCOST() {
            return BOTOTALCOST;
        }

        public void setBOTOTALCOST(String BOTOTALCOST) {
            this.BOTOTALCOST = BOTOTALCOST;
        }
    }


}
