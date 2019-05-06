package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by Administrator on 2018\6\21 0021.
 */

public class R_WORKREPORTLINE {
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
        private List<WORKREPORTLINE> resultlist; //记录

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

        public List<WORKREPORTLINE> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<WORKREPORTLINE> resultlist) {
            this.resultlist = resultlist;
        }
    }
    public static class WORKREPORTLINE extends Entity {
        private String ACTIVITY1DESC;
        private String ACTIVITY2DESC;
        private String ACTURALHOURS;
        private String ACTURALHOURS2;
        private String ACTURALHOURS3;
        private String ACTURALHOURS4;
        private String ACTURALHOURS5;
        private String COMMENTS;
        private String COMMENTS2;
        private String COMMENTS3;
        private String COMMENTS4;
        private String COMMENTS5;
        private String PARTDESC;
        private String PARTNO;
        private String PLANENDDATE;
        private String PLANSTARTDATE;
        private String RESULTDESC;
        private String WL01;
        private String WORKREPORTLINEID;
        private String WRID;
        private String WRNUM;

        public String getACTIVITY1DESC() {
            return ACTIVITY1DESC;
        }

        public void setACTIVITY1DESC(String ACTIVITY1DESC) {
            this.ACTIVITY1DESC = ACTIVITY1DESC;
        }

        public String getACTIVITY2DESC() {
            return ACTIVITY2DESC;
        }

        public void setACTIVITY2DESC(String ACTIVITY2DESC) {
            this.ACTIVITY2DESC = ACTIVITY2DESC;
        }

        public String getACTURALHOURS2() {
            return ACTURALHOURS2;
        }

        public void setACTURALHOURS2(String ACTURALHOURS2) {
            this.ACTURALHOURS2 = ACTURALHOURS2;
        }

        public String getACTURALHOURS() {
            return ACTURALHOURS;
        }

        public void setACTURALHOURS(String ACTURALHOURS) {
            this.ACTURALHOURS = ACTURALHOURS;
        }

        public String getACTURALHOURS3() {
            return ACTURALHOURS3;
        }

        public void setACTURALHOURS3(String ACTURALHOURS3) {
            this.ACTURALHOURS3 = ACTURALHOURS3;
        }

        public String getACTURALHOURS4() {
            return ACTURALHOURS4;
        }

        public void setACTURALHOURS4(String ACTURALHOURS4) {
            this.ACTURALHOURS4 = ACTURALHOURS4;
        }

        public String getACTURALHOURS5() {
            return ACTURALHOURS5;
        }

        public void setACTURALHOURS5(String ACTURALHOURS5) {
            this.ACTURALHOURS5 = ACTURALHOURS5;
        }

        public String getCOMMENTS() {
            return COMMENTS;
        }

        public void setCOMMENTS(String COMMENTS) {
            this.COMMENTS = COMMENTS;
        }

        public String getCOMMENTS2() {
            return COMMENTS2;
        }

        public void setCOMMENTS2(String COMMENTS2) {
            this.COMMENTS2 = COMMENTS2;
        }

        public String getCOMMENTS3() {
            return COMMENTS3;
        }

        public void setCOMMENTS3(String COMMENTS3) {
            this.COMMENTS3 = COMMENTS3;
        }

        public String getCOMMENTS4() {
            return COMMENTS4;
        }

        public void setCOMMENTS4(String COMMENTS4) {
            this.COMMENTS4 = COMMENTS4;
        }

        public String getCOMMENTS5() {
            return COMMENTS5;
        }

        public void setCOMMENTS5(String COMMENTS5) {
            this.COMMENTS5 = COMMENTS5;
        }

        public String getPARTDESC() {
            return PARTDESC;
        }

        public void setPARTDESC(String PARTDESC) {
            this.PARTDESC = PARTDESC;
        }

        public String getPARTNO() {
            return PARTNO;
        }

        public void setPARTNO(String PARTNO) {
            this.PARTNO = PARTNO;
        }

        public String getPLANENDDATE() {
            return PLANENDDATE;
        }

        public void setPLANENDDATE(String PLANENDDATE) {
            this.PLANENDDATE = PLANENDDATE;
        }

        public String getPLANSTARTDATE() {
            return PLANSTARTDATE;
        }

        public void setPLANSTARTDATE(String PLANSTARTDATE) {
            this.PLANSTARTDATE = PLANSTARTDATE;
        }

        public String getRESULTDESC() {
            return RESULTDESC;
        }

        public void setRESULTDESC(String RESULTDESC) {
            this.RESULTDESC = RESULTDESC;
        }

        public String getWL01() {
            return WL01;
        }

        public void setWL01(String WL01) {
            this.WL01 = WL01;
        }

        public String getWORKREPORTLINEID() {
            return WORKREPORTLINEID;
        }

        public void setWORKREPORTLINEID(String WORKREPORTLINEID) {
            this.WORKREPORTLINEID = WORKREPORTLINEID;
        }

        public String getWRID() {
            return WRID;
        }

        public void setWRID(String WRID) {
            this.WRID = WRID;
        }

        public String getWRNUM() {
            return WRNUM;
        }

        public void setWRNUM(String WRNUM) {
            this.WRNUM = WRNUM;
        }
    }
}
