package com.hqxh.fiamproperty.ui.activity.GNWorker.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_Workorder;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 合同
 */
public class TaskSelectAdapter extends BaseQuickAdapter<R_Workorder.Workorder> {

    public TaskSelectAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, R_Workorder.Workorder item) {
        helper.setText(R.id.wonum_text_id, JsonUnit.convertStrToArray(item.getWONUM())[0]);
        helper.setText(R.id.description_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
        helper.setText(R.id.WORKTYPE_text_id, JsonUnit.convertStrToArray(item.getWORKTYPE())[0]);
        helper.setText(R.id.REPORTEDBYNAME_text_id, JsonUnit.convertStrToArray(item.getREPORTEDBYNAME())[0]);
//        helper.setText(R.id.PROJECTDESC_text_id, JsonUnit.convertStrToArray(item.getPROJECTDESC())[0]);
        helper.setText(R.id.STATUS_text_id, "状态:"+ JsonUnit.convertStrToArray(item.getSTATUSDESC())[0]);
        switch (helper.getPosition()%2){
            case 0:
                helper.setBackgroundRes(R.id.wonum_text_id, R.drawable.design_one_point);
                break;
            case 1:
                helper.setBackgroundRes(R.id.wonum_text_id, R.drawable.design_two_point);
                break;

        }
    }





}
