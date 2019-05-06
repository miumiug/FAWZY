package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_PERSONRELATION.PERSONRELATION;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 出差人
 */
public class CcrAdapter extends BaseQuickAdapter<PERSONRELATION> {

    private String appid;

    public CcrAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, PERSONRELATION item) {
        if (appid.equals(GlobalConfig.TRAVEL_APPID)||appid.equals(GlobalConfig.TRAVELS_APPID)) {
            helper.setText(R.id.displayname_text_id, JsonUnit.convertStrToArray(item.getDISPLAYNAME())[0] + "," + JsonUnit.convertStrToArray(item.getTITLE())[0]);
            helper.setText(R.id.cudept_text_id, JsonUnit.convertStrToArray(item.getCUDEPT())[0]);
            helper.setText(R.id.cucrew_text_id, JsonUnit.convertStrToArray(item.getCUCREW())[0]);
        } else if (appid.equals(GlobalConfig.EXPENSES_APPID)) {
            helper.setText(R.id.person_title_id, "人员");
            helper.setText(R.id.displayname1_text_id, JsonUnit.convertStrToArray(item.getPERSONNAME())[0] + "," + JsonUnit.convertStrToArray(item.getCUDEPT())[0]);
            helper.setText(R.id.trvcost1_text_id, JsonUnit.convertStrToArray(item.getTRVCOST1())[0]);
            helper.setText(R.id.trvcost2_text_id, JsonUnit.convertStrToArray(item.getTRVCOST2())[0]);
            helper.setText(R.id.linecost_text_id, JsonUnit.convertStrToArray(item.getLINECOST())[0]);
        }

    }


    public void setAppid(String appid) {
        this.appid = appid;
    }

}
