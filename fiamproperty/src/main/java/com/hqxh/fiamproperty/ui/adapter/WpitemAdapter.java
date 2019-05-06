package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_WPITEM.WPITEM;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;

/**
 * 物资领料单明细信息
 */

public class WpitemAdapter extends BaseQuickAdapter<WPITEM>{




    public WpitemAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }
    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, WPITEM item) {
        helper.setText(R.id.description1_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
        helper.setText(R.id.grlin1_text_id,JsonUnit.convertStrToArray(item.getWPT5())[0]);
        helper.setText(R.id.measureunitid_text_id,JsonUnit.convertStrToArray(item.getORDERUNIT())[0]);
        helper.setText(R.id.qty_text_id,JsonUnit.convertStrToArray(item.getITEMQTY())[0]);
    }

}
