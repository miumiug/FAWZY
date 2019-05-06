package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 * 附件文档
 */

public class R_DOCLINKS {
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
        private List<DOCLINKS> resultlist; //记录

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

        public List<DOCLINKS> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<DOCLINKS> resultlist) {
            this.resultlist = resultlist;
        }
    }

    /*
    附件文档
    */
    public static class DOCLINKS extends Entity {

        private String DOCUMENT;//编号
        private String DESCRIPTION;//文档描述
        private String OWNER;//创建人
        private String CREATEDATE;//创建日期
        private String URLNAME;//文档地址

        public String getDOCUMENT() {
            return DOCUMENT;
        }

        public void setDOCUMENT(String DOCUMENT) {
            this.DOCUMENT = DOCUMENT;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getOWNER() {
            return OWNER;
        }

        public void setOWNER(String OWNER) {
            this.OWNER = OWNER;
        }

        public String getURLNAME() {
            return URLNAME;
        }

        public void setURLNAME(String URLNAME) {
            this.URLNAME = URLNAME;
        }

        public String getCREATEDATE() {
            return CREATEDATE;
        }

        public void setCREATEDATE(String CREATEDATE) {
            this.CREATEDATE = CREATEDATE;
        }
    }
}
