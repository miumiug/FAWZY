package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_PAYCHECKRELATION.PAYCHECKRELATION;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 备用金报销-付款验收
 */
public class PaycheckrelationAdapter extends BaseQuickAdapter<PAYCHECKRELATION> {


    public PaycheckrelationAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, PAYCHECKRELATION item) {
        helper.setText(R.id.paychecknum_text_id, JsonUnit.convertStrToArray(item.getPAYCHECKNUM())[0]);
        helper.setText(R.id.paycheckdesc_text_id, JsonUnit.convertStrToArray(item.getPAYCHECKDESC())[0]);
        helper.setText(R.id.projectid_text_id, JsonUnit.convertStrToArray(item.getPROJECTID())[0]);
        helper.setText(R.id.totalcost_text_id, JsonUnit.convertStrToArray(item.getTOTALCOST())[0]);
        helper.setText(R.id.tax_text_id, JsonUnit.convertStrToArray(item.getTAX())[0]);

    }


}
