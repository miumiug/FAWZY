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
import com.hqxh.fiamproperty.model.R_BO;
import com.hqxh.fiamproperty.model.R_BO.BO;
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
 * 报销单详情
 **/
public class BoActivity extends BaseTitleActivity {
    private static final String TAG = "BoActivity";

    private ScrollView scrollView;
    /**
     * 信息展示
     **/
    private TextView bonumText; //借款单
    private TextView descriptionText; //借款事由
    private TextView typeText; //借款类型
    private TextView applycostText; //申请金额
    private TextView totalcostText; //借款金额
    private TextView statusText; //状态
    private TextView enterbyText; //借款人
    private TextView departmentText; //部门
//    private TextView crewText; //科室
    private TextView enterdateText; //借款时间


    private ImageView qtxxImageView;  //其它信息
    private LinearLayout qtxxLinearLayout;  //其它信息
    private View qtxxView;  //其它信息
    private TextView contractname_text;//合同名称
    private TextView contractnumText; //合同
    private TextView prnumText; //采购申请
    private TextView projectidText; //费用号
    private TextView payvendorText; //收款单位
    private TextView address1Text; //收款单位地址
    private TextView bankText; //开户银行
    private TextView bankaccountText; //银行账号


    private ImageView sqjlImageView; //审批记录

    private Button workflowBtn;
    private RelativeLayout workflowRelativeLayout;//布局

    private BO bo;

    private Animation rotate;

    private int mark = 0;//跳转标识
    private String appid; //appid
    private String ownernum;//ownernum
    private String ownertable;//ownertable

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("bo")) {
            bo = (BO) getIntent().getSerializableExtra("bo");
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
        return R.layout.activity_bo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        scrollView = (ScrollView) findViewById(R.id.scrollView_id);
        bonumText = (TextView) findViewById(R.id.bonum_text_id);
        descriptionText = (TextView) findViewById(R.id.description_text_id);
        typeText = (TextView) findViewById(R.id.type_text_id);
        applycostText = (TextView) findViewById(R.id.applycost_text_id);
        totalcostText = (TextView) findViewById(R.id.bocost_text_id);
        statusText = (TextView) findViewById(R.id.status_text_id);
        enterbyText = (TextView) findViewById(R.id.jk_enterby_text_id);
        departmentText = (TextView) findViewById(R.id.department_text_id);
//        crewText = (TextView) findViewById(R.id.cucrew_text_id);
        enterdateText = (TextView) findViewById(R.id.enterdate_text_id);
        contractname_text=(TextView)findViewById(R.id.contractname_text);
        qtxxImageView = (ImageView) findViewById(R.id.jbxx_kz_imageview_id);
        qtxxLinearLayout = (LinearLayout) findViewById(R.id.linearLayout_id);
        qtxxView = (View) findViewById(R.id.jbxx_view_id);

        contractnumText = (TextView) findViewById(R.id.contractnum_text_id);
        prnumText = (TextView) findViewById(R.id.prnum_text_id);
        projectidText = (TextView) findViewById(R.id.projectid_text_id);
        payvendorText = (TextView) findViewById(R.id.payvendor_text_id);
        address1Text = (TextView) findViewById(R.id.address1_text_id);
        bankText = (TextView) findViewById(R.id.bank_text_id);
        bankaccountText = (TextView) findViewById(R.id.bankaccount_text_id);

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
            getNetWorkBo();
        }
    }


    //展示界面数据
    private void showData() {
        bonumText.setText(JsonUnit.convertStrToArray(bo.getBONUM())[0]);
        descriptionText.setText(JsonUnit.convertStrToArray(bo.getDESCRIPTION())[0]);
        typeText.setText(JsonUnit.convertStrToArray(bo.getTYPE())[0]);
        applycostText.setText(JsonUnit.convertStrToArray(bo.getAPPLYCOST())[0]);
        totalcostText.setText(JsonUnit.convertStrToArray(bo.getTOTALCOST())[0]);
        statusText.setText(JsonUnit.convertStrToArray(bo.getSTATUSDESC())[0]);
        enterbyText.setText(JsonUnit.convertStrToArray(bo.getENTERBY())[0]);
        departmentText.setText(JsonUnit.convertStrToArray(bo.getDEPARTMENT())[0]);
//        crewText.setText(JsonUnit.convertStrToArray(bo.getCREW())[0]);
        enterdateText.setText(JsonUnit.strToDateString(JsonUnit.convertStrToArray(bo.getENTERDATE())[0]));

        contractnumText.setText(JsonUnit.convertStrToArray(bo.getCONTRACTNUM())[0]);
        contractname_text.setText(JsonUnit.convertStrToArray(bo.getCONTRACTDESCR())[0]);
        if (!JsonUnit.convertStrToArray(bo.getPRNUM())[0].isEmpty()) {
            prnumText.setText(JsonUnit.convertStrToArray(bo.getPRNUM())[0] + "," + JsonUnit.convertStrToArray(bo.getPRDESC())[0]);

        }
        if (!JsonUnit.convertStrToArray(bo.getPROJECTID())[0].isEmpty()) {
            projectidText.setText(JsonUnit.convertStrToArray(bo.getPROJECTID())[0] + "," + JsonUnit.convertStrToArray(bo.getFINCNTRLDESC())[0]);
        }
        payvendorText.setText(JsonUnit.convertStrToArray(bo.getPAYVENDOR())[0]);
        address1Text.setText(JsonUnit.convertStrToArray(bo.getADDRESS1())[0]);
        bankText.setText(JsonUnit.convertStrToArray(bo.getBANK())[0]);
        bankaccountText.setText(JsonUnit.convertStrToArray(bo.getBANKACCOUNT())[0]);

        rotate = AnimationUtils.loadAnimation(this, R.anim.arrow_rotate);//创建动画

        qtxxImageView.setOnClickListener(jbxxImageViewOnClickListener);


        sqjlImageView.setOnClickListener(sqjlImageViewOnClickListener);


        workflowBtn.setOnClickListener(workflowBtnOnClickListener);


    }

    @Override
    protected String getSubTitle() {
        return "FiAM > " + getString(R.string.jkdxq_text);
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


    //审批记录
    private View.OnClickListener sqjlImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(BoActivity.this, WftransactionActivity.class);
            intent.putExtra("ownertable", GlobalConfig.EXPENSE_NAME);
            intent.putExtra("ownerid", JsonUnit.convertStrToArray(bo.getBOID())[0]);
            startActivityForResult(intent, 0);
        }
    };


    //审批
    private View.OnClickListener workflowBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PostStart(ownertable, JsonUnit.convertStrToArray(bo.getBOID())[0], appid, AccountUtils.getpersonId(BoActivity.this));
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
                            showMiddleToast(BoActivity.this, workflow.getErrmsg());
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        showMiddleToast(BoActivity.this, getString(R.string.spsb_text));
                    }
                });
    }


    //http://10.60.12.98/maximo/mobile/wf/approve?ownertable=GR&ownerid=77128&memo=驳回&selectWhat=0&userid=zhuyinan
    private void PostApprove(String ownertable, String ownerid, String memo, String selectWhat, String userid) {
        Log.e(TAG, "ownertable=" + ownertable + ",ownerid=" + ownerid + ",memo=" + memo + ",selectWhat=" + selectWhat + ",userid=" + userid);

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
                        Log.e(TAG, "s=" + s);
                        R_WORKFLOW workflow = new Gson().fromJson(s, R_WORKFLOW.class);
                        showMiddleToast(BoActivity.this, workflow.getErrmsg());
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        showMiddleToast(BoActivity.this, getString(R.string.spsb_text));
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
                        PostApprove(ownertable, JsonUnit.convertStrToArray(bo.getBOID())[0], memo, result.getIspositive(), AccountUtils.getpersonId(BoActivity.this));
                    }


                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    //根据appid,grnum,objctname获取国内出差信息
    private void getNetWorkBo() {

        String data = HttpManager.getBoUrl(appid, ownernum, AccountUtils.getpersonId(BoActivity.this), 1, 10);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_BO.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_BO>() {
                    @Override
                    public void accept(@NonNull R_BO r_bo) throws Exception {
                    }
                })

                .map(new Function<R_BO, R_BO.ResultBean>() {
                    @Override
                    public R_BO.ResultBean apply(@NonNull R_BO r_bo) throws Exception {

                        return r_bo.getResult();
                    }
                })
                .map(new Function<R_BO.ResultBean, List<BO>>() {
                    @Override
                    public List<BO> apply(@NonNull R_BO.ResultBean resultBean) throws Exception {
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BO>>() {
                    @Override
                    public void accept(@NonNull List<BO> bos) throws Exception {
                        dismissLoadingDialog();
                        if (bos == null || bos.isEmpty()) {
                        } else {
                            bo = bos.get(0);
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
