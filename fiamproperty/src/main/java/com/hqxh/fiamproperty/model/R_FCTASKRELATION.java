package com.hqxh.fiamproperty.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 项目费用管理-任务单出差申请采购申请的预算明细
 */

public class R_FCTASKRELATION {
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
        private List<FCTASKRELATION> resultlist; //记录

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

        public List<FCTASKRELATION> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<FCTASKRELATION> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     * 需款计划
     **/
    public static class FCTASKRELATION implements Cloneable, Serializable {

        private String TASKID; //任务
        private String DESCRIPTION; //描述
        private String TASKTYPEDESC;//类型
        private String FCYEAR;//年度

        public String getTASKID() {
            return TASKID;
        }

        public void setTASKID(String TASKID) {
            this.TASKID = TASKID;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getTASKTYPEDESC() {
            return TASKTYPEDESC;
        }

        public void setTASKTYPEDESC(String TASKTYPEDESC) {
            this.TASKTYPEDESC = TASKTYPEDESC;
        }

        public String getFCYEAR() {
            return FCYEAR;
        }

        public void setFCYEAR(String FCYEAR) {
            this.FCYEAR = FCYEAR;
        }
    }


}
