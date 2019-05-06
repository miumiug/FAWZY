package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by Administrator on 2018\6\21 0021.
 */

public class R_WORKREPORT {
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
        private List<WORKREPORT> resultlist; //记录

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

        public List<WORKREPORT> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<WORKREPORT> resultlist) {
            this.resultlist = resultlist;
        }
    }

    public static class WORKREPORT extends Entity {
        private String ACTURALHOURS;
        private String APPBYPM;
        private String CREATEBY;
        private String CREATENAME;
        private String PROJECTDESC;
        private String PROJECTID;
        private String REPORTDATE;
        private String STATUSDESC;
        private String SUPERVISORDESC;
        private String WORKREPORTID;
        private String WRNUM;
        private String BRIEF;

        public boolean ischeck;//是否被选中

        public boolean ischeck() {
            return ischeck;
        }

        public void setIscheck(boolean ischeck) {
            this.ischeck = ischeck;
        }

        public String getBRIEF() {
            return BRIEF;
        }

        public void setBRIEF(String BRIEF) {
            this.BRIEF = BRIEF;
        }

        public String getACTURALHOURS() {
            return ACTURALHOURS;
        }

        public void setACTURALHOURS(String ACTURALHOURS) {
            this.ACTURALHOURS = ACTURALHOURS;
        }

        public String getAPPBYPM() {
            return APPBYPM;
        }

        public void setAPPBYPM(String APPBYPM) {
            this.APPBYPM = APPBYPM;
        }

        public String getCREATEBY() {
            return CREATEBY;
        }

        public void setCREATEBY(String CREATEBY) {
            this.CREATEBY = CREATEBY;
        }

        public String getCREATENAME() {
            return CREATENAME;
        }

        public void setCREATENAME(String CREATENAME) {
            this.CREATENAME = CREATENAME;
        }

        public String getPROJECTID() {
            return PROJECTID;
        }

        public void setPROJECTID(String PROJECTID) {
            this.PROJECTID = PROJECTID;
        }

        public String getPROJECTDESC() {
            return PROJECTDESC;
        }

        public void setPROJECTDESC(String PROJECTDESC) {
            this.PROJECTDESC = PROJECTDESC;
        }

        public String getREPORTDATE() {
            return REPORTDATE;
        }

        public void setREPORTDATE(String REPORTDATE) {
            this.REPORTDATE = REPORTDATE;
        }

        public String getSTATUSDESC() {
            return STATUSDESC;
        }

        public void setSTATUSDESC(String STATUSDESC) {
            this.STATUSDESC = STATUSDESC;
        }

        public String getSUPERVISORDESC() {
            return SUPERVISORDESC;
        }

        public void setSUPERVISORDESC(String SUPERVISORDESC) {
            this.SUPERVISORDESC = SUPERVISORDESC;
        }

        public String getWORKREPORTID() {
            return WORKREPORTID;
        }

        public void setWORKREPORTID(String WORKREPORTID) {
            this.WORKREPORTID = WORKREPORTID;
        }

        public String getWRNUM() {
            return WRNUM;
        }

        public void setWRNUM(String WRNUM) {
            this.WRNUM = WRNUM;
        }
    }
}
