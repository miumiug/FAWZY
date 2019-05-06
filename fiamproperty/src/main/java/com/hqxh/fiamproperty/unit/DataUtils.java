package com.hqxh.fiamproperty.unit;

import java.util.Calendar;

/**
 * 数组操作工具类
 * 
 * Utils of data operation
 *
 * @author AigeStudio 2015-07-22
 */
public final class DataUtils {
    /**
     * 一维数组转换为二维数组
     *
     * @param src    ...
     * @param row    ...
     * @param column ...
     * @return ...
     */
    public static String[][] arraysConvert(String[] src, int row, int column) {
        String[][] tmp = new String[row][column];
        for (int i = 0; i < row; i++) {
            tmp[i] = new String[column];
            System.arraycopy(src, i * column, tmp[i], 0, column);
        }
        return tmp;
    }

    /***
     * 获取当前年
     */


     public static int getYear(){
         Calendar now = Calendar.getInstance();
         return now.get(Calendar.YEAR);
     }

    /**获取当前月份
     *
     */
    public static int getMonths(){
        Calendar now = Calendar.getInstance();
        return (now.get(Calendar.MONTH) + 1);
    }


}
