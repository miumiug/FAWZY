package hqxh.worklogproperty.model;

import java.util.List;

/**
 * 工作日志
 */

public class R_WORKDAILYLOG {
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
        private List<WORKDAILYLOG> resultlist; //记录

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

        public List<WORKDAILYLOG> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<WORKDAILYLOG> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     * 工作日志
     **/
    public static class WORKDAILYLOG extends Entity {

        private String DAILYTYPE; //类型
        private String DESCRIPTION;//工作内容
        private String SUPERVISOR;//创建人
        private String STATUS;//状态
        private String STATUSDATE;//确认时间
        private String CONFIRMBY;//确认人
        private String MANHOUR;//工时
        private String PROJECTID;//项目号
        private String DAILYLOGDATE;//日志日期
        private String CONFIRMBYNAME;//确认人姓名

        public String getDAILYTYPE() {
            return DAILYTYPE;
        }

        public void setDAILYTYPE(String DAILYTYPE) {
            this.DAILYTYPE = DAILYTYPE;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getSUPERVISOR() {
            return SUPERVISOR;
        }

        public void setSUPERVISOR(String SUPERVISOR) {
            this.SUPERVISOR = SUPERVISOR;
        }

        public String getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }

        public String getSTATUSDATE() {
            return STATUSDATE;
        }

        public void setSTATUSDATE(String STATUSDATE) {
            this.STATUSDATE = STATUSDATE;
        }

        public String getCONFIRMBY() {
            return CONFIRMBY;
        }

        public void setCONFIRMBY(String CONFIRMBY) {
            this.CONFIRMBY = CONFIRMBY;
        }

        public String getMANHOUR() {
            return MANHOUR;
        }

        public void setMANHOUR(String MANHOUR) {
            this.MANHOUR = MANHOUR;
        }

        public String getPROJECTID() {
            return PROJECTID;
        }

        public void setPROJECTID(String PROJECTID) {
            this.PROJECTID = PROJECTID;
        }

        public String getDAILYLOGDATE() {
            return DAILYLOGDATE;
        }

        public void setDAILYLOGDATE(String DAILYLOGDATE) {
            this.DAILYLOGDATE = DAILYLOGDATE;
        }

        public String getCONFIRMBYNAME() {
            return CONFIRMBYNAME;
        }

        public void setCONFIRMBYNAME(String CONFIRMBYNAME) {
            this.CONFIRMBYNAME = CONFIRMBYNAME;
        }
    }


}
