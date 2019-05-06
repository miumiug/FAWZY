package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_PR.PR;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 采购申请
 */
public class PrAdapter extends BaseQuickAdapter<PR> {

    private String appid;

    public PrAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, PR item) {
        helper.setText(R.id.wonum_text_id, JsonUnit.convertStrToArray(item.getPRNUM())[0]);
        helper.setText(R.id.description_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
        helper.setText(R.id.reportedby_text_id, "状态:" + JsonUnit.convertStrToArray(item.getSTATUSDESC())[0]);
        if (appid.equals(GlobalConfig.WPPR_APPID)) {
            helper.setText(R.id.fincntrldesc_text_id, "项目名称:" + JsonUnit.convertStrToArray(item.getPROJECTDESC())[0]);

        } else {
            helper.setText(R.id.fincntrldesc_text_id, "类型:" + JsonUnit.convertStrToArray(item.getCUTYPE())[0]);

        }
        switch (helper.getPosition() % 2) {
            case 0:
                helper.setBackgroundRes(R.id.wonum_text_id, R.drawable.design_one_point);
                break;
            case 1:
                helper.setBackgroundRes(R.id.wonum_text_id, R.drawable.design_two_point);
                break;

        }
    }


    public void setAppid(String appid) {
        this.appid = appid;
    }


}
