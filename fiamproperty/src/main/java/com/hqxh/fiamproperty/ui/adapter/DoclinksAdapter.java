package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_DOCLINKS.DOCLINKS;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 附件文档
 */
public class DoclinksAdapter extends BaseQuickAdapter<DOCLINKS> {
    /**
     * 点击事件*
     */
    public cOnClickListener onclicklistener;

    public DoclinksAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DOCLINKS item) {
        Log.e(TAG, "文件路径=" + JsonUnit.convertStrToArray(item.getURLNAME())[0]);
        Log.e(TAG, "文件名称=" + JsonUnit.convertStrToArray(item.getDOCUMENT())[0]);

        helper.setText(R.id.document_text_id, JsonUnit.convertStrToArray(item.getDOCUMENT())[0]);
        helper.setText(R.id.description_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
        helper.setText(R.id.create_owner_text_id, JsonUnit.convertStrToArray(item.getOWNER())[0]);
        helper.setText(R.id.createdate_text_id, JsonUnit.strToDateString(JsonUnit.convertStrToArray(item.getCREATEDATE())[0]));
        helper.setTextColorRes(R.id.preview_button_id, R.color.btn_color);
        helper.setOnClickListener(R.id.preview_button_id, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicklistener.cOnClickListener(helper.getPosition(), JsonUnit.convertStrToArray(item.getURLNAME())[0]);
            }
        });
    }

    public interface cOnClickListener {
        public void cOnClickListener(int postion, String urlname);
    }

    public cOnClickListener getOnclicklistener() {
        return onclicklistener;
    }

    public void setOnclicklistener(cOnClickListener onclicklistener) {
        this.onclicklistener = onclicklistener;
    }
}
