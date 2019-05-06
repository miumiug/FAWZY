package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_WPITEM.WPITEM;
import com.hqxh.fiamproperty.ui.activity.HomeActivity;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;

/**
 * 调件任务单明细信息
 */

public class DjWpitemAdapter extends BaseQuickAdapter<WPITEM> {
    private static final String TAG = "DjWpitemAdapter";
    private Context context;

    private cOnClickListener mOnClickListener; //点击事件

    private int mark;

    public DjWpitemAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
        this.context = context;
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(final BaseViewHolder helper, WPITEM item) {

        helper.setText(R.id.description_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
        helper.setText(R.id.wpt5_text_id, JsonUnit.convertStrToArray(item.getWPT5())[0]);
        helper.setText(R.id.qty_text_id, JsonUnit.convertStrToArray(item.getITEMQTY())[0]);
        helper.setText(R.id.orderunit_text_id, JsonUnit.convertStrToArray(item.getORDERUNIT())[0]);
        helper.setText(R.id.ownername_text_id, JsonUnit.convertStrToArray(item.getOWNERNAME())[0]);

        if (JsonUnit.convertStrToArray(item.getOWNER())[1].equals(GlobalConfig.NOTREADONLY) && mark == HomeActivity.DB_CODE) {
            Drawable nav_up = context.getResources().getDrawable(R.drawable.ic_search_black);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            TextView ownerText = helper.getView(R.id.ownername_text_id);
            ownerText.setCompoundDrawablesWithIntrinsicBounds(null, null, nav_up, null);
            helper.setOnClickListener(R.id.ownername_text_id, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClickListener.cOnClickListener(helper.getLayoutPosition());
                }
            });
        }


    }


    public void setMark(int mark) {
        this.mark = mark;
    }

    public interface cOnClickListener {
        public void cOnClickListener(int postion);
    }


    public cOnClickListener getmOnClickListener() {
        return mOnClickListener;
    }

    public void setmOnClickListener(cOnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
}
