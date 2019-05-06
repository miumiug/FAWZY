package com.hqxh.fiamproperty.unit;

import android.text.TextUtils;
import android.util.Log;

import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_PRLINE.PRLINE;
import com.hqxh.fiamproperty.model.R_WPITEM.WPITEM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by apple on 17/7/26.
 * json操作类
 */

public class JsonUnit {

    private static final String TAG = "JsonUnit";

    //使用String的split 方法
    public static String[] convertStrToArray(String str) {
        String[] strArray = null;
        if (null == str) {
            strArray = new String[]{""};
        } else {
            strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        }
        return strArray;
    }


    /**
     * 转换日期格式
     **/
    public static String strToDateString(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String str = null;
        try {
            date = format.parse(strDate);
            str = format.format(date);
        } catch (ParseException e) {
            return null;
        }

        return str;
    }


    /**
     * 封装试验任务单的数据
     *
     * @param workorderid    唯一列
     * @param cudept         部门
     * @param udtargcompdate 要求完成日期
     * @param owner          执行人
     * @param cucrew         执行科室
     * @param userid         用户Id
     **/
    public static String workorderData(String workorderid, String cudept, String udtargcompdate, String owner, String cucrew, String udpaythisyear, String userid, String appid) {
        JSONObject jsdata = new JSONObject();  //第一层
        JSONObject jsrec = new JSONObject();   //第二层
        JSONObject jswo = new JSONObject();    //第三层

        try {
            jswo.put("WORKORDERID", workorderid);//唯一列
            if (null != cudept) {
                jswo.put("CUDEPT", cudept);
            }

            jswo.put("UDTARGCOMPDATE", udtargcompdate);
            jswo.put("OWNER", owner);
            if (null != cucrew) {
                jswo.put("CUCREW", cucrew);
            }
            if (!udpaythisyear.equals("")) {
                jswo.put("UDPAYTHISYEAR", udpaythisyear);
            }
            jsrec.put("WORKORDER", jswo.toString());
            jsdata.put("appid", appid);
            jsdata.put("objectname", GlobalConfig.WORKORDER_NAME);
            jsdata.put("username", userid);
            jsdata.put("option", "sync");
            jsdata.put("rec", jsrec.toString());

            return jsdata.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * 调件任务单
     **/
    public static String djworkorderData(String workorderid, String udtargcompdate, List<WPITEM> wpitems, String userid, String appid) {
        JSONObject jsdata = new JSONObject();  //第一层
        JSONObject jsrec = new JSONObject();   //第二层
        JSONObject jswo = new JSONObject();    //第三层

        try {
            jswo.put("WORKORDERID", workorderid);//唯一列
            jswo.put("UDTARGCOMPDATE", udtargcompdate);
            jsrec.put("WORKORDER", jswo.toString());

            //任务单明细
            JSONArray jaline = new JSONArray();
            for (int i = 0; i < wpitems.size(); i++) {
                JSONObject jsline = new JSONObject();
                WPITEM wpitem = wpitems.get(i);
                jsline.put("WPITEMID", JsonUnit.convertStrToArray(wpitem.getWPITEMID())[0]);
                jsline.put("OWNER", JsonUnit.convertStrToArray(wpitem.getOWNER())[0]);//执行人
                jaline.put(jsline);
            }
            jsrec.put("WPITEM", jaline.toString());

            //最外层
            jsdata.put("appid", appid);
            jsdata.put("objectname", GlobalConfig.WORKORDER_NAME);
            jsdata.put("username", userid);
            jsdata.put("option", "sync");
            jsdata.put("rec", jsrec.toString());

            return jsdata.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * 合同
     **/
    public static String djpurchviewData(String contractid, String signdate, String startdate, String enddate, String udcontractnum, String userid, String appid) {
        JSONObject jsdata = new JSONObject();  //第一层
        JSONObject jsrec = new JSONObject();   //第二层
        JSONObject jswo = new JSONObject();    //第三层

        try {
            jswo.put("CONTRACTID", contractid);//唯一列
            jswo.put("SIGNDATE", signdate);//开始日期
            jswo.put("STARTDATE", startdate);//签订日期
            jswo.put("UDCONTRACTNUM", udcontractnum);//国外合同编号
            jswo.put("ENDDATE", enddate);//结束日期



            jsrec.put("PURCHVIEW", jswo.toString());

            //最外层
            jsdata.put("appid", appid);
            jsdata.put("objectname", GlobalConfig.PURCHVIEW_NAME);
            jsdata.put("username", userid);
            jsdata.put("option", "sync");
            jsdata.put("rec", jsrec.toString());

            return jsdata.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 付款验收
     **/
    public static String PaycheckData(String paycheckid, String checkdate, String userid, String appid) {
        JSONObject jsdata = new JSONObject();  //第一层
        JSONObject jsrec = new JSONObject();   //第二层
        JSONObject jswo = new JSONObject();    //第三层

        try {
            jswo.put("PAYCHECKID", paycheckid);//唯一列
            jswo.put("CHECKDATE", checkdate);


            jsrec.put("PAYCHECK", jswo.toString());

            //最外层
            jsdata.put("appid", appid);
            jsdata.put("objectname", GlobalConfig.PAYCHECK_NAME);
            jsdata.put("username", userid);
            jsdata.put("option", "sync");
            jsdata.put("rec", jsrec.toString());

            return jsdata.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * 技术采购申请/试制采购申请/服务采购申请
     **/
    public static String JsPrData(String prid, String rdchead, String udassigner, String userid, String appid) {
        JSONObject jsdata = new JSONObject();  //第一层
        JSONObject jsrec = new JSONObject();   //第二层
        JSONObject jswo = new JSONObject();    //第三层

        try {
            jswo.put("PRID", prid);//唯一列
            jswo.put("RDCHEAD", rdchead); //中心分管领导
            if (null != udassigner) {
                jswo.put("UDASSIGNER", udassigner); //执行人代码
            }


            jsrec.put("PR", jswo.toString());

            //最外层
            jsdata.put("appid", appid);
            jsdata.put("objectname", GlobalConfig.PR_NAME);
            jsdata.put("username", userid);
            jsdata.put("option", "sync");
            jsdata.put("rec", jsrec.toString());

            return jsdata.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * 物资采购申请
     **/
    public static String WzPrData(String prid, String rdchead, String uddate2, List<PRLINE> prlines, String userid, String appid) {
        JSONObject jsdata = new JSONObject();  //第一层
        JSONObject jsrec = new JSONObject();   //第二层
        JSONObject jswo = new JSONObject();    //第三层

        try {
            jswo.put("PRID", prid);//唯一列
            jswo.put("RDCHEAD", rdchead); //中心分管领导
            jswo.put("UDDATE2", uddate2); //执行人代码


            jsrec.put("PR", jswo.toString());
            //采购申请明细
            JSONArray jaline = new JSONArray();
            if (null != prlines) {
                for (int i = 0; i < prlines.size(); i++) {
                    JSONObject jsline = new JSONObject();
                    PRLINE prline = prlines.get(i);
                    jsline.put("PRLINEID", JsonUnit.convertStrToArray(prline.getPRLINEID())[0]);
                    jsline.put("UDASSIGNER", JsonUnit.convertStrToArray(prline.getUDASSIGNER())[0]);//执行人
                    jaline.put(jsline);

                }
                jsrec.put("PRLINE", jaline.toString());
            } else {
                jsrec.put("PRLINE", "");
            }


            //最外层
            jsdata.put("appid", appid);
            jsdata.put("objectname", GlobalConfig.PR_NAME);
            jsdata.put("username", userid);
            jsdata.put("option", "sync");
            jsdata.put("rec", jsrec.toString());

            return jsdata.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }


    //获取身份证号
    public static String getIdentity(String data) {
        Log.e(TAG, "data=" + data);
        String id_number = null;
        if (!TextUtils.isEmpty(data)) {

            try {
                JSONObject jsonObject = new JSONObject(data);
                id_number = jsonObject.getString("id_number");
                return id_number;

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 转换斜杠
     **/

    //http://mobile.faw.com.cn:8091/maximo/file/app/ibm/doclinks/attach2017/1503035302926.docx
    public static String getSlash(String str) {
//        String qzurl = "C:\\doclinks";
        String qzurl = "/app/ibm/doclinks";
        if (!str.isEmpty()) {
            str = str.replace(qzurl, "");
            str = str.replaceAll("\\\\", "/");
        }


        return str;
    }

    /**
     * 获取文件名
     **/
    //http://10.60.12.98/attach2017/1503304774234.xlsx
    public static String getFile(String str) {
//        String qzurl = "C:\\doclinks";
        String qzurl = "/app/ibm/doclinks/attach2017";
        if (!str.isEmpty()) {
            int index = str.lastIndexOf("/");
            str = str.substring(index + 1, str.length());
        }


        return str;
    }
}
