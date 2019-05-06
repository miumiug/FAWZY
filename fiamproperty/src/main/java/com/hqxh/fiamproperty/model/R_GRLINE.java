package com.hqxh.fiamproperty.model;

import java.util.List;

/**
 物资明细，整车明细.
 */

public class R_GRLINE {
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
        private List<GRLINE> resultlist; //记录

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

        public List<GRLINE> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<GRLINE> resultlist) {
            this.resultlist = resultlist;
        }
    }
   /*
   物资明细
   */
  public static class GRLINE{

       private  String  DESCRIPTION;//名称
       private String GRLIN1;//规格类型
       private String QTY;//数量
       private String MEASUREUNIT;//计量单位


       public String getDESCRIPTION() {
           return DESCRIPTION;
       }

       public void setDESCRIPTION(String DESCRIPTION) {
           this.DESCRIPTION = DESCRIPTION;
       }

       public String getGRLIN1() {
           return GRLIN1;
       }

       public void setGRLIN1(String GRLIN1) {
           this.GRLIN1 = GRLIN1;
       }

       public String getQTY() {
           return QTY;
       }

       public void setQTY(String QTY) {
           this.QTY = QTY;
       }

       public String getMEASUREUNIT() {
           return MEASUREUNIT;
       }

       public void setMEASUREUNIT(String MEASUREUNIT) {
           this.MEASUREUNIT = MEASUREUNIT;
       }

     /*
      整车明细
      */

      // private String DESCRIPTION;//样车编号
      // private String GRLIN1;//型号
       private String GRLIN3;//品牌
       private String GRLIN4;//车辆类型


       public String getGRLIN3() {
           return GRLIN3;
       }

       public void setGRLIN3(String GRLIN3) {
           this.GRLIN3 = GRLIN3;
       }

       public String getGRLIN4() {
           return GRLIN4;
       }

       public void setGRLIN4(String GRLIN4) {
           this.GRLIN4 = GRLIN4;
       }
   }
}
