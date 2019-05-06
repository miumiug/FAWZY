package com.hqxh.fiamproperty.api;

import com.hqxh.fiamproperty.constant.GlobalConfig;

/**
 * Created by apple on 17/7/27.
 * 查询条件
 */

public class HttpManager {
    /**
     * 待办任务*
     */
    public static String getWFASSIGNMENTUrl(String search, String username, String assignstatus, int curpage, int showcount) {
        if (null == search || search.equals("")) {
            return "{'appid':'" + GlobalConfig.WFADMIN_APPID + "','objectname':'" + GlobalConfig.WFASSIGNMENT_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ASSIGNSTATUS':'=" + assignstatus + "','ASSIGNCODE':'=" + username + "'}}";

        } else {
            return "{'appid':'" + GlobalConfig.WFADMIN_APPID + "','objectname':'" + GlobalConfig.WFASSIGNMENT_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ASSIGNSTATUS':'=" + assignstatus + "','ASSIGNCODE':'=" + username + "'},'reporsearch':{'UDAPPNAME':'" + search + "','DESCRIPTION':'" + search + "'}}";

        }

    }

    /**
     * 国内出差*
     */
    public static String getWORKORDERUrl(String appid, String objectname, String wonum, String username, int curpage, int showcount) {
        if (wonum.isEmpty()) {
            return "{'appid':'" + appid + "','objectname':'" + objectname + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";

        } else {
            return "{'appid':'" + appid + "','objectname':'" + objectname + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WONUM':'=" + wonum + "'}}";

        }

    }

    /**
     * 国内出差根据id*
     */
    public static String getWORKORDERbyIDUrl(String appid, String objectname, String id, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + objectname + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WORKORDERID':'=" + id + "'}}";
    }

    /**
     * 根据
     * 条件进行搜索
     */
    public static String getSearchWORKORDERUrl(String appid, String objectname, String search, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + objectname + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','reporsearch':{'WONUM':'" + search + "','DESCRIPTION':'" + search + "'}}";


    }

    /*费用号检验*/
    public static String checkProjectId(String username, int curpage, int showcount, String fcyear, String PROJECTID) {
        return "{'appid':'" + GlobalConfig.FINCNTRL_NAME + "','objectname':'" + GlobalConfig.FINCNTRL_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'fcyear':'" + fcyear + "', 'PROJECTID':'" + PROJECTID + "'}}";
    }

    /**
     * 出差人*
     */
    public static String getR_PERSONRELATIONUrl(String appid, String username, String wonum, int curpage, int showcount) {
        if (appid.equals(GlobalConfig.TRAVEL_APPID) || appid.equals(GlobalConfig.TRAVELS_APPID)) {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PERSONRELATION_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WONUM':'=" + wonum + "'}}";

        } else if (appid.equals(GlobalConfig.EXPENSES_APPID)) {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PERSONRELATION_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'EXPENSENUM':'=" + wonum + "'}}";

        }
        return null;

    }

    public static String getR_PERSONRELATIONByIDUrl(String appid, String username, String id, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PERSONRELATION_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PERSONRELATIONID':'=" + id + "'}}";
    }


    /**
     * 出国立项申请-出国人员知识积累拟交付资料清单*
     */
    public static String getKBFILELISTUrl(String appid, String wonum, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.KBFILELIST_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WONUM':'=" + wonum + "'}}";


    }

    /**
     * 项目费用管理-任务单出差申请采购申请的预算明细
     **/
    public static String getFCTASKRELATIONURL(String wonum, String wonum1, String username, int curpage, int showcount) {
        return "{'appid':'" + GlobalConfig.FINCNTRL_APPID + "','objectname':'" + GlobalConfig.FCTASKRELATION_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'" + wonum + "':'=" + wonum1 + "'}}";


    }

    /**
     * 项目费用管理-任务单出差申请采购申请的预算明细
     **/
    public static String getFCTASKRELATIONSELECTURL(String username, int curpage, int showcount) {
        return "{'appid':'" + GlobalConfig.FINCNTRL_APPID + "','objectname':'" + GlobalConfig.FCTASK_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
    }

    /**
     * 项目费用管理-任务单出差申请采购申请的预算明细
     **/
    public static String getFCTASKRELATIONSELECTSAVEURL(String id, String content, String username, int curpage, int showcount) {
        return "{'appid':'" + GlobalConfig.TRAVEL_APPID + "','objectname':'" + GlobalConfig.WORKORDER_NAME + "','option':'sync','rec':{'"+ GlobalConfig.WORKORDER_NAME +"':{'WORKORDERID':'" + id +"'},'"+GlobalConfig.FCTASKRELATION_NAME+"':[" + content +"]},'username':'" + username + "'}";
    }

    /**
     * 保存行程明细单*
     */
    public static String savePersonrelation(String username, String id, String content) {
//        return "{'appid':'" + GlobalConfig.TRAVEL_APPID + "','objectname':'" + GlobalConfig.PERSONRELATION_NAME + "','option':'sync','rec':{'"+ GlobalConfig.PERSONRELATION_NAME +"':" + content +"},'username':'" + username + "'}";
        return "{'appid':'" + GlobalConfig.TRAVEL_APPID + "','objectname':'" + GlobalConfig.WORKORDER_NAME + "','option':'sync','rec':{'"+ GlobalConfig.WORKORDER_NAME +"':{'WORKORDERID':'" + id +"'},'"+GlobalConfig.PERSONRELATION_NAME+"':[" + content +"]},'username':'" + username + "'}";
    }

    /**
     * 查询主任务单*
     */
    public static String getMainTask(String appid, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.WORKORDER_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
    }

    /**
     * 根据
     * 条件进行搜索
     */
    public static String getSearchTSUrl(String appid, String objectname, String search, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + objectname + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','reporsearch':{'WONUM':'" + search + "','DESCRIPTION':'" + search + "'}}";
    }

    /**
     * 保存国内出差申请单*
     */
    public static String saveWorkorder(String username, String content) {
        return "{'appid':'" + GlobalConfig.TRAVEL_APPID + "','objectname':'" + GlobalConfig.WORKORDER_NAME + "','option':'sync','rec':{'"+ GlobalConfig.WORKORDER_NAME +"':" + content +"},'username':'" + username + "'}";
    }


    /*
    物资明细/整车明细
    */
    public static String getGRLINEUrl(String appid, String username, String grnum, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.GRLINE_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'GRNUM':'=" + grnum + "'}}";

    }
/*
    整车明细
*/

    public static String getZCMXUrl(String username, String grnum, int curpage, int showcount) {
        return "{'appid':'" + GlobalConfig.GRZC_APPID + "','objectname':'" + GlobalConfig.GRLINE_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'GRNUM':'=" + grnum + "'}}";

    }


    /**
     * 出门管理*
     */
    public static String getGRUrl(String appid, String objctname, String grnum, String username, int curpage, int showcount) {
        if (grnum.isEmpty()) {
            return "{'appid':'" + appid + "','objectname':'" + objctname + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
        } else {
            return "{'appid':'" + appid + "','objectname':'" + objctname + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'GRNUM':'=" + grnum + "'}}";

        }
    }

    /**
     * 出门管理-搜索*
     */
    public static String getSearchGRUrl(String appid, String objctname, String search, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + objctname + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','reporsearch':{'GRNUM':'" + search + "','DESCRIPTION':'" + search + "'}}";
    }

    /**
     * 采购申请*
     */
    public static String getPRUrl(String appid, String prnum, String username, int curpage, int showcount) {
        if (prnum.isEmpty()) {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PR_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";

        } else {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PR_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PRNUM':'=" + prnum + "'}}";
        }
    }

    /**
     * 采购申请-搜索*
     */
    public static String getSearchPRUrl(String appid, String search, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PR_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','reporsearch':{'PRNUM':'" + search + "','DESCRIPTION':'" + search + "'}}";

    }


    /**
     * 潜在供应商*
     */
    public static String getPRVENDORUrl(String appid, String prnum, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PRVENDOR_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PRNUM':'=" + prnum + "'}}";

    }

    /**
     * 申请明细*
     */
    public static String getPRLINEUrl(String appid, String prnum, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PRLINE_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PRNUM':'=" + prnum + "'}}";

    }

    /**
     * 任务单*
     */
    public static String getRWDUrl(String appid, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.WORKORDER_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";

    }


    /**
     * 任务单-搜索*
     */
    public static String getSearchRWDUrl(String appid, String search, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.WORKORDER_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','reporsearch':{'WONUM':'" + search + "','DESCRIPTION':'" + search + "'}}";

    }


    /**
     * 明细信息*
     */
    public static String getWPITEMUrl(String appid, String wonum, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.WPITEM_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WONUM':'=" + wonum + "'}}";

    }

    /**
     * 合同*
     */
    public static String getPURCHVIEWUrl(String appid, String contractnum, String username, int curpage, int showcount) {
        if (contractnum.isEmpty()) {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PURCHVIEW_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";

        } else {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PURCHVIEW_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'CONTRACTNUM':'=" + contractnum + "'}}";

        }

    }

    /**
     * 合同-搜索*
     */
    public static String getSearchPURCHVIEWUrl(String appid, String search, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PURCHVIEW_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','reporsearch':{'CONTRACTNUM':'" + search + "','DESCRIPTION':'" + search + "'}}";


    }

    /**
     * 付款计划*
     */
    public static String getCONTRACTPAYURL(String username, String contractnum, String contractrev, int curpage, int showcount) {
        return "{'appid':'" + GlobalConfig.CONTPURCH_APPID + "','objectname':'" + GlobalConfig.CONTRACTPAY_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'CONTRACTNUM':'=" + contractnum + "','CONTRACTREV':'=" + contractrev + "'}}";

    }

    /**
     * 付款验收明细*
     */
    public static String getPAYCHECKLINEUrl(String appid, String paychecknum, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PAYCHECKLINE_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PAYCHECKNUM':'=" + paychecknum + "'}}";

    }

    /**
     * 合同行*
     */
    public static String getCONTRACTLINEURL(String username, String contractnum, String contractrev, int curpage, int showcount) {
        return "{'appid':'" + GlobalConfig.CONTPURCH_APPID + "','objectname':'" + GlobalConfig.CONTRACTLINE_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'CONTRACTNUM':'=" + contractnum + "','REVISIONNUM':'=" + contractrev + "'}}";

    }


    /**
     * 付款验收*
     */
    public static String getPAYCHECKUrl(String appid, String paychecknum, String username, int curpage, int showcount) {
        if (paychecknum.isEmpty()) {
            return "{'appid':'" + GlobalConfig.PAYCHECK_APPID + "','objectname':'" + GlobalConfig.PAYCHECK_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";

        } else {
            return "{'appid':'" + GlobalConfig.PAYCHECK_APPID + "','objectname':'" + GlobalConfig.PAYCHECK_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PAYCHECKNUM':'=" + paychecknum + "'}}";

        }

    }


    /**
     * 付款验收-搜索*
     */
    public static String getSearchPAYCHECKUrl(String appid, String search, String username, int curpage, int showcount) {

        return "{'appid':'" + GlobalConfig.PAYCHECK_APPID + "','objectname':'" + GlobalConfig.PAYCHECK_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','reporsearch':{'PAYCHECKNUM':'" + search + "','DESCRIPTION':'" + search + "'}}";


    }

    /**
     * 需款计划*
     */
    public static String getPAYPLANUrl(String appid, String payplannum, String username, int curpage, int showcount) {
        if (payplannum.isEmpty()) {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PAYPLAN_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";

        } else {
            if (appid.equals(GlobalConfig.PPCHANGE_APPID)) {  //子需款计划
                return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PAYPLAN_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PARENT':'=" + payplannum + "'}}";

            }
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PAYPLAN_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PAYPLANNUM':'=" + payplannum + "'}}";

        }

    }


    /**
     * 需款计划-搜索*
     */
    public static String getSearchPAYPLANUrl(String appid, String search, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PAYPLAN_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','reporsearch':{'PAYPLANNUM':'" + search + "','DESCRIPTION':'" + search + "'}}";


    }


    /**
     * 需款项目
     */
    public static String gePAYPLANPROJECTUrl(String appid, String payplannum, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PAYPLANPROJECT_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PAYPLANNUM':'=" + payplannum + "'}}";

    }

    /**
     * 差旅报销单／备用金报销*
     */
    public static String getEXPENSEUrl(String appid, String expensenum, String username, int curpage, int showcount) {
        if (expensenum.isEmpty()) {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.EXPENSE_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";

        } else {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.EXPENSE_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'EXPENSENUM':'=" + expensenum + "'}}";

        }

    }

    /**
     * 差旅报销单／备用金报销 -搜索*
     */
    public static String getSearchEXPENSEUrl(String appid, String search, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.EXPENSE_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','reporsearch':{'EXPENSENUM':'" + search + "','DESCRIPTION':'" + search + "'}}";


    }


    /**
     * 补助明细*
     */
    public static String getSUBSIDIESUrl(String appid, String username, String expensenum, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.SUBSIDIES_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'EXPENSENUM':'=" + expensenum + "'}}";

    }

    /**
     * 外出试验－补助明细*
     */
    public static String getOuttestineUrl(String appid, String username, String expensenum, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.OUTTESTINE_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'EXPENSENUM':'=" + expensenum + "'}}";

    }

    /**
     * 补助明细/交通明细*
     */
    public static String getCLMXUrl(String appid, String objectname, String username, String expensenum, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + objectname + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'EXPENSENUM':'=" + expensenum + "'}}";

    }

    /**
     * 借款单*
     */
    public static String getBoUrl(String appid, String bonum, String username, int curpage, int showcount) {
        if (bonum.isEmpty()) {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.BO_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";

        } else {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.BO_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'BONUM':'=" + bonum + "'}}";

        }

    }

    /**
     * 借款单-搜索*
     */
    public static String getSearchBoUrl(String appid, String search, String username, int curpage, int showcount) {
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.BO_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','reporsearch':{'BONUM':'" + search + "','DESCRIPTION':'" + search + "'}}";


    }

    /**
     * 审批记录*
     */
    public static String getWFTRANSACTIONUrl(String username, String ownertable, String ownerid, int curpage, int showcount) {
        return "{'appid':'" + GlobalConfig.WFADMIN_APPID + "','objectname':'" + GlobalConfig.WFTRANSACTION_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'OWNERTABLE':'=" + ownertable + "','OWNERID':'=" + ownerid + "'}}";

    }

    /**
     * 执行部门 /科室*
     * GlobalConfig.USER_APPID 部门
     * GlobalConfig.ROLE_APPID 科室
     */
    public static String getCUDEPTUrl(String appid, String deptnum, String username, int curpage, int showcount) {
        if (appid.equals(GlobalConfig.USER_APPID)) {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.CUDEPT_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
        } else if (appid.equals(GlobalConfig.ROLE_APPID)) {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.CUDEPT_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PARENT':'=" + deptnum + "'}}";

        }
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.CUDEPT_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
    }


    /**
     * 执行部门 /科室*
     * GlobalConfig.USER_APPID 部门
     * GlobalConfig.ROLE_APPID 科室
     */
    public static String getCUDEPTSearchUrl(String appid, String deptnum, String search, String username, int curpage, int showcount) {
        if (appid.equals(GlobalConfig.USER_APPID)) {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.CUDEPT_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','reporsearch':{'DEPTNUM':'" + search + "','DESCRIPTION':'" + search + "'}}";
        } else if (appid.equals(GlobalConfig.ROLE_APPID)) {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.CUDEPT_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PARENT':'=" + deptnum + "'},'reporsearch':{'DEPTNUM':'" + search + "','DESCRIPTION':'" + search + "'}}";

        }
        return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.CUDEPT_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','reporsearch':{'DEPTNUM':'" + search + "','DESCRIPTION':'" + search + "'}}";
    }


    /**
     * 附件合同*
     */
    public static String getDOCLINKSUrl(String username, String ownertable, String ownerid, int curpage, int showcount) {
        return "{'appid':'" + GlobalConfig.USER_APPID + "','objectname':'" + GlobalConfig.DOCLINKS_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'OWNERTABLE':'=" + ownertable + "','OWNERID':'=" + ownerid + "'}}";

    }


    /**
     * 执行人
     *
     * @param cudept    部门
     * @param cucrew    科室
     * @param username  用户名
     * @param curpage   当前页
     * @param showcount 显示条数
     */
    public static String getPERSONUrl(String appid, String cudept, String cucrew, String username, int curpage, int showcount) {
        if (null == cudept && null == cucrew) {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PERSON_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";

        } else {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PERSON_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'CUDEPT':'=" + cudept + "','CUCREW':'=" + cucrew + "'}}";
        }
    }


    /**
     * 执行人-搜索
     */
    public static String getPERSONSearchUrl(String appid, String cudept, String cucrew, String search, String username, int curpage, int showcount) {
        if (null == cudept && null == cucrew) {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PERSON_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','reporsearch':{'PERSONID':'" + search + "','DISPLAYNAME':'" + search + "'}}";

        } else {
            return "{'appid':'" + appid + "','objectname':'" + GlobalConfig.PERSON_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'CUDEPT':'=" + cudept + "','CUCREW':'=" + cucrew + "'},'reporsearch':{'PERSONID':'" + search + "','DISPLAYNAME':'" + search + "'}}";
        }
    }
    /*
    项目写实-我的写实
*/

    public static String getMyProject(String username,String dailylogdate, int curpage, int showcount) {
        return "{\"appid\":\"" + GlobalConfig.WRTRACK_APPID + "\",\"objectname\":\"" + GlobalConfig.WORKREPORT_NAME + "\",\"username\":\"" + username + "\",'curpage':" + curpage + ",\"showcount\":" + showcount + ",\"option\":\"read\",\"freesearch\":\"CREATEBY='" + username + "' and to_char(reportdate,'yyyymmdd') = '"+ dailylogdate + "'\"}";

    }

    public static String getMyProject_month(String username,String dailylogdate, int curpage, int showcount) {
        return "{\"appid\":\"" + GlobalConfig.WRTRACK_APPID + "\",\"objectname\":\"" + GlobalConfig.WORKREPORT_NAME + "\",\"username\":\"" + username + "\",'curpage':" + curpage + ",\"showcount\":" + showcount + ",\"option\":\"read\",\"freesearch\":\"CREATEBY='" + username + "'and to_char(reportdate,'yyyymm') = '"+ dailylogdate + "'\"}";

    }

      /*
    项目写实-我的写实xiangqing
*/

    public static String getMyProjectDetail(String username, String wrnum, int curpage, int showcount) {
        return "{'appid':'" + GlobalConfig.WRTRACK_APPID + "','objectname':'" + GlobalConfig.WORKREPORTLINE_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WRNUM':'=" + wrnum + "'}}";

    }

       /*
    项目写实-附件
*/

    public static String getDocu(String username, String wrnum, int curpage, int showcount) {
        return "{'appid':'" + GlobalConfig.WRTRACK_APPID + "','objectname':'" + GlobalConfig.DOCLINKS_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'OWNERID':'=" + wrnum + "','OWNERTABLE':'WORKREPORT'}}";

    }

          /*
    项目写实-待确认写实
*/

    public static String getWait(String username,String dailylogdate, int curpage, int showcount) {
        String select = "workreportid in (select ownerid from wfassignment where ownertable='WORKREPORT' and assignstatus='ACTIVE' and assigncode='" + username + "') and to_char(reportdate,'yyyymmdd') = '" + dailylogdate + "'";
        return "{\"appid\":\"" + GlobalConfig.WRTRACK_APPID + "\",\"objectname\":\"" + GlobalConfig.WORKREPORT_NAME + "\",\"username\":\"" + username + "\",\"curpage\":" + curpage + ",\"showcount\":" + showcount + ",\"option\":\"read\",\"freesearch\":\"" + select + "\"}";

    }
 /*
    项目写实-待确认写实
    */

    public static String getWait_month(String username,String dailylogdate, int curpage, int showcount) {
        String select = "workreportid in (select ownerid from wfassignment where ownertable='WORKREPORT' and assignstatus='ACTIVE' and assigncode='" + username + "') and to_char(reportdate,'yyyymm') = '" + dailylogdate + "'";
        return "{\"appid\":\"" + GlobalConfig.WRTRACK_APPID + "\",\"objectname\":\"" + GlobalConfig.WORKREPORT_NAME + "\",\"username\":\"" + username + "\",\"curpage\":" + curpage + ",\"showcount\":" + showcount + ",\"option\":\"read\",\"freesearch\":\"" + select + "\"}";

    }

/*
    项目写实-已确认写实
    */

    public static String getCofirm(String username,String dailylogdate, int curpage, int showcount) {
        String select = "workreportid in (select ownerid from wftransaction where ownertable='WORKREPORT' and personid='" + username + "' and transtype='WFASSIGNCOMP') and to_char(reportdate,'yyyymmdd') = '" + dailylogdate + "'";
        return "{\"appid\":\"" + GlobalConfig.WRTRACK_APPID + "\",\"objectname\":\"" + GlobalConfig.WORKREPORT_NAME + "\",\"username\":\"" + username + "\",\"curpage\":" + curpage + ",\"showcount\":" + showcount + ",\"option\":\"read\",\"freesearch\":\"" + select + "\"}";

    }

  /*
    项目写实-已确认写实
    */

    public static String getCofirm_month(String username,String dailylogdate, int curpage, int showcount) {
        String select = "workreportid in (select ownerid from wftransaction where ownertable='WORKREPORT' and personid='" + username + "' and transtype='WFASSIGNCOMP') and to_char(reportdate,'yyyymm') = '" + dailylogdate + "'";
        return "{\"appid\":\"" + GlobalConfig.WRTRACK_APPID + "\",\"objectname\":\"" + GlobalConfig.WORKREPORT_NAME + "\",\"username\":\"" + username + "\",\"curpage\":" + curpage + ",\"showcount\":" + showcount + ",\"option\":\"read\",\"freesearch\":\"" + select + "\"}";

    }
}
