package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.model.R_PAYCHECKLINE;
import com.hqxh.fiamproperty.model.R_WORKREPORT;
import com.hqxh.fiamproperty.model.R_WORKREPORTLINE;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.unit.JsonUnit;

import java.util.List;

/**
 * Created by Administrator on 2018\6\21 0021.
 */

public class WorkReportLineAdapter extends BaseQuickAdapter<R_WORKREPORTLINE.WORKREPORTLINE> {
    private Animation rotate;
    private R_WORKREPORT.WORKREPORT workreport;

    public R_WORKREPORT.WORKREPORT getWorkreport() {
        return workreport;
    }

    public void setWorkreport(R_WORKREPORT.WORKREPORT workreport) {
        this.workreport = workreport;
    }

    public WorkReportLineAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
        rotate = AnimationUtils.loadAnimation(context, R.anim.arrow_rotate);//创建动画
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(final BaseViewHolder helper, R_WORKREPORTLINE.WORKREPORTLINE item) {
        helper.setText(R.id.text_createby,JsonUnit.convertStrToArray(workreport.getCREATENAME())[0] );
        helper.setText(R.id.text_num_id, String.valueOf(helper.getPosition() + 1));
        helper.setText(R.id.text_car_num, JsonUnit.convertStrToArray(item.getWL01())[0]);
        helper.setText(R.id.text_first_content, JsonUnit.convertStrToArray(item.getACTIVITY1DESC())[0]);
        helper.setText(R.id.text_second_content, JsonUnit.convertStrToArray(item.getACTIVITY2DESC())[0]);
        helper.setText(R.id.text_num, JsonUnit.convertStrToArray(item.getPARTNO())[0]);
        helper.setText(R.id.text_name, JsonUnit.convertStrToArray(item.getPARTDESC())[0]);
        helper.setText(R.id.text_thing_name, JsonUnit.convertStrToArray(item.getRESULTDESC())[0]);
        helper.setText(R.id.text_start_time, JsonUnit.convertStrToArray(item.getPLANSTARTDATE())[0]);
        helper.setText(R.id.text_finish_time, JsonUnit.convertStrToArray(item.getPLANENDDATE())[0]);
        helper.setText(R.id.text_content1, JsonUnit.convertStrToArray(item.getCOMMENTS())[0]);
        helper.setText(R.id.text_time1, JsonUnit.convertStrToArray(item.getACTURALHOURS())[0]);
        helper.setText(R.id.text_content2, JsonUnit.convertStrToArray(item.getCOMMENTS2())[0]);
        helper.setText(R.id.text_time2, JsonUnit.convertStrToArray(item.getACTURALHOURS2())[0]);
        helper.setText(R.id.text_content3, JsonUnit.convertStrToArray(item.getCOMMENTS3())[0]);
        helper.setText(R.id.text_time3, JsonUnit.convertStrToArray(item.getACTURALHOURS3())[0]);
        helper.setText(R.id.text_content4, JsonUnit.convertStrToArray(item.getCOMMENTS4())[0]);
        helper.setText(R.id.text_time4, JsonUnit.convertStrToArray(item.getACTURALHOURS4())[0]);
        helper.setText(R.id.text_content5, JsonUnit.convertStrToArray(item.getCOMMENTS5())[0]);
        helper.setText(R.id.text_time5, JsonUnit.convertStrToArray(item.getACTURALHOURS5())[0]);
        helper.setOnClickListener(R.id.text_more, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate.setInterpolator(new LinearInterpolator());//设置为线性旋转

                rotate.setFillAfter(!rotate.getFillAfter());//每次都取相反值，使得可以不恢复原位的旋转
                v.startAnimation(rotate);

                boolean falg = rotate.getFillAfter();
                if (falg) {
                    helper.setVisible(R.id.linearlayout_id,true);
                } else {

                    helper.setVisible(R.id.linearlayout_id,false);
                }
            }
        });

    }

}

