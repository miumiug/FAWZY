package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_BORELATION.BORELATION;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 差旅报销单-借款单
 */
public class BorelationAdapter extends BaseQuickAdapter<BORELATION> {


    public BorelationAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, BORELATION item) {
        helper.setText(R.id.bonum_text_id, JsonUnit.convertStrToArray(item.getBONUM())[0]);
        helper.setText(R.id.bodesc_text_id, JsonUnit.convertStrToArray(item.getBODESC())[0]);
        helper.setText(R.id.boenterdate_text_id, JsonUnit.strToDateString(JsonUnit.convertStrToArray(item.getBOENTERDATE())[0]));
        helper.setText(R.id.bototalcost_text_id, JsonUnit.convertStrToArray(item.getBOTOTALCOST())[0]);

    }


}
