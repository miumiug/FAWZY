package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_PAYCHECKLINE.PAYCHECKLINE;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 付款验收-付款明细
 */
public class PaychecklineAdapter extends BaseQuickAdapter<PAYCHECKLINE> {

    public PaychecklineAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, PAYCHECKLINE item) {
        helper.setText(R.id.linenum_text_id, JsonUnit.convertStrToArray(item.getLINENUM())[0]);
        helper.setText(R.id.description2_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION2())[0]);
        helper.setText(R.id.modelnum2_text_id, JsonUnit.convertStrToArray(item.getMODELNUM2())[0]);
    }


}
