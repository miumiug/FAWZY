package com.hqxh.fiamproperty.ui.widget;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/1/28.
 */
public class DateSelect {
    //日期展示框
    View textView;
    //context
    Context context;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    StringBuffer sb = new StringBuffer();

    public DateSelect(Context context, View textView) {
        this.textView = textView;
        this.context = context;
    }

    /**
     * 设置时间选择器*
     */
    private void setDataListener() {

        final Calendar objTime = Calendar.getInstance();
        int iYear = objTime.get(Calendar.YEAR);
        int iMonth = objTime.get(Calendar.MONTH);
        int iDay = objTime.get(Calendar.DAY_OF_MONTH);
        int hour = objTime.get(Calendar.HOUR_OF_DAY);

        int minute = objTime.get(Calendar.MINUTE);


        datePickerDialog = new DatePickerDialog(context, new datelistener(), iYear, iMonth, iDay);
        timePickerDialog = new CumTimePickerDialog(context, new timelistener(), hour, minute, true);
    }

    private class datelistener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            sb = new StringBuffer();
            if (dayOfMonth < 10) {
                sb.append(year + "-" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
            } else {
                sb.append(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
            sb.append(" 00:00:00");
            updateLabel(textView);
//            timePickerDialog.show();
        }
    }

    private class timelistener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            sb.append(" ");
            if (i1 < 10) {
                sb.append(i + ":" + "0" + i1 + ":00");
            } else {
                sb.append(i + ":" + i1 + ":00");
            }
            updateLabel(textView);
        }
    }

//    //获取一个日历对象
//    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
//    //获取日期格式器对象
//    SimpleDateFormat fmtDateAndTime=new SimpleDateFormat("yyyy-MM-dd");
//
//    //日期
//    //当点击DatePickerDialog控件的设置按钮时，调用该方法
//    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener()
//    {
//        @Override
//        public void onDateSet(DatePicker view, int year, int monthOfYear,
//                              int dayOfMonth) {
//            //修改日历控件的年，月，日
//            //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
//            dateAndTime.set(Calendar.YEAR, year);
//            dateAndTime.set(Calendar.MONTH, monthOfYear);
//            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//            //将页面TextView的显示更新为最新时间
//            updateLabel(textView);
//        }
//    };

    public void showDialog() {
        setDataListener();
        //生成一个DatePickerDialog对象，并显示。显示的DatePickerDialog控件可以选择年月日，并设置
//        new DatePickerDialog(context,
//                d,
//                dateAndTime.get(Calendar.YEAR),
//                dateAndTime.get(Calendar.MONTH),
//                dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
        datePickerDialog.show();
//        timePickerDialog.show();
    }

    //更新页面TextView的方法
    private void updateLabel(View view) {
        ((TextView)view).setText(sb);
        ((TextView)view).clearFocus();
        ((TextView)view).setError(null);
    }
}
