package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.util.Log;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;

import com.hqxh.fiamproperty.model.R_Wfassignemt.Wfassignment;


/**
 * Created by apple on 15/10/26
 * 待办任务
 */
public class WfassignmentAdapter extends BaseQuickAdapter<Wfassignment> {

    public WfassignmentAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, Wfassignment item) {
        String app = JsonUnit.convertStrToArray(item.getAPP())[0];
        if (app.equals(GlobalConfig.TRAVEL) || app.equals(GlobalConfig.TRAVELS_APPID)) {//国内出差/出国
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_ccsq);
        } else if (app.equals(GlobalConfig.GRWZ)) {//出门管理
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_cmgl);
        } else if (app.equals(GlobalConfig.JSPR_APPID)) {//技术采购申请
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_jscgsq);
        } else if (app.equals(GlobalConfig.SZPR_APPID)) {//试制采购申请
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_szcgsq);
        } else if (app.equals(GlobalConfig.PR_APPID)) {//物资采购申请
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_wzcgsq);
        } else if (app.equals(GlobalConfig.FWPR_APPID)) {//服务采购申请
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_fwcgsq);
        } else if (app.equals(GlobalConfig.WPPR_APPID)) {//外培采购申请
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_wpcgsq);
        } else if (app.equals(GlobalConfig.TOSY_APPID)) {//试验任务单
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_syrwd);
        } else if (app.equals(GlobalConfig.TOSZ_APPID)) {//试制任务单
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_szrwd);
        } else if (app.equals(GlobalConfig.TOLL_APPID)) {//物资领料单
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_wzlld);
        } else if (app.equals(GlobalConfig.TODJ_APPID)) {//调件任务单
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_djrwd);
        } else if (app.equals(GlobalConfig.TOOIL_APPID)) {//燃油申请单
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_rysq);
        } else if (app.equals(GlobalConfig.TOQT_APPID)) {//其它任务单
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_qtrwd);
        } else if (app.equals(GlobalConfig.GRWZ)) {//合同
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_ht);
        } else if (app.equals(GlobalConfig.PAYCHECK_APPID)) {//付款验收
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_fkys);
        } else if (app.equals(GlobalConfig.PP_APPID)) {//需款计划
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_xkjh);
        } else if (app.equals(GlobalConfig.EXPENSES_APPID) || app.equals(GlobalConfig.EXPENSE_APPID) || app.equals(GlobalConfig.BO_APPID)) {//报销
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_bx);
        } else {//其他
            helper.setImageResource(R.id.task_image_id, R.drawable.ic_qt);
        }

        helper.setText(R.id.duedate_text_id, JsonUnit.convertStrToArray(item.getDUEDATE())[0]);
        helper.setText(R.id.processname_text_id, JsonUnit.convertStrToArray(item.getUDAPPNAME())[0]);
        helper.setText(R.id.description_text_id, JsonUnit.convertStrToArray(item.getDESCRIPTION())[0]);
    }


}
