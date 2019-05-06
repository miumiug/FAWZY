package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 差旅报销单-补助明细
 */

public class R_SUBSIDIES {
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
        private List<SUBSIDIES> resultlist; //记录

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

        public List<SUBSIDIES> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<SUBSIDIES> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     * 差旅报销单
     **/
    public static class SUBSIDIES extends Entity {

        private String LINENUM; //行
        private String DAYS; //人天
        private String STANDARDCOST;//补助标准
        private String LINECOST;//补助金额

        public String getLINENUM() {
            return LINENUM;
        }

        public void setLINENUM(String LINENUM) {
            this.LINENUM = LINENUM;
        }

        public String getDAYS() {
            return DAYS;
        }

        public void setDAYS(String DAYS) {
            this.DAYS = DAYS;
        }

        public String getSTANDARDCOST() {
            return STANDARDCOST;
        }

        public void setSTANDARDCOST(String STANDARDCOST) {
            this.STANDARDCOST = STANDARDCOST;
        }

        public String getLINECOST() {
            return LINECOST;
        }

        public void setLINECOST(String LINECOST) {
            this.LINECOST = LINECOST;
        }
    }


}
