package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by wxs on 2019/3/19.
 */

public class FINCNTRLModel {
    private String errcode; //返回状态
    private String errmsg; //消息
    private String userid; //用户名
    private FINCNTRLModel.ResultBean result;

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

    public FINCNTRLModel.ResultBean getResult() {
        return result;
    }

    public void setResult(FINCNTRLModel.ResultBean result) {
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
        private List<FINCNTRL_Model> resultlist; //记录

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

        public List<FINCNTRL_Model> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<FINCNTRL_Model> resultlist) {
            this.resultlist = resultlist;
        }
    }

    public static class FINCNTRL_Model {
        /**
         * FCTYPE : PROJECT,notreadonly
         * FCYEAR : 2019,notreadonly
         * FINCNTRLUID : 60346,readonly
         * PROJECTID : Z1924,notreadonly
         * STATUS : APPR,notreadonly
         */

        private String FCTYPE;
        private String FCYEAR;
        private String FINCNTRLUID;
        private String PROJECTID;
        private String STATUS;

        public String getFCTYPE() {
            return FCTYPE;
        }

        public void setFCTYPE(String FCTYPE) {
            this.FCTYPE = FCTYPE;
        }

        public String getFCYEAR() {
            return FCYEAR;
        }

        public void setFCYEAR(String FCYEAR) {
            this.FCYEAR = FCYEAR;
        }

        public String getFINCNTRLUID() {
            return FINCNTRLUID;
        }

        public void setFINCNTRLUID(String FINCNTRLUID) {
            this.FINCNTRLUID = FINCNTRLUID;
        }

        public String getPROJECTID() {
            return PROJECTID;
        }

        public void setPROJECTID(String PROJECTID) {
            this.PROJECTID = PROJECTID;
        }

        public String getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }
    }
}
