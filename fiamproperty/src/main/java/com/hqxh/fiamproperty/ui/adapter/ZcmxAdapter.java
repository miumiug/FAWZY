package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.util.Log;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_GRLINE.GRLINE;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;

/**
整车明细
 */

public class ZcmxAdapter extends BaseQuickAdapter<GRLINE> {


    public ZcmxAdapter(Context context, int layoutResId, List<GRLINE> data) {
        super(context, layoutResId, data);
    }

    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }
    @Override
    protected void convert(BaseViewHolder helper, GRLINE item) {


        helper.setText(R.id.samplenum_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
        helper.setText(R.id.udmodel_text_id, JsonUnit.convertStrToArray(item.getGRLIN1())[0]);
        helper.setText(R.id.udlicensenum_text_id, JsonUnit.convertStrToArray(item.getGRLIN3())[0]);
        helper.setText(R.id.udvehicletype_text_id, JsonUnit.convertStrToArray(item.getGRLIN4())[0]);

    }


}
