package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 * 试制采购申请-明细
 */

public class R_PRLINE {
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
        private List<PRLINE> resultlist; //记录

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

        public List<PRLINE> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<PRLINE> resultlist) {
            this.resultlist = resultlist;
        }
    }

    /*
    物资明细
    */
    public static class PRLINE extends Entity {

        private String PRLINEID;//PRLINEID
        private String LINECOST;//费用总计
        private String RLIN9;//推荐供应商
        private String ORDERQTY;//数量
        private String UNITCOST2;//含税单价
        private String LINECOST2;//含税总价
        private String RLIN7;//版本
        private String UNITCOST1;//附加费
        private String PRLINENUM;//行
        private String DESCRIPTION;//零部件名称
        private String UDMODEL;//图号
        private String UDASSIGNER;//执行人
        private String ASSIGNERPERSON;//执行人名称
        private String UNITCOST;//含税单价


        public String getUNITCOST() {
            return UNITCOST;
        }

        public void setUNITCOST(String UNITCOST) {
            this.UNITCOST = UNITCOST;
        }

        public String getLINECOST() {
            return LINECOST;
        }

        public void setLINECOST(String LINECOST) {
            this.LINECOST = LINECOST;
        }

        public String getRLIN9() {
            return RLIN9;
        }

        public void setRLIN9(String RLIN9) {
            this.RLIN9 = RLIN9;
        }

        public String getORDERQTY() {
            return ORDERQTY;
        }

        public void setORDERQTY(String ORDERQTY) {
            this.ORDERQTY = ORDERQTY;
        }

        public String getUNITCOST2() {
            return UNITCOST2;
        }

        public void setUNITCOST2(String UNITCOST2) {
            this.UNITCOST2 = UNITCOST2;
        }

        public String getLINECOST2() {
            return LINECOST2;
        }

        public void setLINECOST2(String LINECOST2) {
            this.LINECOST2 = LINECOST2;
        }

        public String getRLIN7() {
            return RLIN7;
        }

        public void setRLIN7(String RLIN7) {
            this.RLIN7 = RLIN7;
        }

        public String getUNITCOST1() {
            return UNITCOST1;
        }

        public void setUNITCOST1(String UNITCOST1) {
            this.UNITCOST1 = UNITCOST1;
        }

        public String getPRLINENUM() {
            return PRLINENUM;
        }

        public void setPRLINENUM(String PRLINENUM) {
            this.PRLINENUM = PRLINENUM;
        }

        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }

        public String getUDMODEL() {
            return UDMODEL;
        }

        public void setUDMODEL(String UDMODEL) {
            this.UDMODEL = UDMODEL;
        }

        public String getPRLINEID() {
            return PRLINEID;
        }

        public void setPRLINEID(String PRLINEID) {
            this.PRLINEID = PRLINEID;
        }

        public String getUDASSIGNER() {
            return UDASSIGNER;
        }

        public void setUDASSIGNER(String UDASSIGNER) {
            this.UDASSIGNER = UDASSIGNER;
        }

        public String getASSIGNERPERSON() {
            return ASSIGNERPERSON;
        }

        public void setASSIGNERPERSON(String ASSIGNERPERSON) {
            this.ASSIGNERPERSON = ASSIGNERPERSON;
        }
    }
}
