package hqxh.worklogproperty.api;


import hqxh.worklogproperty.constant.GlobalConfig;

/**
 * Created by apple on 17/7/27.
 * 查询条件
 */

public class HttpManager {
    /**
     * 我的日志
     */
    public static String getWORKDAILYUrl(String username, String dailylogdate, int curpage, int showcount) {
        return "{'appid':'" + GlobalConfig.WORKDAILY_APPID + "','objectname':'" + GlobalConfig.WORKDAILYLOG_OBJCTNAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'SUPERVISOR':'=" + username + "','DAILYLOGDATE':'=" + dailylogdate + "'}}";
    }

    /**
     * 人员选择
     **/
    public static String getPERSONUrl(String search, String username, int curpage, int showcount) {
        if (search.equals("")) {
            return "{'appid':'" + GlobalConfig.USER_APPID + "','objectname':'" + GlobalConfig.PERSON_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";

        } else {
            return "{'appid':'" + GlobalConfig.USER_APPID + "','objectname':'" + GlobalConfig.PERSON_NAME + "','username':'" + username + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','reporsearch':{'PERSONID':'" + search + "','DISPLAYNAME':'" + search + "'}}";

        }
    }
}
