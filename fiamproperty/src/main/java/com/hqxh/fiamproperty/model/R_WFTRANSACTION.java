package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 审批记录
 */

public class R_WFTRANSACTION {
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
        private List<WFTRANSACTION> resultlist; //记录

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

        public List<WFTRANSACTION> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<WFTRANSACTION> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     *审批记录
     * **/
    public static class  WFTRANSACTION {

        private String DISPLAYNAME; //审批人
        private String TRANSDATE;//审批日期
        private String MEMO;//意见
        private String NODE;//标题

        public String getDISPLAYNAME() {
            return DISPLAYNAME;
        }

        public void setDISPLAYNAME(String DISPLAYNAME) {
            this.DISPLAYNAME = DISPLAYNAME;
        }

        public String getTRANSDATE() {
            return TRANSDATE;
        }

        public void setTRANSDATE(String TRANSDATE) {
            this.TRANSDATE = TRANSDATE;
        }

        public String getMEMO() {
            return MEMO;
        }

        public void setMEMO(String MEMO) {
            this.MEMO = MEMO;
        }

        public String getNODE() {
            return NODE;
        }

        public void setNODE(String NODE) {
            this.NODE = NODE;
        }
    }


}
