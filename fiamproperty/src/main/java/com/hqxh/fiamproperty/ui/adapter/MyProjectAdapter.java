package com.hqxh.fiamproperty.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.bean.R_APPROVE;
import com.hqxh.fiamproperty.bean.R_APPROVES;
import com.hqxh.fiamproperty.bean.R_APPROVESS;
import com.hqxh.fiamproperty.bean.R_WORKFLOW;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_PR;
import com.hqxh.fiamproperty.model.R_WORKREPORT;
import com.hqxh.fiamproperty.ui.activity.ActiveTaskActivity;
import com.hqxh.fiamproperty.ui.activity.RyrwdWorkorderActivity;
import com.hqxh.fiamproperty.ui.activity.SzPrActivity;
import com.hqxh.fiamproperty.ui.widget.BaseViewHolder;
import com.hqxh.fiamproperty.ui.widget.ConfirmDialog;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.hqxh.fiamproperty.unit.JsonUnit;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.hqxh.fiamproperty.base.BaseActivity.showMiddleToast;

/**
 * Created by Administrator on 2018\6\21 0021.
 */

public class MyProjectAdapter extends BaseQuickAdapter<R_WORKREPORT.WORKREPORT> {
    Context contexts;
    boolean flag;

    private R_WORKREPORT.WORKREPORT workreport;

    public MyProjectAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
        contexts = context;
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final R_WORKREPORT.WORKREPORT item) {
        workreport = item;
        helper.setText(R.id.text_tittle, JsonUnit.convertStrToArray(item.getPROJECTDESC())[0]);
        helper.setText(R.id.text_number, JsonUnit.convertStrToArray(item.getWRNUM())[0]);
        helper.setText(R.id.text_projectnum, JsonUnit.convertStrToArray(item.getPROJECTID())[0]);
        if (JsonUnit.convertStrToArray(item.getSTATUSDESC())[0].equals("草稿")){
            flag = true;
            helper.setTextColorRes(R.id.text_submit,R.color.white);
            helper.setBackgroundRes(R.id.text_submit,R.drawable.bg_selector_no);
        }else {
            flag = false;
            helper.setTextColorRes(R.id.text_submit,R.color.text_color);
            helper.setBackgroundRes(R.id.text_submit,R.drawable.bg_selector_s);
        }
        if (JsonUnit.convertStrToArray(item.getSTATUSDESC())[0].equals("草稿")){
            helper.setTextColorRes(R.id.text_status,R.color.text_draft_color);
        }else if (JsonUnit.convertStrToArray(item.getSTATUSDESC())[0].equals("待确定")){
            helper.setTextColorRes(R.id.text_status,R.color.red);
        }else if (JsonUnit.convertStrToArray(item.getSTATUSDESC())[0].equals("已确认")){
            helper.setTextColorRes(R.id.text_status,R.color.text_back_color_2);
        }else {
            helper.setTextColorRes(R.id.text_status,R.color.btn_onclick);
        }
        helper.setText(R.id.text_status, JsonUnit.convertStrToArray(item.getSTATUSDESC())[0]);
        helper.setText(R.id.text_time, JsonUnit.convertStrToArray(item.getACTURALHOURS())[0] + "小时");
        helper.setText(R.id.text_fzr, JsonUnit.convertStrToArray(item.getSUPERVISORDESC())[0]);
        if (JsonUnit.convertStrToArray(item.getAPPBYPM())[0].equals("1")){
            helper.setChecked(R.id.managerGroupID,true);
        }else {
            helper.setChecked(R.id.managerGroupID,false);
        }
        helper.setOnClickListener(R.id.text_submit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == true){
                    PostStart("WORKREPORT", JsonUnit.convertStrToArray(item.getWORKREPORTID())[0], GlobalConfig.WRTRACK_APPID, AccountUtils.getpersonId(contexts));
                }
            }
        });
        helper.setText(R.id.text_brief, JsonUnit.convertStrToArray(item.getBRIEF())[0]);
    }

    //流程启动
    private void PostStart(String ownertable, String ownerid, String appid, String userid) {
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_START_WORKFLOW)
                .addBodyParameter("ownertable", ownertable)
                .addBodyParameter("ownerid", ownerid)
                .addBodyParameter("appid", appid)
                .addBodyParameter("userid", userid)
                .build().getStringObservable()
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String string) throws Exception {

                    }
                })


                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        if (s.contains("WF-E-101")){
                            R_APPROVES r_approve = new Gson().fromJson(s, R_APPROVES.class);
                            showMiddleToast(contexts, r_approve.getErrmsg());
                        }else {
                            R_APPROVESS r_approve = new Gson().fromJson(s, R_APPROVESS.class);
                            showMiddleToast(contexts, r_approve.getErrmsg());
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        showMiddleToast(contexts, "启动失败");
                    }
                });

    }

}

