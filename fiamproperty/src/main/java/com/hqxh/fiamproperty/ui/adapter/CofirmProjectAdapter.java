package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_WORKREPORT;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;

/**
 * Created by Administrator on 2018\6\25 0025.
 */

public class CofirmProjectAdapter extends BaseQuickAdapter<R_WORKREPORT.WORKREPORT> {


    public CofirmProjectAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, R_WORKREPORT.WORKREPORT item) {
        helper.setText(R.id.text_tittle, JsonUnit.convertStrToArray(item.getPROJECTDESC())[0]);
        helper.setText(R.id.text_number, JsonUnit.convertStrToArray(item.getWRNUM())[0]);
        helper.setText(R.id.text_projectnum, JsonUnit.convertStrToArray(item.getPROJECTID())[0]);
        if (JsonUnit.convertStrToArray(item.getSTATUSDESC())[0].equals("草稿")){
            helper.setTextColorRes(R.id.text_status,R.color.text_draft_color);
        }else if (JsonUnit.convertStrToArray(item.getSTATUSDESC())[0].equals("待确认")){
            helper.setTextColorRes(R.id.text_status,R.color.red);
        }else if (JsonUnit.convertStrToArray(item.getSTATUSDESC())[0].equals("已确认")){
            helper.setTextColorRes(R.id.text_status,R.color.text_green);
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
        helper.setVisible(R.id.check,false);
        helper.setOnClickListener(R.id.text_bh, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        helper.setOnClickListener(R.id.text_sp, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        helper.setText(R.id.text_brief, JsonUnit.convertStrToArray(item.getBRIEF())[0]);
        helper.setVisible(R.id.text_bh, false);
        helper.setVisible(R.id.text_sp, false);
    }
}
