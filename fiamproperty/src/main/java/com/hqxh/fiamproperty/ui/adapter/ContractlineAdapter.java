package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_CONTRACTLINE.CONTRACTLINE;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 合同-合同行
 */
public class ContractlineAdapter extends BaseQuickAdapter<CONTRACTLINE> {

    public ContractlineAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, CONTRACTLINE item) {
        helper.setText(R.id.contractline_text_id, JsonUnit.convertStrToArray(item.getCONTRACTLINE())[0]);
        helper.setText(R.id.description_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
        helper.setText(R.id.orderqty_text_id, JsonUnit.convertStrToArray(item.getORDERQTY())[0]);
        helper.setText(R.id.unitcost_text_id, JsonUnit.convertStrToArray(item.getUNITCOST())[0]);
        helper.setText(R.id.linecost_text_id, JsonUnit.convertStrToArray(item.getLINECOST())[0]);
        helper.setText(R.id.projectid_text_id, JsonUnit.convertStrToArray(item.getPROJECTID())[0]);
    }


}
