package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_FCTASKRELATION.FCTASKRELATION;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 项目费用管理-任务单出差申请采购申请的预算明细
 */
public class FctaskrelationAdapter extends BaseQuickAdapter<FCTASKRELATION> {

    public FctaskrelationAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, FCTASKRELATION item) {
        helper.setText(R.id.taskid_text_id, JsonUnit.convertStrToArray(item.getTASKID())[0]);
        helper.setText(R.id.description_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
        helper.setText(R.id.tasktypedesc_text_id, JsonUnit.convertStrToArray(item.getTASKTYPEDESC())[0]);
        helper.setText(R.id.fcyear_text_id, JsonUnit.convertStrToArray(item.getFCYEAR())[0]);
    }


}
