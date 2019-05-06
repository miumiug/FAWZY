package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_WFTRANSACTION.WFTRANSACTION;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 审批记录
 */
public class WftransactionAdapter extends BaseQuickAdapter<WFTRANSACTION> {

    public WftransactionAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, WFTRANSACTION item) {

        helper.setText(R.id.displayname_text_id, JsonUnit.convertStrToArray(item.getDISPLAYNAME())[0]);
        helper.setText(R.id.node_text_id, JsonUnit.convertStrToArray(item.getNODE())[0]);
        helper.setText(R.id.trnsdate_text_id, JsonUnit.convertStrToArray(item.getTRANSDATE())[0]);
        helper.setText(R.id.memo_text_id, JsonUnit.convertStrToArray(item.getMEMO())[0]);
    }





}
