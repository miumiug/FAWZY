package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_CONTRACTPAY.CONTRACTPAY;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 合同-付款计划
 */
public class ContractpayAdapter extends BaseQuickAdapter<CONTRACTPAY> {

    public ContractpayAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, CONTRACTPAY item) {
        helper.setText(R.id.conpaylinenum_text_id, JsonUnit.convertStrToArray(item.getCONPAYLINENUM())[0]);
        helper.setText(R.id.term_text_id, JsonUnit.convertStrToArray(item.getTERM())[0]);
        helper.setText(R.id.duedate_text_id, JsonUnit.strToDateString(JsonUnit.convertStrToArray(item.getDUEDATE())[0]));
        helper.setText(R.id.linecost_text_id, JsonUnit.convertStrToArray(item.getLINECOST())[0]);
        helper.setText(R.id.paymentpercent_text_id, JsonUnit.convertStrToArray(item.getPAYMENTPERCENT())[0]);
        if (JsonUnit.convertStrToArray(item.getISADVANCE())[0].equals("0")) {
            helper.setChecked(R.id.isadvance_text_id, false);
        } else {
            helper.setChecked(R.id.isadvance_text_id, true);
        }

    }


}
