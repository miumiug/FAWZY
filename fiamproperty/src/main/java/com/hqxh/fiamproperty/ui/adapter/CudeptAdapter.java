package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_CUDEPT.CUDEPT;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 部门名称
 */
public class CudeptAdapter extends BaseQuickAdapter<CUDEPT> {

    public CudeptAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, CUDEPT item) {
        helper.setText(R.id.deptnum_text_id, JsonUnit.convertStrToArray(item.getDEPTNUM())[0]);
        helper.setText(R.id.description_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
    }


}
