package com.hqxh.fiamproperty.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.bean.R_APPROVE.Result;

import java.util.HashMap;
import java.util.List;


/**
 * Created by apple on 17/08/14
 * 审批结果集
 */
public class ApprovalAdapter extends BaseAdapter {

    private Context context;
    private List<Result> results;
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();//用于记录每个RadioButton的状态，并保证只可选一个


    /**
     * 点击事件*
     */
    public cOnClickListener onclicklistener;

    public ApprovalAdapter(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
        setStates(results.size());

    }

    @Override
    public int getCount() {
        return null == results ? 0 : results.size();
    }

    @Override
    public Result getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_approval, null);
            holder = new ViewHolder();
            holder.rdBtn = (RadioButton) convertView.findViewById(R.id.radio_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.rdBtn.setText(getItem(position).getInstruction());
        //当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
        holder.rdBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                states.put(String.valueOf(position), holder.rdBtn.isChecked());
                onclicklistener.cOnClickListener(position);
                notifyDataSetChanged();
            }
        });

        holder.rdBtn.setChecked(states.get(String.valueOf(position)));

        return convertView;
    }

    static class ViewHolder {
        RadioButton rdBtn;
    }


    //定义需要选者的值
    private void setStates(int size) {
        states.put(String.valueOf(0), true);
        for (int i = 1; i < size; i++) {

            states.put(String.valueOf(i), false);
        }
    }


    public interface cOnClickListener {
        public void cOnClickListener(int postion);
    }


    public cOnClickListener getOnclicklistener() {
        return onclicklistener;
    }

    public void setOnclicklistener(cOnClickListener onclicklistener) {
        this.onclicklistener = onclicklistener;
    }
}
