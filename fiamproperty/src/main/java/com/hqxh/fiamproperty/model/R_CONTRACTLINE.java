package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 合同行
 */

public class R_CONTRACTLINE {
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
        private List<CONTRACTLINE> resultlist; //记录

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

        public List<CONTRACTLINE> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<CONTRACTLINE> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     * 合同行
     **/
    public static class CONTRACTLINE extends Entity {

        private String CONTRACTLINE; //行
        private String DESCRIPTION; //内容
        private String MODELNUM;//规格型号
        private String ORDERQTY;//数量
        private String ORDERUNIT;//计量单位
        private String UNITCOST;//单价
        private String LINECOST;//总价
        private String PROJECTID;//费用号

        public String getCONTRACTLINE() {
            return CONTRACTLINE;
        }

        public void setCONTRACTLINE(String CONTRACTLINE) {
            this.CONTRACTLINE = CONTRACTLINE;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getMODELNUM() {
            return MODELNUM;
        }

        public void setMODELNUM(String MODELNUM) {
            this.MODELNUM = MODELNUM;
        }

        public String getORDERQTY() {
            return ORDERQTY;
        }

        public void setORDERQTY(String ORDERQTY) {
            this.ORDERQTY = ORDERQTY;
        }

        public String getORDERUNIT() {
            return ORDERUNIT;
        }

        public void setORDERUNIT(String ORDERUNIT) {
            this.ORDERUNIT = ORDERUNIT;
        }

        public String getUNITCOST() {
            return UNITCOST;
        }

        public void setUNITCOST(String UNITCOST) {
            this.UNITCOST = UNITCOST;
        }

        public String getLINECOST() {
            return LINECOST;
        }

        public void setLINECOST(String LINECOST) {
            this.LINECOST = LINECOST;
        }

        public String getPROJECTID() {
            return PROJECTID;
        }

        public void setPROJECTID(String PROJECTID) {
            this.PROJECTID = PROJECTID;
        }
    }


}
