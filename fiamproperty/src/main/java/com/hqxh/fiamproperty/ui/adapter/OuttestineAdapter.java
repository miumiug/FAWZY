package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_OUTTESTLINE.OUTTESTLINE;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 外出试验－补助明细
 */
public class OuttestineAdapter extends BaseQuickAdapter<OUTTESTLINE> {


    public OuttestineAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, OUTTESTLINE item) {
        helper.setText(R.id.linenum_text_id, JsonUnit.convertStrToArray(item.getLINENUM())[0]);
        helper.setText(R.id.pegiontype_text_id, JsonUnit.convertStrToArray(item.getPEGIONTYPE())[0]);
        helper.setText(R.id.persontype_text_id, JsonUnit.convertStrToArray(item.getPERSONTYPE())[0]);
        helper.setText(R.id.stayname_text_id, JsonUnit.convertStrToArray(item.getSTAYNAME())[0]);
        helper.setText(R.id.startdate_text_id, JsonUnit.strToDateString(JsonUnit.convertStrToArray(item.getSTARTDATE())[0]));
        helper.setText(R.id.enddate_text_id, JsonUnit.strToDateString(JsonUnit.convertStrToArray(item.getENDDATE())[0]));
        helper.setText(R.id.staydays_text_id, JsonUnit.convertStrToArray(item.getSTAYDAYS())[0]);
        helper.setText(R.id.linecost_text_id, JsonUnit.convertStrToArray(item.getLINECOST())[0]);
        helper.setText(R.id.standard_text_id, JsonUnit.convertStrToArray(item.getSTANDARD())[0]);
        helper.setText(R.id.place_text_id, JsonUnit.convertStrToArray(item.getPLACE())[0]);

    }


}
