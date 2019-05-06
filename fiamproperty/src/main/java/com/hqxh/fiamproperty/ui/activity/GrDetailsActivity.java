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
import com.hqxh.fiamproperty.model.R_GR;
import com.hqxh.fiamproperty.model.R_GR.GR;
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
//物资出门详情

public class GrDetailsActivity extends BaseTitleActivity {
    private static String TAG = "GrDetailsActivity ";

    private ScrollView scrollView;
    TextView grnum_text; //编号
    TextView descritionText;//描述
    TextView location_text;//门岗1
    TextView location2_text;//门岗2
    TextView reason_text;//理由
    TextView type_text;//类型
    TextView schedulardate_text;//计划出门日期

    private ImageView jbxx_text;//其它信息
    private LinearLayout jbxxLinearLayout; //其它信息

    TextView displayname_text;//申请人
    TextView phone_text;//电话
    TextView cudept_text;//部门
    TextView cucrew_text;//科室
    TextView description_text;//状态
    TextView enterdate_text;//申请日期

    ImageView wzmx_text;//物资明细
    ImageView zcmx_text;//整车明细
    RelativeLayout zcmx_relativeLayout; //整车明细布局
    View zcmxView; //整车明细布局
    ImageView spjl_text;//审批记录

    private Button workflowBtn;//审批按钮
    private RelativeLayout workflowRelativeLayout;//布局

    private Animation rotate;


    private GR gr; //对象

    private int mark = 0;//跳转标识
    private String appid; //appid
    private String ownernum;//ownernum
    private String ownertable;//ownertable


    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("gr")) {
            gr = (GR) getIntent().getExtras().getSerializable("gr");
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
        return R.layout.activity_grdetails;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        scrollView = (ScrollView) findViewById(R.id.scrollView_id);
        grnum_text = (TextView) findViewById(R.id.grnum_text);
        descritionText = (TextView) findViewById(R.id.description_text_id);

        location_text = (TextView) findViewById(R.id.location_text);
        location2_text = (TextView) findViewById(R.id.location2_text);
        reason_text = (TextView) findViewById(R.id.reason_text);
        type_text = (TextView) findViewById(R.id.type_text);
        schedulardate_text = (TextView) findViewById(R.id.schedulardate_text);


        jbxx_text = (ImageView) findViewById(R.id.jbxx_text);
        jbxxLinearLayout = (LinearLayout) findViewById(R.id.jbxx_linearlayout_id);

        displayname_text = (TextView) findViewById(R.id.displayname_text);
        phone_text = (TextView) findViewById(R.id.phone_text);
        cudept_text = (TextView) findViewById(R.id.cudept_text);
        cucrew_text = (TextView) findViewById(R.id.cucrew_text);
        description_text = (TextView) findViewById(R.id.status_text);
        enterdate_text = (TextView) findViewById(R.id.enterdate_text);
        wzmx_text = (ImageView) findViewById(R.id.wzmx_text);
        zcmx_text = (ImageView) findViewById(R.id.zcmx_text);
        zcmx_relativeLayout = (RelativeLayout) findViewById(R.id.zcmx_relativelayout_id);
        zcmxView = (View) findViewById(R.id.zcmx_view_id);

        spjl_text = (ImageView) findViewById(R.id.spjl_text);

        workflowBtn = (Button) findViewById(R.id.workflow_btn_id);
        workflowRelativeLayout = (RelativeLayout) findViewById(R.id.relavtivelayout_btn_id);


        rotate = AnimationUtils.loadAnimation(this, R.anim.arrow_rotate);//创建动画
        if (null == appid) {
            showData();
        } else {
            if (mark == HomeActivity.DB_CODE) { //待办任务
                workflowRelativeLayout.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(0, 0, 0, getHeight(workflowRelativeLayout));//4个参数按顺序分别是左上右下
                scrollView.setLayoutParams(layoutParams);
            }
            showLoadingDialog(getString(R.string.loading_hint));
            getNetWorkGr();
        }


        setOnClickListener();
    }

    private void showData() {
        grnum_text.setText(JsonUnit.convertStrToArray(gr.getGRNUM())[0]);
        descritionText.setText(JsonUnit.convertStrToArray(gr.getDESCRIPTION())[0]);
        location_text.setText(JsonUnit.convertStrToArray(gr.getLOCATIONDESCRIPTION())[0]);
        location2_text.setText(JsonUnit.convertStrToArray(gr.getLOCATION2DESCRIPTION())[0]);
        reason_text.setText(JsonUnit.convertStrToArray(gr.getREASON())[0]);
        type_text.setText(JsonUnit.convertStrToArray(gr.getTYPE())[0]);
        schedulardate_text.setText(JsonUnit.strToDateString(JsonUnit.convertStrToArray(gr.getSCHEDULARDATE())[0]));
        displayname_text.setText(JsonUnit.convertStrToArray(gr.getENTERBY())[0]);
        phone_text.setText(JsonUnit.convertStrToArray(gr.getPHONE())[0]);
        cudept_text.setText(JsonUnit.convertStrToArray(gr.getCUDEPT())[0]);
        cucrew_text.setText(JsonUnit.convertStrToArray(gr.getCUCREW())[0]);
        description_text.setText(JsonUnit.convertStrToArray(gr.getSTATUSDESC())[0]);
        enterdate_text.setText(JsonUnit.convertStrToArray(gr.getENTERDATE())[0]);
        if (JsonUnit.convertStrToArray(gr.getTYPE())[0].equals("物资")) {
            zcmx_relativeLayout.setVisibility(View.GONE);
            zcmxView.setVisibility(View.GONE);
        }


    }


    private void setOnClickListener() {
        jbxx_text.setOnClickListener(jbxx_textOnClickListener);

        wzmx_text.setOnClickListener(wzmx_textOnClickListener);
        zcmx_text.setOnClickListener(zcmx_textOnClickListener);
        spjl_text.setOnClickListener(sqjlImageViewOnClickListener);
        workflowBtn.setOnClickListener(spOnClickListener);
    }


    private boolean startAnaim() {

        rotate.setInterpolator(new LinearInterpolator());//设置为线性旋转

        rotate.setFillAfter(!rotate.getFillAfter());//每次都取相反值，使得可以不恢复原位的旋转

        jbxx_text.startAnimation(rotate);
        return rotate.getFillAfter();
    }


    @Override
    protected String getSubTitle() {
        return getString(R.string.wzcmxq_text);
    }


    private View.OnClickListener jbxx_textOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (startAnaim()) {
                jbxxLinearLayout.setVisibility(View.GONE);
            } else {
                jbxxLinearLayout.setVisibility(View.VISIBLE);
            }
        }
    };

    /**
     * 物资明细
     **/
    private View.OnClickListener wzmx_textOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(GrDetailsActivity.this, WzmxListActivity.class);
            intent.putExtra("grnum", JsonUnit.convertStrToArray(gr.getGRNUM())[0]);
            intent.putExtra("appid", GlobalConfig.GRWZ);
            intent.putExtra("title", getResources().getString(R.string.wzmx_text));
            startActivityForResult(intent, 0);

        }
    };
    private View.OnClickListener zcmx_textOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(GrDetailsActivity.this, WzmxListActivity.class);
            intent.putExtra("grnum", JsonUnit.convertStrToArray(gr.getGRNUM())[0]);
            intent.putExtra("appid", GlobalConfig.GRZC_APPID);
            intent.putExtra("title", getResources().getString(R.string.zcmx_text));
            startActivityForResult(intent, 0);

        }
    };
    //审批记录
    private View.OnClickListener sqjlImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(GrDetailsActivity.this, WftransactionActivity.class);
            intent.putExtra("ownertable", GlobalConfig.GR_NAME);
            intent.putExtra("ownerid", JsonUnit.convertStrToArray(gr.getGRID())[0]);
            startActivityForResult(intent, 0);
        }
    };


    //审批
    private View.OnClickListener spOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            PostStart(GlobalConfig.GR_NAME, JsonUnit.convertStrToArray(gr.getGRID())[0], GlobalConfig.GRWZ_APPID, AccountUtils.getpersonId(GrDetailsActivity.this));
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
                            for (int i = 0; i < r_approve.getResult().size(); i++) {
                                R_APPROVE.Result result = r_approve.getResult().get(i);
                            }

                            showDialog(r_approve.getResult());
                        } else {
                            showMiddleToast(GrDetailsActivity.this, workflow.getErrmsg());
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        showMiddleToast(GrDetailsActivity.this, getString(R.string.spsb_text));
                    }
                });
    }

/*审批流程*/

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
                        showMiddleToast(GrDetailsActivity.this, workflow.getErrmsg());
                        if (workflow.getErrcode().equals(GlobalConfig.WORKFLOW_103)) {
                            setResult(ActiveTaskActivity.TASK_RESULTCODE);
                            finish();
                        }

                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        showMiddleToast(GrDetailsActivity.this, getString(R.string.spsb_text));
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
                        PostApprove(ownertable, JsonUnit.convertStrToArray(gr.getGRID())[0], memo, result.getIspositive(), AccountUtils.getpersonId(GrDetailsActivity.this));
                    }


                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    //根据appid,grnum,objctname获取出门证信息
    private void getNetWorkGr() {
        String data = HttpManager.getGRUrl(appid, ownertable, ownernum, AccountUtils.getpersonId(this), 1, 20);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_GR.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_GR>() {
                    @Override
                    public void accept(@NonNull R_GR r_gr) throws Exception {
                    }
                })

                .map(new Function<R_GR, R_GR.ResultBean>() {
                    @Override
                    public R_GR.ResultBean apply(@NonNull R_GR r_gr) throws Exception {

                        return r_gr.getResult();
                    }
                })
                .map(new Function<R_GR.ResultBean, List<GR>>() {
                    @Override
                    public List<GR> apply(@NonNull R_GR.ResultBean resultBean) throws Exception {
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GR>>() {
                    @Override
                    public void accept(@NonNull List<GR> grs) throws Exception {
                        dismissLoadingDialog();
                        if (grs == null || grs.isEmpty()) {
                        } else {
                            gr = grs.get(0);
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



