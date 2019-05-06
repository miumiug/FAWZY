package cn.hqxh.fiam.mobile1.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import cn.hqxh.fiam.mobile1.model.Login;


/**
 * Json数据解析封装类
 */
public class JsonUtils {
    private static final String TAG = "JsonUtils";

    /**
     * 风机票安全措施
     */
    public static ArrayList<Login> parsingLogin(String data) {
        ArrayList<Login> list = null;
        Login user = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<Login>();
            for (int i = 0; i < jsonArray.length(); i++) {
                user = new Login();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = user.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = user.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(user);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = user.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(user, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(user);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


}