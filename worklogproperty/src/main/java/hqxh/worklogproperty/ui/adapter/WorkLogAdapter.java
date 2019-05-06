package hqxh.worklogproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.util.Log;

import java.util.List;

import hqxh.worklogproperty.R;
import hqxh.worklogproperty.model.R_WORKDAILYLOG.WORKDAILYLOG;
import hqxh.worklogproperty.until.JsonUnit;
import hqxh.worklogproperty.widget.BaseViewHolder;


/**
 * Created by apple on 15/10/26
 * 工作日志
 */
public class WorkLogAdapter extends BaseQuickAdapter<WORKDAILYLOG> {


    public WorkLogAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, WORKDAILYLOG item) {
        helper.setText(R.id.dailytype_text_id, JsonUnit.convertStrToArray(item.getDAILYTYPE())[0]);
        helper.setText(R.id.description_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
        Log.e(TAG, "PROJECTID=" + JsonUnit.convertStrToArray(item.getPROJECTID())[0]);
        helper.setText(R.id.projectid_text_id, JsonUnit.convertStrToArray(item.getPROJECTID())[0]);
        helper.setText(R.id.manhour_text_id, JsonUnit.convertStrToArray(item.getMANHOUR())[0]);
        helper.setText(R.id.status_text_id, JsonUnit.convertStrToArray(item.getSTATUS())[0]);

    }


}
