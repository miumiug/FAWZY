package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_KBFILELIST.KBFILELIST;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 出国立项申请-出国人员知识积累拟交付资料清单
 */
public class KbfilelistAdapter extends BaseQuickAdapter<KBFILELIST> {

    public KbfilelistAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, KBFILELIST item) {
        helper.setText(R.id.linenum_text_id, JsonUnit.convertStrToArray(item.getLINENUM())[0]);
        helper.setText(R.id.filetype_text_id, JsonUnit.convertStrToArray(item.getFILETYPE())[0]);
        helper.setText(R.id.description_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
        helper.setText(R.id.owner_text_id, JsonUnit.convertStrToArray(item.getOWNER())[0]);
    }


}
