package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 付款验收-验收明细
 */

public class R_PAYCHECKLINE {
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
        private List<PAYCHECKLINE> resultlist; //记录

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

        public List<PAYCHECKLINE> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<PAYCHECKLINE> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     * 付款验收
     **/
    public static class PAYCHECKLINE extends Entity {

        private String ORDERUNIT; //计量单位
        private String LINENUM; //行
        private String DESCRIPTION2;//名称
        private String MODELNUM2;//型号
        private String QUANTITY;//数量
        private String UNITCOST2;//不含税单价
        private String LINECOST2;//不含税总价
        private String TAX;//数额
        private String LINECOST;//含税总价
        private String UNITCOST;//含税单价

        public String getORDERUNIT() {
            return ORDERUNIT;
        }

        public void setORDERUNIT(String ORDERUNIT) {
            this.ORDERUNIT = ORDERUNIT;
        }

        public String getLINENUM() {
            return LINENUM;
        }

        public void setLINENUM(String LINENUM) {
            this.LINENUM = LINENUM;
        }

        public String getDESCRIPTION2() {
            return DESCRIPTION2;
        }

        public void setDESCRIPTION2(String DESCRIPTION2) {
            this.DESCRIPTION2 = DESCRIPTION2;
        }

        public String getMODELNUM2() {
            return MODELNUM2;
        }

        public void setMODELNUM2(String MODELNUM2) {
            this.MODELNUM2 = MODELNUM2;
        }

        public String getQUANTITY() {
            return QUANTITY;
        }

        public void setQUANTITY(String QUANTITY) {
            this.QUANTITY = QUANTITY;
        }

        public String getUNITCOST2() {
            return UNITCOST2;
        }

        public void setUNITCOST2(String UNITCOST2) {
            this.UNITCOST2 = UNITCOST2;
        }

        public String getLINECOST2() {
            return LINECOST2;
        }

        public void setLINECOST2(String LINECOST2) {
            this.LINECOST2 = LINECOST2;
        }

        public String getTAX() {
            return TAX;
        }

        public void setTAX(String TAX) {
            this.TAX = TAX;
        }

        public String getLINECOST() {
            return LINECOST;
        }

        public void setLINECOST(String LINECOST) {
            this.LINECOST = LINECOST;
        }

        public String getUNITCOST() {
            return UNITCOST;
        }

        public void setUNITCOST(String UNITCOST) {
            this.UNITCOST = UNITCOST;
        }
    }


}
