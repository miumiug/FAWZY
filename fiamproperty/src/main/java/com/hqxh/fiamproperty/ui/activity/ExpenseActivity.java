package com.hqxh.fiamproperty.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.hqxh.fiamproperty.model.R_EXPENSE;
import com.hqxh.fiamproperty.model.R_EXPENSE.EXPENSE;
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
 * 差旅报销明细
 **/

public class ExpenseActivity extends BaseTitleActivity {
    private static final String TAG = "ExpenseActivity";

    private ScrollView scrollView;
    /**
     * 信息展示
     **/
    private TextView expensenumText; //报销单
    private TextView descriptionText; //报销事由
    private TextView wftypeText; //类别
    private TextView statusText; //状态
    private TextView wonum2Text; //出差申请
    private TextView projectidText; //项目预算
    private TextView totalcostText; //报销金额
    private TextView bocostText; //借款金额


    private ImageView qtxxImageView;  //其它信息
    private LinearLayout qtxxLinearLayout;  //其它信息
    private View qtxxView;  //其它信息

    private TextView enterbyText; //报销人
    private TextView departmentText; //部门
//    private TextView crewText; //科室
    //出差时间
    private TextView actstartText; //开始日期
    private TextView actfinishText; //结束日期
    private TextView actdaysText; //出差天数

    private ImageView ccrImageView; //出差人
    private ImageView bzmxImageView; //补助明细
    private ImageView jtfyImageView; //交通费明细
    private ImageView jkdImageView; //借款单
    private ImageView sqjlImageView; //审批记录

    private Button workflowBtn;
    private RelativeLayout workflowRelativeLayout;//布局

    private EXPENSE expense;

    private Animation rotate;

    private int mark = 0;//跳转标识
    private String appid; //appid
    private String ownernum;//ownernum
    private String ownertable;//ownertable

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("expense")) {
            expense = (EXPENSE) getIntent().getSerializableExtra("expense");
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
        return R.layout.activity_expense;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        scrollView = (ScrollView) findViewById(R.id.scrollView_id);
        expensenumText = (TextView) findViewById(R.id.expensenum_text_id);
        descriptionText = (TextView) findViewById(R.id.description_bx_text_id);
        wftypeText = (TextView) findViewById(R.id.wftype_text_id);
        statusText = (TextView) findViewById(R.id.status_text_id);
        wonum2Text = (TextView) findViewById(R.id.wonum2_text_id);
        projectidText = (TextView) findViewById(R.id.projectid_text_id);
        totalcostText = (TextView) findViewById(R.id.totalcost_text_id);
        bocostText = (TextView) findViewById(R.id.bocost_text_id);

        qtxxImageView = (ImageView) findViewById(R.id.jbxx_kz_imageview_id);
        qtxxLinearLayout = (LinearLayout) findViewById(R.id.linearLayout_id);
        qtxxView = (View) findViewById(R.id.jbxx_view_id);

        enterbyText = (TextView) findViewById(R.id.enterby_text_id);
        departmentText = (TextView) findViewById(R.id.cudept_text_id);
//        crewText = (TextView) findViewById(R.id.cucrew_text_id);
        actstartText = (TextView) findViewById(R.id.actstart_text_id);
        actfinishText = (TextView) findViewById(R.id.actfinish_text_id);
        actdaysText = (TextView) findViewById(R.id.actdays_text_id);

        ccrImageView = (ImageView) findViewById(R.id.ccr_image_id);
        bzmxImageView = (ImageView) findViewById(R.id.bzmx_imageview_id);
        jtfyImageView = (ImageView) findViewById(R.id.jtmx_text_id);
        jkdImageView = (ImageView) findViewById(R.id.jkd_imageview_id);
        sqjlImageView = (ImageView) findViewById(R.id.sqjl_imageview_id);

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
            getNetWorkEXPENSE();
        }
    }


    //展示界面数据
    private void showData() {
        expensenumText.setText(JsonUnit.convertStrToArray(expense.getEXPENSENUM())[0]);
        descriptionText.setText(JsonUnit.convertStrToArray(expense.getDESCRIPTION())[0]);
        wftypeText.setText(JsonUnit.convertStrToArray(expense.getWFTYPE())[0]);
        statusText.setText(JsonUnit.convertStrToArray(expense.getSTATUSDESC())[0]);
        wonum2Text.setText(JsonUnit.convertStrToArray(expense.getWONUM2())[0]);
        projectidText.setText(JsonUnit.convertStrToArray(expense.getPROJECTID())[0] + JsonUnit.convertStrToArray(expense.getFINCNTRLDESC())[0]);
        totalcostText.setText(JsonUnit.convertStrToArray(expense.getTOTALCOST())[0]);
        bocostText.setText(JsonUnit.convertStrToArray(expense.getBOCOST())[0]);

        enterbyText.setText(JsonUnit.convertStrToArray(expense.getENTERBY())[0]);
        departmentText.setText(JsonUnit.convertStrToArray(expense.getDEPARTMENT())[0]);
//        crewText.setText(JsonUnit.convertStrToArray(expense.getCREW())[0]);
        actstartText.setText(JsonUnit.strToDateString(JsonUnit.convertStrToArray(expense.getACTSTART())[0]));
        actfinishText.setText(JsonUnit.strToDateString(JsonUnit.convertStrToArray(expense.getACTFINISH())[0]));
        actdaysText.setText(JsonUnit.convertStrToArray(expense.getACTDAYS())[0]);

        rotate = AnimationUtils.loadAnimation(this, R.anim.arrow_rotate);//创建动画

        qtxxImageView.setOnClickListener(jbxxImageViewOnClickListener);


        ccrImageView.setOnClickListener(ccrImageViewOnClickListener);
        bzmxImageView.setOnClickListener(bzmxImageViewOnClickListener);
        jtfyImageView.setOnClickListener(jtfyImageViewOnClickListener);
        jkdImageView.setOnClickListener(jkdImageViewOnClickListener);
        sqjlImageView.setOnClickListener(sqjlImageViewOnClickListener);


        workflowBtn.setOnClickListener(workflowBtnOnClickListener);


    }

    @Override
    protected String getSubTitle() {
        return getString(R.string.clbxdxq_text);
    }


    //基本信息
    private View.OnClickListener jbxxImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (startAnaim()) {
                qtxxLinearLayout.setVisibility(View.GONE);
                qtxxView.setVisibility(View.GONE);
            } else {
                qtxxLinearLayout.setVisibility(View.VISIBLE);
                qtxxView.setVisibility(View.VISIBLE);
            }

        }
    };


    //启动动画
    private boolean startAnaim() {

        rotate.setInterpolator(new LinearInterpolator());//设置为线性旋转

        rotate.setFillAfter(!rotate.getFillAfter());//每次都取相反值，使得可以不恢复原位的旋转

        qtxxImageView.startAnimation(rotate);
        return rotate.getFillAfter();
    }

    //出差人
    private View.OnClickListener ccrImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ExpenseActivity.this, PersonrelationActivity.class);
            intent.putExtra("appid", GlobalConfig.EXPENSES_APPID);
            intent.putExtra("wonum", JsonUnit.convertStrToArray(expense.getEXPENSENUM())[0]);
            intent.putExtra("title", getResources().getString(R.string.ccr_text));
            startActivityForResult(intent, 0);
        }
    };
    //补助明细
    private View.OnClickListener bzmxImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;
            if (JsonUnit.convertStrToArray(expense.getWFTYPE())[0].indexOf("2.4") != -1) {
                Log.e(TAG,"存在");
                intent = new Intent(ExpenseActivity.this, OuttestineActivity.class);
            } else {
                Log.e(TAG,"不存在");
                intent = new Intent(ExpenseActivity.this, SubsidiesActivity.class);
            }

            intent.putExtra("expensenum", JsonUnit.convertStrToArray(expense.getEXPENSENUM())[0]);
            startActivityForResult(intent, 0);
        }
    };
    //交通补助
    private View.OnClickListener jtfyImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ExpenseActivity.this, ExpenselineActivity.class);
            intent.putExtra("expensenum", JsonUnit.convertStrToArray(expense.getEXPENSENUM())[0]);
            intent.putExtra("appid", GlobalConfig.EXPENSES_APPID);
            startActivityForResult(intent, 0);
        }
    };
    //借款单
    private View.OnClickListener jkdImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ExpenseActivity.this, BorelationActivity.class);
            intent.putExtra("expensenum", JsonUnit.convertStrToArray(expense.getEXPENSENUM())[0]);
            intent.putExtra("appid", GlobalConfig.EXPENSES_APPID);
            startActivityForResult(intent, 0);
        }
    };

    //审批记录
    private View.OnClickListener sqjlImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ExpenseActivity.this, WftransactionActivity.class);
            intent.putExtra("ownertable", GlobalConfig.EXPENSE_NAME);
            intent.putExtra("ownerid", JsonUnit.convertStrToArray(expense.getEXPENSEID())[0]);
            startActivityForResult(intent, 0);
        }
    };


    //审批
    private View.OnClickListener workflowBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PostStart(ownertable, JsonUnit.convertStrToArray(expense.getEXPENSEID())[0], appid, AccountUtils.getpersonId(ExpenseActivity.this));
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
                            showMiddleToast(ExpenseActivity.this, workflow.getErrmsg());
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        showMiddleToast(ExpenseActivity.this, getString(R.string.spsb_text));
                    }
                });
    }


    private void PostApprove(String ownertable, String ownerid, String memo, String selectWhat, String userid) {

        Rx2AndroidNetworking
                .post(GlobalConfig.HTTP_URL_APPROVE_WORKFLOW)
                .addBodyParameter("ownertable", ownertable)
                .addBodyParameter("ownerid", ownerid)
                .addBodyParameter("memo", memo)
                .addBodyParameter("selectWhat", selectWhat)
                .addBodyParameter("userid", userid)
                .build()
                .getStringObservable()
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
                        showMiddleToast(ExpenseActivity.this, workflow.getErrmsg());
                        if (workflow.getErrcode().equals(GlobalConfig.WORKFLOW_103)) {
                            setResult(ActiveTaskActivity.TASK_RESULTCODE);
                            finish();
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        showMiddleToast(ExpenseActivity.this, getString(R.string.spsb_text));
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
                        PostApprove(ownertable, JsonUnit.convertStrToArray(expense.getEXPENSEID())[0], memo, result.getIspositive(), AccountUtils.getpersonId(ExpenseActivity.this));
                    }


                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    //根据appid,grnum,objctname获取国内出差信息
    private void getNetWorkEXPENSE() {

        String data = HttpManager.getEXPENSEUrl(appid, ownernum, AccountUtils.getpersonId(ExpenseActivity.this), 1, 10);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_EXPENSE.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_EXPENSE>() {
                    @Override
                    public void accept(@NonNull R_EXPENSE r_expense) throws Exception {
                    }
                })

                .map(new Function<R_EXPENSE, R_EXPENSE.ResultBean>() {
                    @Override
                    public R_EXPENSE.ResultBean apply(@NonNull R_EXPENSE r_expense) throws Exception {

                        return r_expense.getResult();
                    }
                })
                .map(new Function<R_EXPENSE.ResultBean, List<EXPENSE>>() {
                    @Override
                    public List<EXPENSE> apply(@NonNull R_EXPENSE.ResultBean resultBean) throws Exception {
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<EXPENSE>>() {
                    @Override
                    public void accept(@NonNull List<EXPENSE> expenses) throws Exception {
                        dismissLoadingDialog();
                        if (expenses == null || expenses.isEmpty()) {
                        } else {

                            expense = expenses.get(0);
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
