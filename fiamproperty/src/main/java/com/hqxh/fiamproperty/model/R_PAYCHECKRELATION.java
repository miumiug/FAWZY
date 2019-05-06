package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 付款验收
 */

public class R_PAYCHECKRELATION {
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
        private List<PAYCHECKRELATION> resultlist; //记录

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

        public List<PAYCHECKRELATION> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<PAYCHECKRELATION> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     *付款验收
     * **/
    public static class  PAYCHECKRELATION{

        private String  PAYCHECKNUM; //验收单
        private String  PAYCHECKDESC;//描述
        private String  PROJECTID;//费用号
        private String  TOTALCOST;//含税金额
        private String  TAX;//税额

        public String getPAYCHECKNUM() {
            return PAYCHECKNUM;
        }

        public void setPAYCHECKNUM(String PAYCHECKNUM) {
            this.PAYCHECKNUM = PAYCHECKNUM;
        }

        public String getPAYCHECKDESC() {
            return PAYCHECKDESC;
        }

        public void setPAYCHECKDESC(String PAYCHECKDESC) {
            this.PAYCHECKDESC = PAYCHECKDESC;
        }

        public String getPROJECTID() {
            return PROJECTID;
        }

        public void setPROJECTID(String PROJECTID) {
            this.PROJECTID = PROJECTID;
        }

        public String getTOTALCOST() {
            return TOTALCOST;
        }

        public void setTOTALCOST(String TOTALCOST) {
            this.TOTALCOST = TOTALCOST;
        }

        public String getTAX() {
            return TAX;
        }

        public void setTAX(String TAX) {
            this.TAX = TAX;
        }
    }



}
