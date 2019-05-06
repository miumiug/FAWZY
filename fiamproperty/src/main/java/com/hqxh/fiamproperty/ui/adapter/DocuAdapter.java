package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_DOCLINK;
import com.hqxh.fiamproperty.model.R_GRLINE;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;

/**
 * Created by Administrator on 2018\6\22 0022.
 */

public class DocuAdapter extends BaseQuickAdapter<R_DOCLINK.DOCLINK> {

    /**
     * 点击事件*
     */
    public DoclinksAdapter.cOnClickListener onclicklistener;

    public DocuAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final R_DOCLINK.DOCLINK item) {
            helper.setText(R.id.text_desc, JsonUnit.convertStrToArray(item.getDOCDESC())[0]);
            helper.setText(R.id.text_num, JsonUnit.convertStrToArray(item.getDOCUMENT())[0]);
            helper.setText(R.id.text_people, JsonUnit.convertStrToArray(item.getOWNERNAME())[0]);
            helper.setText(R.id.text_time, JsonUnit.convertStrToArray(item.getCREATEDATE())[0]);
            helper.setOnClickListener(R.id.card_container,new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onclicklistener.cOnClickListener(helper.getPosition(), JsonUnit.convertStrToArray(item.getURLNAME())[0]);
            }
         });
    }

    public interface cOnClickListener {
        public void cOnClickListener(int postion, String urlname);
    }

    public DoclinksAdapter.cOnClickListener getOnclicklistener() {
        return onclicklistener;
    }

    public void setOnclicklistener(DoclinksAdapter.cOnClickListener onclicklistener) {
        this.onclicklistener = onclicklistener;
    }
}
