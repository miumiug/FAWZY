package com.hqxh.fiamproperty.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 出门管理
 */

public class R_GR {
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
        private List<GR> resultlist; //记录

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

        public List<GR> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<GR> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**出门管理
     *
     * **/
    public static class  GR implements Serializable {
        private String  GRID; //GRID
        private String  ENTERBY; //申请人
        private String  ENTERDATE;//申请时间
        private String  GRNUM;//编号
        private String  DESCRIPTION;//描述
        private String  TYPE;//类型
        private String  PHONE;//电话
        private String  CUDEPT;//部门
        private String  CUCREW;//科室
        private String  STATUSDESC;//状态
        private String  LOCATIONDESCRIPTION;//门岗1
        private String  SCHEDULARDATE;//计划出门日期
        private String  LOCATION2DESCRIPTION;//门岗2
        private String  REASON;//理由

        public String getGRID() {
            return GRID;
        }

        public void setGRID(String GRID) {
            this.GRID = GRID;
        }

        public String getENTERBY() {
            return ENTERBY;
        }

        public void setENTERBY(String ENTERBY) {
            this.ENTERBY = ENTERBY;
        }

        public String getENTERDATE() {
            return ENTERDATE;
        }

        public void setENTERDATE(String ENTERDATE) {
            this.ENTERDATE = ENTERDATE;
        }

        public String getGRNUM() {
            return GRNUM;
        }

        public void setGRNUM(String GRNUM) {
            this.GRNUM = GRNUM;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public  String getTYPE() {
            return TYPE;
        }

        public void setTYPE(String TYPE) {
            this.TYPE = TYPE;
        }

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }

        public String getCUDEPT() {
            return CUDEPT;
        }

        public void setCUDEPT(String CUDEPT) {
            this.CUDEPT = CUDEPT;
        }

        public String getCUCREW() {
            return CUCREW;
        }

        public void setCUCREW(String CUCREW) {
            this.CUCREW = CUCREW;
        }

        public String getSTATUSDESC() {
            return STATUSDESC;
        }

        public void setSTATUSDESC(String STATUSDESC) {
            this.STATUSDESC = STATUSDESC;
        }

        public String getLOCATIONDESCRIPTION() {
            return LOCATIONDESCRIPTION;
        }

        public void setLOCATIONDESCRIPTION(String LOCATIONDESCRIPTION) {
            this.LOCATIONDESCRIPTION = LOCATIONDESCRIPTION;
        }

        public String getSCHEDULARDATE() {
            return SCHEDULARDATE;
        }

        public void setSCHEDULARDATE(String SCHEDULARDATE) {
            this.SCHEDULARDATE = SCHEDULARDATE;
        }

        public String getLOCATION2DESCRIPTION() {
            return LOCATION2DESCRIPTION;
        }

        public void setLOCATION2DESCRIPTION(String LOCATION2DESCRIPTION) {
            this.LOCATION2DESCRIPTION = LOCATION2DESCRIPTION;
        }

        public String getREASON() {
            return REASON;
        }

        public void setREASON(String REASON) {
            this.REASON = REASON;
        }



    }


}
