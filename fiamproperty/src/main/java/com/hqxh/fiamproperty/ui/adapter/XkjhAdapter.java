package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_PAYPLAN.PAYPLAN;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 需款计划
 */
public class XkjhAdapter extends BaseQuickAdapter<PAYPLAN> {

    public XkjhAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, PAYPLAN item) {
        helper.setText(R.id.wonum_text_id, JsonUnit.convertStrToArray(item.getPAYPLANNUM())[0]);
        helper.setText(R.id.description_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
        helper.setText(R.id.reportedby_text_id, "申请人:"+JsonUnit.convertStrToArray(item.getENTERBYNAME())[0]);
        helper.setText(R.id.fincntrldesc_text_id, "状态:"+JsonUnit.convertStrToArray(item.getSTATUSDESC())[0]);
        switch (helper.getPosition()%2){
            case 0:
                helper.setBackgroundRes(R.id.wonum_text_id,R.drawable.design_one_point);
                break;
            case 1:
                helper.setBackgroundRes(R.id.wonum_text_id,R.drawable.design_two_point);
                break;

        }
    }





}
