package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_PRLINE.PRLINE;
import com.hqxh.fiamproperty.ui.activity.HomeActivity;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 试制采购申请-申请明细
 */
public class PrlineAdapter extends BaseQuickAdapter<PRLINE> {

    private String appid;
    private int mark;

    private cOnClickListener mOnClickListener; //点击事件

    private Context context;

    public PrlineAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(final BaseViewHolder helper, PRLINE item) {
        helper.setText(R.id.prlinenum_text_id, JsonUnit.convertStrToArray(item.getPRLINENUM())[0]);
        helper.setText(R.id.description_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
        helper.setText(R.id.udmodel_text_id, JsonUnit.convertStrToArray(item.getUDMODEL())[0]);
        helper.setText(R.id.orderqty_text_id, JsonUnit.convertStrToArray(item.getORDERQTY())[0]);
        if (appid.equals(GlobalConfig.SZPR_APPID)) { //试制采购申请
            helper.setText(R.id.unitcost_text_id, JsonUnit.convertStrToArray(item.getUNITCOST2())[0]);
            helper.setText(R.id.linecost_text_id, JsonUnit.convertStrToArray(item.getLINECOST2())[0]);
            helper.setText(R.id.fy_linecost_text_id, JsonUnit.convertStrToArray(item.getLINECOST())[0]);
            helper.setText(R.id.unitcost1_text_id, JsonUnit.convertStrToArray(item.getUNITCOST1())[0]);
        } else if (appid.equals(GlobalConfig.PR_APPID)) {//物资采购申请
            helper.setText(R.id.unitcost_text_id, JsonUnit.convertStrToArray(item.getUNITCOST())[0]);
            helper.setText(R.id.linecost_text_id, JsonUnit.convertStrToArray(item.getLINECOST())[0]);
            helper.setText(R.id.ownername_text_id, JsonUnit.convertStrToArray(item.getASSIGNERPERSON())[0]);
            helper.setVisible(R.id.view_id, false);
            helper.setVisible(R.id.linearlayout_id, false);
            helper.setVisible(R.id.linearlayout_1_id, true);
            helper.setVisible(R.id.view_1_id, true);
            Log.e(TAG,""+JsonUnit.convertStrToArray(item.getUDASSIGNER())[1]);
            if (JsonUnit.convertStrToArray(item.getUDASSIGNER())[1].equals(GlobalConfig.NOTREADONLY) && mark == HomeActivity.DB_CODE) {
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
    }


    public void setAppid(String appid) {
        this.appid = appid;

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
