package hqxh.worklogproperty.constant;

/**
 * 全局配置
 */

public final class GlobalConfig {

    /**
     * 服务器地址
     **/
   // public final static String HTTP_URL = "http://qmportal1.qm.cn:8090"; //外网测试
//    public final static String HTTP_URL = "http://mobile.faw.com.cn:8091"; //外网正式
    public final static String HTTP_URL = "http://mobile.faw.com.cn:8089"; //修改后的地址
//    public final static String HTTP_URL = "http://10.60.12.98:9080";//内网


    /**
     * 服务器登录接口地址
     **/

    public final static String HTTP_URL_LOGIN = HTTP_URL + "/maximo/mobile/system/portallogin";

    /**
     * 服务器通用查询接口地址
     **/

    public final static String HTTP_URL_SEARCH = HTTP_URL + "/maximo/mobile/common/api";

    //工作日志APPID
    public final static String WORKDAILY_APPID = "WORKDAILY";

    //工作日志objectname
    public final static String WORKDAILYLOG_OBJCTNAME = "WORKDAILYLOG";

    //人员选择
    public final static String USER_APPID = "USER";
    //人员选择-表名
    public final static String PERSON_NAME = "PERSON";


    //人员返回码
    public final static int PERSON_REQUESTCODE = 1000; //人员请求码

    public final static int PERSON_RESULTCODE = 1001; //人员返回码
}
