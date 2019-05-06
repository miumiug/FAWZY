package com.hqxh.fiamproperty.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseTitleActivity;
import com.hqxh.fiamproperty.bean.R_APPROVE;
import com.hqxh.fiamproperty.bean.R_WORKFLOW;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_PAYPLAN;
import com.hqxh.fiamproperty.model.R_PAYPLAN.PAYPLAN;
import com.hqxh.fiamproperty.ui.widget.ConfirmDialog;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.hqxh.fiamproperty.unit.JsonUnit;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 需款计划详情
 */

public class XkplandetailActivity extends BaseTitleActivity {
    private static final String TAG = "XkplandetailActivity";

    private PAYPLAN payplan;
    private Animation rotate;

    private ScrollView scrollView;

    TextView payplannum_text;//计划单
    TextView description_text;//描述
    TextView month_text;//日期
    TextView type1_text;//需款类型
    TextView totalcost1_text;//需款金额
    TextView status_text;//状态
    TextView contractnum1_text;//合同
    TextView description2_text;//合同描述

    TextView phase_text;//付款阶段
    TextView vendor_text;//供应商
    TextView wonum2_text;//出差申请

    CheckBox isbopayplan_text;//借款需款？

    TextView sqr_text;//申请人
    TextView enterdate_text;//申请时间
    TextView cudept_text;//部门
    TextView cucrew_text;//科室
    LinearLayout jbxxLinearlayout;

    ImageView jbxx_text;//其它信息
    ImageView xkxmImageView;//需款项目
    TextView xkxmText; //需款标题
    ImageView spjl_text;//审批记录

    private Button workflowBtn;
    private RelativeLayout workflowRelativeLayout;//布局

    private int mark = 0;//跳转标识
    private String appid; //appid
    private String ownernum;//ownernum
    private String ownertable;//ownertable

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("payplan")) {
            payplan = (PAYPLAN) getIntent().getExtras().getSerializable("payplan");
        }


        if (getIntent().hasExtra("mark")) {
            mark = getIntent().getExtras().getInt("mark");
        }
        if (getIntent().hasExtra("appid")) {
            appid = getIntent().getExtras().getString("appid");
        }
        if (getIntent().hasExtra("ownernum")) {
            ownernum = getIntent().getExtras().getString("ownernum");
        }
        if (getIntent().hasExtra("ownertable")) {
            ownertable = getIntent().getExtras().getString("ownertable");
        }

    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.xkplandetails;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        scrollView = (ScrollView) findViewById(R.id.scrollView_id);
        payplannum_text = (TextView) findViewById(R.id.payplannum_text);
        description_text = (TextView) findViewById(R.id.description_text_id);
        month_text = (TextView) findViewById(R.id.month_text);
        type1_text = (TextView) findViewById(R.id.type1_text);

        totalcost1_text = (TextView) findViewById(R.id.totalcost1_text);
        status_text = (TextView) findViewById(R.id.status_text);
        contractnum1_text = (TextView) findViewById(R.id.contractnum1_text);
        description2_text = (TextView) findViewById(R.id.description2_text);
        phase_text = (TextView) findViewById(R.id.phase_text);
        vendor_text = (TextView) findViewById(R.id.vendor_text);
        wonum2_text = (TextView) findViewById(R.id.wonum2_text);
        cudept_text = (TextView) findViewById(R.id.cudept_text);
        cucrew_text = (TextView) findViewById(R.id.cucrew_text);


        isbopayplan_text = (CheckBox) findViewById(R.id.isbopayplan_text);

        sqr_text = (TextView) findViewById(R.id.sqr_text);
        enterdate_text = (TextView) findViewById(R.id.enterdate_text);

        jbxxLinearlayout = (LinearLayout) findViewById(R.id.jbxx_text_id);

        jbxx_text = (ImageView) findViewById(R.id.jbxx_text);
        xkxmText = (TextView) findViewById(R.id.xumx_name_id);
        xkxmImageView = (ImageView) findViewById(R.id.xkxm_text);
        spjl_text = (ImageView) findViewById(R.id.spjl_text);

        workflowBtn = (Button) findViewById(R.id.workflow_btn_id);

        workflowRelativeLayout = (RelativeLayout) findViewById(R.id.relavtivelayout_btn_id);
        if (null == appid) {
            showData();
        } else {
            if (mark == HomeActivity.DB_CODE) { //待办任务
                workflowRelativeLayout.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(0, 0, 0, getHeight(workflowRelativeLayout));//4个参数按顺序分别是左上右下
                scrollView.setLayoutParams(layoutParams);
            }
            showLoadingDialog(getResources().getString(R.string.loading_hint));
            getNetWorkPAYPLAN();
        }

    }


    private void showData() {
        payplannum_text.setText(JsonUnit.convertStrToArray(payplan.getPAYPLANNUM())[0]);
        description_text.setText(JsonUnit.convertStrToArray(payplan.getDESCRIPTION())[0]);
        month_text.setText(JsonUnit.convertStrToArray(payplan.getMONTH())[0]);
        type1_text.setText(JsonUnit.convertStrToArray(payplan.getTYPE())[0]);
        totalcost1_text.setText(JsonUnit.convertStrToArray(payplan.getTOTALCOST())[0]);
        status_text.setText(JsonUnit.convertStrToArray(payplan.getSTATUSDESC())[0]);
        contractnum1_text.setText(JsonUnit.convertStrToArray(payplan.getCONTRACTNUM())[0]);
        description2_text.setText(JsonUnit.convertStrToArray(payplan.getCONTRACTDESC())[0]);
        phase_text.setText(JsonUnit.convertStrToArray(payplan.getPHASE())[0]);
        vendor_text.setText(JsonUnit.convertStrToArray(payplan.getVENDORNAME())[0]);
        wonum2_text.setText(JsonUnit.convertStrToArray(payplan.getWONUM2())[0]);
        cudept_text.setText(JsonUnit.convertStrToArray(payplan.getDEPARTMENT())[0]);
        cucrew_text.setText(JsonUnit.convertStrToArray(payplan.getCREW())[0]);


        if (JsonUnit.convertStrToArray(payplan.getISBOPAYPLAN())[0].equals("1")) {
            isbopayplan_text.setChecked(true);
        } else {
            isbopayplan_text.setChecked(false);
        }
        sqr_text.setText(JsonUnit.convertStrToArray(payplan.getENTERBYNAME())[0]);
        enterdate_text.setText(JsonUnit.convertStrToArray(payplan.getENTERDATE())[0]);

        rotate = AnimationUtils.loadAnimation(this, R.anim.arrow_rotate);//创建动画
        if (JsonUnit.convertStrToArray(payplan.getTYPE())[0].equals("3.部门需款汇总")) {
            xkxmText.setText(R.string.zxkjh_text);
        }
        jbxx_text.setOnClickListener(jbxx_textOnClickListener);
        spjl_text.setOnClickListener(sqjlImageViewOnClickListener);
        xkxmImageView.setOnClickListener(xkxm_textOnClickListener);
        workflowBtn.setOnClickListener(sp_btn_idOnClickListener);
    }

    @Override
    protected String getSubTitle() {
        return getString(R.string.xkjh_text);
    }


    //需款项目
    private View.OnClickListener xkxm_textOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            if (JsonUnit.convertStrToArray(payplan.getTYPE())[0].equals("3.部门需款汇总")) {
                intent = new Intent(XkplandetailActivity.this, ZxkjhActivity.class);
                intent.putExtra("payplannum", JsonUnit.convertStrToArray(payplan.getPAYPLANNUM())[0]);
                intent.putExtra("title", getResources().getString(R.string.zxkjh_text));
                intent.putExtra("appid", GlobalConfig.PPCHANGE_APPID);
                startActivityForResult(intent, 0);
            } else {
                intent = new Intent(XkplandetailActivity.this, XkxmActivity.class);
                intent.putExtra("payplannum", JsonUnit.convertStrToArray(payplan.getPAYPLANNUM())[0]);
                intent.putExtra("title", getResources().getString(R.string.xkxm_text));
                intent.putExtra("appid", GlobalConfig.PP_APPID);
                startActivityForResult(intent, 0);
            }

        }
    };

    //审批记录
    private View.OnClickListener sqjlImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(XkplandetailActivity.this, WftransactionActivity.class);
            intent.putExtra("ownertable", GlobalConfig.PAYPLAN_NAME);
            intent.putExtra("ownerid", JsonUnit.convertStrToArray(payplan.getPAYPLANID())[0]);
            startActivityForResult(intent, 0);
        }
    };
    /*
    其它信息
    */
    private View.OnClickListener jbxx_textOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (startAnaim()) {
                jbxxLinearlayout.setVisibility(View.GONE);
            } else {
                jbxxLinearlayout.setVisibility(View.VISIBLE);
            }

        }
    };

    //启动动画
    private boolean startAnaim() {

        rotate.setInterpolator(new LinearInterpolator());//设置为线性旋转
        rotate.setFillAfter(!rotate.getFillAfter());//每次都取相反值，使得可以不恢复原位的旋转

        jbxx_text.startAnimation(rotate);
        return rotate.getFillAfter();
    }

    //审批
    private View.OnClickListener sp_btn_idOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PostStart(ownertable, JsonUnit.convertStrToArray(payplan.getPAYPLANID())[0], appid, AccountUtils.getpersonId(XkplandetailActivity.this));
        }
    };


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


                        R_WORKFLOW workflow = new Gson().fromJson(s, R_WORKFLOW.class);
                        if (workflow.getErrcode().equals(GlobalConfig.WORKFLOW_106)) {
                            R_APPROVE r_approve = new Gson().fromJson(s, R_APPROVE.class);
                            showDialog(r_approve.getResult());
                        } else {
                            showMiddleToast(XkplandetailActivity.this, workflow.getErrmsg());
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        showMiddleToast(XkplandetailActivity.this, getString(R.string.spsb_text));
                    }
                });
    }


    private void PostApprove(String ownertable, String ownerid, String memo, String selectWhat, String userid) {
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_APPROVE_WORKFLOW)
                .addBodyParameter("ownertable", ownertable)
                .addBodyParameter("ownerid", ownerid)
                .addBodyParameter("memo", memo)
                .addBodyParameter("selectWhat", selectWhat)
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
                        R_WORKFLOW workflow = new Gson().fromJson(s, R_WORKFLOW.class);
                        showMiddleToast(XkplandetailActivity.this, workflow.getErrmsg());
                        if (workflow.getErrcode().equals(GlobalConfig.WORKFLOW_103)) {
                            setResult(ActiveTaskActivity.TASK_RESULTCODE);
                            finish();
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        showMiddleToast(XkplandetailActivity.this, getString(R.string.spsb_text));
                    }
                });
    }


    //弹出对话框
    public void showDialog(List<R_APPROVE.Result> results) {//

        ConfirmDialog.Builder dialog = new ConfirmDialog.Builder(this);
        dialog.setTitle("审批")
                .setData(results)
                .setPositiveButton("确定", new ConfirmDialog.Builder.cOnClickListener() {
                    @Override
                    public void cOnClickListener(DialogInterface dialogInterface, R_APPROVE.Result result, String memo) {
                        dialogInterface.dismiss();
                        PostApprove(ownertable, JsonUnit.convertStrToArray(payplan.getPAYPLANID())[0], memo, result.getIspositive(), AccountUtils.getpersonId(XkplandetailActivity.this));
                    }


                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    //根据appid,grnum,objctname获取国内出差信息
    private void getNetWorkPAYPLAN() {
        String data = HttpManager.getPAYPLANUrl(appid, ownernum, AccountUtils.getpersonId(this), 1, 10);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_PAYPLAN.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_PAYPLAN>() {
                    @Override
                    public void accept(@NonNull R_PAYPLAN r_payplan) throws Exception {
                    }
                })

                .map(new Function<R_PAYPLAN, R_PAYPLAN.ResultBean>() {
                    @Override
                    public R_PAYPLAN.ResultBean apply(@NonNull R_PAYPLAN r_payplan) throws Exception {

                        return r_payplan.getResult();
                    }
                })
                .map(new Function<R_PAYPLAN.ResultBean, List<PAYPLAN>>() {
                    @Override
                    public List<PAYPLAN> apply(@NonNull R_PAYPLAN.ResultBean resultBean) throws Exception {
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PAYPLAN>>() {
                    @Override
                    public void accept(@NonNull List<PAYPLAN> payplans) throws Exception {
                        dismissLoadingDialog();
                        if (payplans == null || payplans.isEmpty()) {
                        } else {
                            payplan = payplans.get(0);
                            showData();

                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadingDialog();
                    }
                });
    }
}
