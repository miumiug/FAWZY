package com.hqxh.fiamproperty.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hqxh.fiamproperty.R;


public class LoadingDialog extends Dialog {

    private TextView mHtvText;
    private String mText;
    public LoadingDialog(Context context, String text,int themeResId) {
        super(context,themeResId);
        this.mText=text;
        init();
    }



    private void init() {
        setContentView(R.layout.dialogui_loading_horizontal);
        mHtvText = (TextView) findViewById(R.id.dialogui_tv_msg);
        mHtvText.setText(mText);
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }


    public static Dialog createLoadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialogui_loading_horizontal, null);// 得到加载view
        TextView mHtvText = (TextView) v.findViewById(R.id.dialogui_tv_msg);

        mHtvText.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;

    }
}
