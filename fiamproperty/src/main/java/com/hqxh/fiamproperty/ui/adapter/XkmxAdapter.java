package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_PAYPLANPROJECT.PAYPLANPROJECT;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 需款项目
 */
public class XkmxAdapter extends BaseQuickAdapter<PAYPLANPROJECT> {

    public XkmxAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, PAYPLANPROJECT item) {
        helper.setText(R.id.projectid1_text, JsonUnit.convertStrToArray(item.getPROJECTID())[0]);
        helper.setText(R.id.description12_text, JsonUnit.convertStrToArray(item.getFINCNTRLDESC())[0]);
        helper.setText(R.id.description13_text, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
        helper.setText(R.id.linecost2_text, JsonUnit.convertStrToArray(item.getLINECOST())[0]);


    }





}
