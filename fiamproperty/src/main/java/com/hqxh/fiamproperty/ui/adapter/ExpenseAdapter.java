package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_EXPENSE.EXPENSE;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 差旅报销单
 */
public class ExpenseAdapter extends BaseQuickAdapter<EXPENSE> {

    public ExpenseAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, EXPENSE item) {
        helper.setText(R.id.wonum_text_id, JsonUnit.convertStrToArray(item.getEXPENSENUM())[0]);
        helper.setText(R.id.description_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
        helper.setText(R.id.reportedby_text_id, "报销人:"+JsonUnit.convertStrToArray(item.getENTERBY())[0]);
        helper.setText(R.id.fincntrldesc_text_id, "类别:"+JsonUnit.convertStrToArray(item.getWFTYPE())[0]);
        switch (helper.getPosition()%2){
            case 0:
                helper.setBackgroundRes(R.id.wonum_text_id,R.drawable.design_one_point);
                break;
            case 1:
                helper.setBackgroundRes(R.id.wonum_text_id,R.drawable.design_two_point);
                break;

        }
    }





}
