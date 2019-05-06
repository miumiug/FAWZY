package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.bean.R_APPROVE;
import com.hqxh.fiamproperty.bean.R_APPROVES;
import com.hqxh.fiamproperty.model.APPROVES;
import com.hqxh.fiamproperty.model.R_WORKREPORT;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.ui.widget.ConfirmDialog;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\6\25 0025.
 */

public class WaitProjectAdapter extends BaseQuickAdapter<R_WORKREPORT.WORKREPORT> {
    Context contexts;
    private boolean allChoose;

    private ArrayList<APPROVES> chooseWorkOrderList = new ArrayList<APPROVES>(); //选中的记录

    public ArrayList<APPROVES> getChooseWorkOrderList() {
        return chooseWorkOrderList;
    }

    public void setChooseWorkOrderList(ArrayList<APPROVES> chooseWorkOrderList) {
        this.chooseWorkOrderList = chooseWorkOrderList;
    }

    /**
     * 选中事件*
     */
    public OnCheckedChangeListener onCheckedChangeListener;

    public WaitProjectAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
        contexts = context;
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final R_WORKREPORT.WORKREPORT item) {
        helper.setText(R.id.text_tittle, JsonUnit.convertStrToArray(item.getPROJECTDESC())[0]);
        helper.setText(R.id.text_number, JsonUnit.convertStrToArray(item.getWRNUM())[0]);
        helper.setText(R.id.text_projectnum, JsonUnit.convertStrToArray(item.getPROJECTID())[0]);
        if (JsonUnit.convertStrToArray(item.getSTATUSDESC())[0].equals("草稿")){
            helper.setTextColorRes(R.id.text_status,R.color.text_draft_color);
        }else if (JsonUnit.convertStrToArray(item.getSTATUSDESC())[0].equals("待确认")){
            helper.setTextColorRes(R.id.text_status,R.color.red);
        }else if (JsonUnit.convertStrToArray(item.getSTATUSDESC())[0].equals("已确认")){
            helper.setTextColorRes(R.id.text_status,R.color.text_back_color_2);
        }else {
            helper.setTextColorRes(R.id.text_status,R.color.btn_onclick);
        }
        helper.setText(R.id.text_status, JsonUnit.convertStrToArray(item.getSTATUSDESC())[0]);
        helper.setText(R.id.text_time, JsonUnit.convertStrToArray(item.getACTURALHOURS())[0] + "小时");
        helper.setText(R.id.text_fzr, JsonUnit.convertStrToArray(item.getSUPERVISORDESC())[0]);
        if (JsonUnit.convertStrToArray(item.getAPPBYPM())[0].equals("1")){
            helper.setChecked(R.id.managerGroupID,true);
        }else {
            helper.setChecked(R.id.managerGroupID,false);
        }
        helper.setChecked(R.id.check, allChoose);
//        for (int i=0;i<chooseWorkOrderList.size();i++){
//            if (JsonUnit.convertStrToArray(item.getWORKREPORTID())[0] == chooseWorkOrderList.get(i).getOwnerid()){
//                helper.setChecked(R.id.check, allChoose);
//            }
//        }
//        helper.setOnCheckedChangeListener(R.id.check, new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                onCheckedChangeListener.cOnCheckedChangeListener(b, helper.getPosition(), item);
//            }
//        });
        helper.setOnClickListener(R.id.text_bh, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) contexts
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.dialog, null);//获取自定义布局
                final TextView description =  (TextView) layout.findViewById(R.id.description);
                TextView cancle =  (TextView) layout.findViewById(R.id.cancle);
                TextView buy_sale =  (TextView) layout.findViewById(R.id.buy_sale);
                final AlertDialog dialog = new AlertDialog.Builder(contexts)
                        .setView(layout)
                        .create();
                //设置关闭对话框的按钮
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                buy_sale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        APPROVES approves = new APPROVES();
                        approves.setOwnertable("WORKREPORT");
                        approves.setMemo(description.getText().toString());
                        approves.setSelectWhat("0");
                        approves.setOwnerid(JsonUnit.convertStrToArray(item.getWORKREPORTID())[0]);
                        approves.setUserid(AccountUtils.getpersonId(contexts));
                        helper.setChecked(R.id.check,true);
                        chooseWorkOrderList.add(approves);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        helper.setOnClickListener(R.id.text_sp, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) contexts
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.dialog, null);//获取自定义布局
                final TextView description =  (TextView) layout.findViewById(R.id.description);
                TextView cancle =  (TextView) layout.findViewById(R.id.cancle);
                TextView buy_sale =  (TextView) layout.findViewById(R.id.buy_sale);
                final AlertDialog dialog = new AlertDialog.Builder(contexts)
                        .setView(layout)
                        .create();
                //设置关闭对话框的按钮
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                buy_sale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        APPROVES approves = new APPROVES();
                        approves.setOwnertable("WORKREPORT");
                        approves.setMemo(description.getText().toString());
                        approves.setSelectWhat("1");
                        approves.setOwnerid(JsonUnit.convertStrToArray(item.getWORKREPORTID())[0]);
                        approves.setUserid(AccountUtils.getpersonId(contexts));
                        helper.setChecked(R.id.check,true);
                        chooseWorkOrderList.add(approves);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        helper.setText(R.id.text_brief, JsonUnit.convertStrToArray(item.getBRIEF())[0]);
    }

    /**
     * 设置全选
     **/
    public void setAll(boolean b) {
        allChoose = b;
    }

    public interface OnCheckedChangeListener {
        public void cOnCheckedChangeListener(boolean b, int postion,R_WORKREPORT.WORKREPORT item);
    }

    public OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }
}
