package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_SUBSIDIES.SUBSIDIES;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 补助明细
 */
public class SubsidiesAdapter extends BaseQuickAdapter<SUBSIDIES> {


    public SubsidiesAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, SUBSIDIES item) {
        helper.setText(R.id.linenum_text_id, JsonUnit.convertStrToArray(item.getLINENUM())[0]);
        helper.setText(R.id.standardcost_text_id, JsonUnit.convertStrToArray(item.getSTANDARDCOST())[0]);
        helper.setText(R.id.days_text_id, JsonUnit.convertStrToArray(item.getDAYS())[0]);
        helper.setText(R.id.linecost_text_id, JsonUnit.convertStrToArray(item.getLINECOST())[0]);

    }


}
