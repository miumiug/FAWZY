package hqxh.worklogproperty.model;

/**
 * Created by apple on 17/7/26.
 * 结果集
 */

public class LOG_PERSONS {
    private String errcode; //返回状态
    private String errmsg; //消息
    private String userid; //用户名
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 结果集
     * DEFSITE
     * DISPLAYNAME
     * EMAILADDRESS
     * MYAPPS
     * PERSONID
     **/
    public static class PERSION {
        private String DEFSITE;
        private String DISPLAYNAME;//别名
        private String EMAILADDRESS;//Email地址
        private String MYAPPS;//用户能够看见的权限
        private String PERSONID;//用户ID
        private String CUDEPT;//部门
        private String CUCREW;//科室


        public String getDEFSITE() {
            return DEFSITE;
        }

        public void setDEFSITE(String DEFSITE) {
            this.DEFSITE = DEFSITE;
        }

        public String getDISPLAYNAME() {
            return DISPLAYNAME;
        }

        public void setDISPLAYNAME(String DISPLAYNAME) {
            this.DISPLAYNAME = DISPLAYNAME;
        }

        public String getEMAILADDRESS() {
            return EMAILADDRESS;
        }

        public void setEMAILADDRESS(String EMAILADDRESS) {
            this.EMAILADDRESS = EMAILADDRESS;
        }

        public String getMYAPPS() {
            return MYAPPS;
        }

        public void setMYAPPS(String MYAPPS) {
            this.MYAPPS = MYAPPS;
        }

        public String getPERSONID() {
            return PERSONID;
        }

        public void setPERSONID(String PERSONID) {
            this.PERSONID = PERSONID;
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
    }


}
