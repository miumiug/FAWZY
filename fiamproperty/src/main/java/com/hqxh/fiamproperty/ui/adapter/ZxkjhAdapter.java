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
 * 需款项目
 */
public class ZxkjhAdapter extends BaseQuickAdapter<PAYPLAN> {

    public ZxkjhAdapter(Context context, int layoutResId, List data) {
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
        helper.setText(R.id.xkjh_text,JsonUnit.convertStrToArray(item.getPAYPLANNUM())[0]);
        helper.setText(R.id.type2_text,JsonUnit.convertStrToArray(item.getTYPE())[0]);
        helper.setText(R.id.description13_text,JsonUnit.convertStrToArray(item.getVENDORNAME())[0]);
        helper.setText(R.id.displayname_text,JsonUnit.convertStrToArray(item.getENTERBYNAME())[0]);
        helper.setText(R.id.isbopayplan1_text,JsonUnit.convertStrToArray(item.getISBOPAYPLAN())[0]);
        helper.setText(R.id.totalcost1_text,JsonUnit.convertStrToArray(item.getTOTALCOST())[0]);
    }
}
