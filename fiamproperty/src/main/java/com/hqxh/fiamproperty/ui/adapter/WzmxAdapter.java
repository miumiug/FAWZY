package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_GRLINE.GRLINE;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public class WzmxAdapter extends BaseQuickAdapter<GRLINE> {

    private String appid;

    public WzmxAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, GRLINE item) {
        if (appid.equals(GlobalConfig.GRWZ)) { //物资明细
            helper.setText(R.id.description1_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
            helper.setText(R.id.grlin1_text_id, JsonUnit.convertStrToArray(item.getGRLIN1())[0]);
            helper.setText(R.id.qty_text_id, JsonUnit.convertStrToArray(item.getQTY())[0]);
            helper.setText(R.id.measureunitid_text_id, JsonUnit.convertStrToArray(item.getMEASUREUNIT())[0]);
        } else if (appid.equals(GlobalConfig.GRZC)) {//整车明细
            helper.setText(R.id.title1_text_id, mContext.getString(R.string.ycdescription_text));
            helper.setText(R.id.title2_text_id, mContext.getString(R.string.xh_grlin1_text));
            helper.setText(R.id.title3_text_id, mContext.getString(R.string.grlin3_text));
            helper.setText(R.id.title4_text_id, mContext.getString(R.string.grlin4_text));

            helper.setText(R.id.description1_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
            helper.setText(R.id.grlin1_text_id, JsonUnit.convertStrToArray(item.getGRLIN1())[0]);
            helper.setText(R.id.qty_text_id, JsonUnit.convertStrToArray(item.getGRLIN3())[0]);
            helper.setText(R.id.measureunitid_text_id, JsonUnit.convertStrToArray(item.getGRLIN4())[0]);
        }


    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
