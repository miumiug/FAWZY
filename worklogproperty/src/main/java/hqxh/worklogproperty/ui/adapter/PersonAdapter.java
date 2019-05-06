package hqxh.worklogproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;

import java.util.List;

import hqxh.worklogproperty.R;
import hqxh.worklogproperty.model.R_PERSONS.PERSON;
import hqxh.worklogproperty.until.JsonUnit;
import hqxh.worklogproperty.widget.BaseViewHolder;


/**
 * Created by apple on 15/10/26
 * 工作日志
 */
public class PersonAdapter extends BaseQuickAdapter<PERSON> {


    public PersonAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, PERSON item) {
        helper.setText(R.id.personid_text_id, JsonUnit.convertStrToArray(item.getPERSONID())[0]);
        helper.setText(R.id.displayname_text_id, JsonUnit.convertStrToArray(item.getDISPLAYNAME())[0]);
        helper.setText(R.id.cudept_text_id, JsonUnit.convertStrToArray(item.getCUDEPT())[0]);

    }


}
