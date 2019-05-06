package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 借款单
 */

public class R_KBFILELIST {
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
        private List<KBFILELIST> resultlist; //记录

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

        public List<KBFILELIST> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<KBFILELIST> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     * 需款计划
     **/
    public static class KBFILELIST extends Entity {

        private String FILETYPE; //资料类型
        private String DESCRIPTION; //描述
        private String LINENUM;//序号
        private String OWNER;//责任人


        public String getFILETYPE() {
            return FILETYPE;
        }

        public void setFILETYPE(String FILETYPE) {
            this.FILETYPE = FILETYPE;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getLINENUM() {
            return LINENUM;
        }

        public void setLINENUM(String LINENUM) {
            this.LINENUM = LINENUM;
        }

        public String getOWNER() {
            return OWNER;
        }

        public void setOWNER(String OWNER) {
            this.OWNER = OWNER;
        }
    }


}
