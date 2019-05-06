package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_EXPENSELINE.EXPENSELINE;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 交通明细/报销明细
 */
public class ExpenselineAdapter extends BaseQuickAdapter<EXPENSELINE> {

    private String appid;
    public ExpenselineAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, EXPENSELINE item) {
        if(appid.equals(GlobalConfig.EXPENSES_APPID)){//差旅报销
            helper.setText(R.id.time_text_id, JsonUnit.strToDateString(JsonUnit.convertStrToArray(item.getACTSTART())[0]) + "至" + JsonUnit.strToDateString(JsonUnit.convertStrToArray(item.getACTFINISH())[0]));
            helper.setText(R.id.dd_text_id, JsonUnit.convertStrToArray(item.getADDRESSBEGIN())[0] + "-" + JsonUnit.convertStrToArray(item.getADDRESSEND())[0]);
            helper.setText(R.id.transport_text_id, JsonUnit.convertStrToArray(item.getTRANSPORT())[0]);
            helper.setText(R.id.linecost_text_id, JsonUnit.convertStrToArray(item.getLINECOST())[0]);
        }else if(appid.equals(GlobalConfig.EXPENSE_APPID)){//备用金报销
            helper.setText(R.id.invoicedate_text_id, JsonUnit.strToDateString(JsonUnit.convertStrToArray(item.getINVOICEDATE())[0]));
            helper.setText(R.id.description_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
            helper.setText(R.id.remark_text_id, JsonUnit.convertStrToArray(item.getREMARK())[0]);
            helper.setText(R.id.totalcost_text_id, JsonUnit.convertStrToArray(item.getLINECOST())[0]);
            helper.setText(R.id.tax_text_id, JsonUnit.convertStrToArray(item.getTAX())[0]);
        }


    }


    public void setAppid(String appid){
        this.appid=appid;
    }

}
