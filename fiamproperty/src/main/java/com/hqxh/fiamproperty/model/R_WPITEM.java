package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * Created by apple on 17/7/26.
 * 物资明细
 */

public class R_WPITEM {
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
        private List<WPITEM> resultlist; //记录

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

        public List<WPITEM> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<WPITEM> resultlist) {
            this.resultlist = resultlist;
        }
    }


    /**
     * 物资明细
     **/
    public static class WPITEM extends Entity {

        private String WPITEMID; //WPITEMID
        private String DESCRIPTION; //物资/零件名称
        private String WPT5;//规格型号/零件号
        private String ORDERUNIT;//计量单位
        private String ITEMQTY;//数量
        private String OWNER;//执行人代码
        private String OWNERNAME;//执行人姓名

        public String getWPITEMID() {
            return WPITEMID;
        }

        public void setWPITEMID(String WPITEMID) {
            this.WPITEMID = WPITEMID;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getWPT5() {
            return WPT5;
        }

        public void setWPT5(String WPT5) {
            this.WPT5 = WPT5;
        }

        public String getORDERUNIT() {
            return ORDERUNIT;
        }

        public void setORDERUNIT(String ORDERUNIT) {
            this.ORDERUNIT = ORDERUNIT;
        }

        public String getITEMQTY() {
            return ITEMQTY;
        }

        public void setITEMQTY(String ITEMQTY) {
            this.ITEMQTY = ITEMQTY;
        }

        public String getOWNER() {
            return OWNER;
        }

        public void setOWNER(String OWNER) {
            this.OWNER = OWNER;
        }

        public String getOWNERNAME() {
            return OWNERNAME;
        }

        public void setOWNERNAME(String OWNERNAME) {
            this.OWNERNAME = OWNERNAME;
        }
    }


}
