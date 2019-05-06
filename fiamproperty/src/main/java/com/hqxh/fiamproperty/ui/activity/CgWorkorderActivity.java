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
import com.hqxh.fiamproperty.model.R_Workorder;
import com.hqxh.fiamproperty.model.R_Workorder.Workorder;
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
 * 出国立项详情
 **/
public class CgWorkorderActivity extends BaseTitleActivity {

    private static final String TAG = "CgWorkorderActivity";
    private ScrollView scrollView;
    /**
     * 信息展示
     **/
    private TextView wonumText; //申请单
    private TextView descriptionText; //描述
    private TextView projectidText; //费用号
    private TextView fincntrldescText; //项目名称
    private TextView udesttotalcostText; //出差费用预算
    private TextView statusText; //状态
    private TextView udtrv2Text; //出国团组名称
    private TextView udremark2Text; //邀请接待单位
    private TextView udremark1Text; //出国目的
    private TextView udremark4Text; //出国工作任务
    private TextView udsummary6Text; //预期成果
    private TextView udksremarkText; //途径国家或地区
    private TextView udaddress1Text; //出访路线
    //出差时间
    private TextView udtargstartdateText; //开始时间
    private TextView udtargcompdateText; //结束时间

    private TextView udtargstartdate2Text; //出国时间
    private TextView udremark3Text; //出访国家或地区
    private TextView udestdur3Text; //停留天数

    private ImageView lcgrymdText; //拟出国人员名单


    private ImageView qtxxImageView;  //其它信息
    private LinearLayout ccrLinearLayout; //其它信息
    private View qtxxView; //其它信息

    private TextView udtrv1Text; //团队负责人
    private TextView rdcheadText; //中心分管领导
    private TextView reportedbyText; //申请人
    private TextView cudeptText; //部门
    private TextView reportdateText; //申请日期
    private TextView phonenumText; //电话

    private ImageView kbfilelistImageView; //出国人员知识积累拟交付资料清单
    private ImageView doclinksImageView; //附件
    private ImageView yxmxImageView; //预算明细
    private ImageView sqjlImageView; //审批记录


    private Button workflowBtn;
    private RelativeLayout workflowRelativeLayout;//布局

    private Workorder workorder;

    private Animation rotate;

    private int mark = 0;//跳转标识
    private String appid; //appid
    private String ownernum;//ownernum
    private String ownertable;//ownertable

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("workorder")) {
            workorder = (Workorder) getIntent().getSerializableExtra("workorder");
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
        return R.layout.activity_cg_workorder;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        scrollView = (ScrollView) findViewById(R.id.scrollView_id);
        wonumText = (TextView) findViewById(R.id.requireplannum_text_id);
        descriptionText = (TextView) findViewById(R.id.description_text_id);
        projectidText = (TextView) findViewById(R.id.projectid_text_id);
        fincntrldescText = (TextView) findViewById(R.id.fincntrldesc_text_id);
        udesttotalcostText = (TextView) findViewById(R.id.udesttotalcost_text_id);
        statusText = (TextView) findViewById(R.id.status_text_id);

        udtrv2Text = (TextView) findViewById(R.id.udtrv2_text_id);
        udremark2Text = (TextView) findViewById(R.id.udremark2_text_id);
        udremark4Text = (TextView) findViewById(R.id.cg_udremark4_text_id);
        udsummary6Text = (TextView) findViewById(R.id.udsummary6_text_id);
        udksremarkText = (TextView) findViewById(R.id.udksremark_text_id);
        udaddress1Text = (TextView) findViewById(R.id.udaddress1_text_id);

        udremark1Text = (TextView) findViewById(R.id.udremark1_text_id);
        udtargstartdateText = (TextView) findViewById(R.id.udtargstartdate_text_id);
        udtargcompdateText = (TextView) findViewById(R.id.udtargcompdate_text_id);
        udtargstartdate2Text = (TextView) findViewById(R.id.udtargstartdate2_text_id);
        udremark3Text = (TextView) findViewById(R.id.udremark3_text_id);
        udestdur3Text = (TextView) findViewById(R.id.udestdur3_text_id);

        lcgrymdText = (ImageView) findViewById(R.id.lcgrymd_imageview_id);
        qtxxImageView = (ImageView) findViewById(R.id.jbxx_kz_imageview_id);
        ccrLinearLayout = (LinearLayout) findViewById(R.id.linearLayout_id);
        qtxxView = (View) findViewById(R.id.qtxx_view_id);

        udtrv1Text = (TextView) findViewById(R.id.udtrv1_text_id);
        rdcheadText = (TextView) findViewById(R.id.rdchead_text_id);
        reportedbyText = (TextView) findViewById(R.id.reportedby_text_id);
        cudeptText = (TextView) findViewById(R.id.cudept_text_id);
        reportdateText = (TextView) findViewById(R.id.reportdate_text_id);
        phonenumText = (TextView) findViewById(R.id.phonenum_text_id);

        kbfilelistImageView = (ImageView) findViewById(R.id.kbfilelist_imageview_id);
        doclinksImageView = (ImageView) findViewById(R.id.doclinks_imageview_id);
        yxmxImageView = (ImageView) findViewById(R.id.yxmx_imageview_id);
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
            getNetWorkWorkOrder();
        }
    }


    //展示界面数据
    private void showData() {
        wonumText.setText(JsonUnit.convertStrToArray(workorder.getWONUM())[0]);
        descriptionText.setText(JsonUnit.convertStrToArray(workorder.getDESCRIPTION())[0]);

        projectidText.setText(JsonUnit.convertStrToArray(workorder.getPROJECTID())[0]);
        fincntrldescText.setText(JsonUnit.convertStrToArray(workorder.getFINCNTRLDESC())[0]);
        udesttotalcostText.setText(JsonUnit.convertStrToArray(workorder.getUDESTTOTALCOST())[0]);
        statusText.setText(JsonUnit.convertStrToArray(workorder.getSTATUS())[0]);

        udtrv2Text.setText(JsonUnit.convertStrToArray(workorder.getUDTRV2())[0]);
        udremark2Text.setText(JsonUnit.convertStrToArray(workorder.getUDREMARK2())[0]);
        udremark4Text.setText(JsonUnit.convertStrToArray(workorder.getUDREMARK4())[0]);
        udsummary6Text.setText(JsonUnit.convertStrToArray(workorder.getUDSUMMARY6())[0]);
        udksremarkText.setText(JsonUnit.convertStrToArray(workorder.getUDKSREMARK())[0]);
        udaddress1Text.setText(JsonUnit.convertStrToArray(workorder.getUDADDRESS1())[0]);

        udremark1Text.setText(JsonUnit.convertStrToArray(workorder.getUDREMARK1())[0]);
        udtargstartdateText.setText(JsonUnit.strToDateString(JsonUnit.convertStrToArray(workorder.getUDTARGSTARTDATE())[0]));
        udtargcompdateText.setText(JsonUnit.strToDateString(JsonUnit.convertStrToArray(workorder.getUDTARGCOMPDATE())[0]));
        udtargstartdate2Text.setText(JsonUnit.strToDateString(JsonUnit.convertStrToArray(workorder.getUDTARGSTARTDATE2())[0]));
        udremark3Text.setText(JsonUnit.convertStrToArray(workorder.getUDREMARK3())[0]);
        udestdur3Text.setText((JsonUnit.convertStrToArray(workorder.getUDESTDUR3())[0]));

        udtrv1Text.setText(JsonUnit.convertStrToArray(workorder.getTEAMLEADER())[0]);
        rdcheadText.setText(JsonUnit.convertStrToArray(workorder.getRDCHEAD())[0]);
        reportedbyText.setText(JsonUnit.convertStrToArray(workorder.getREPORTEDBY())[0]);
        cudeptText.setText(JsonUnit.convertStrToArray(workorder.getCUDEPT())[0]);
        reportdateText.setText(JsonUnit.convertStrToArray(workorder.getREPORTDATE())[0]);
        phonenumText.setText(JsonUnit.convertStrToArray(workorder.getPHONENUM())[0]);

        rotate = AnimationUtils.loadAnimation(this, R.anim.arrow_rotate);//创建动画

        lcgrymdText.setOnClickListener(ccrTextOnClickListener);
        qtxxImageView.setOnClickListener(jbxxImageViewOnClickListener);
        kbfilelistImageView.setOnClickListener(kbfilelistImageViewOnClickListener);
        doclinksImageView.setOnClickListener(doclinksImageViewOnClickListener);
        yxmxImageView.setOnClickListener(yxmxImageViewOnClickListener);
        sqjlImageView.setOnClickListener(sqjlImageViewOnClickListener);

        workflowBtn.setOnClickListener(workflowBtnOnClickListener);


    }

    @Override
    protected String getSubTitle() {
        return "FiAM > " + getString(R.string.cglxxq_text);
    }

    //拟出国人员名单
    private View.OnClickListener ccrTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(CgWorkorderActivity.this, PersonrelationActivity.class);
            intent.putExtra("wonum", JsonUnit.convertStrToArray(workorder.getWONUM())[0]);
            intent.putExtra("title", getResources().getString(R.string.lcgrymd_text));
            intent.putExtra("appid", GlobalConfig.TRAVELS_APPID);
            startActivityForResult(intent, 0);
        }
    };

    //基本信息
    private View.OnClickListener jbxxImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (startAnaim()) {
                ccrLinearLayout.setVisibility(View.GONE);
                qtxxView.setVisibility(View.GONE);
            } else {
                ccrLinearLayout.setVisibility(View.VISIBLE);
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

    //出国立项申请-出国人员知识积累拟交付资料清单
    private View.OnClickListener kbfilelistImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CgWorkorderActivity.this, KbfilelistActivity.class);
            intent.putExtra("appid", GlobalConfig.TRAVELS_APPID);
            intent.putExtra("wonum", JsonUnit.convertStrToArray(workorder.getWONUM())[0]);
            intent.putExtra("title", getResources().getString(R.string.kbfilelist_text));
            startActivityForResult(intent, 0);
        }
    };


    //附件
    private View.OnClickListener doclinksImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CgWorkorderActivity.this, DoclinksActivity.class);
            intent.putExtra("ownertable", GlobalConfig.WORKORDER_NAME);
            intent.putExtra("ownerid", JsonUnit.convertStrToArray(workorder.getWORKORDERID())[0]);
            intent.putExtra("title", getResources().getString(R.string.fj_text));
            startActivityForResult(intent, 0);
        }
    };

    //预算明细
    private View.OnClickListener yxmxImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CgWorkorderActivity.this, FctaskrelationActivity.class);
            intent.putExtra("searchName", "WONUM");
            intent.putExtra("searchValue", JsonUnit.convertStrToArray(workorder.getWONUM())[0]);
            intent.putExtra("title", getResources().getString(R.string.yxmx_text));
            startActivityForResult(intent, 0);
        }
    };


    //审批记录
    private View.OnClickListener sqjlImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CgWorkorderActivity.this, WftransactionActivity.class);
            intent.putExtra("ownertable", GlobalConfig.WORKORDER_NAME);
            intent.putExtra("ownerid", JsonUnit.convertStrToArray(workorder.getWORKORDERID())[0]);
            startActivityForResult(intent, 0);
        }
    };


    //审批
    private View.OnClickListener workflowBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PostStart(GlobalConfig.WORKORDER_NAME, JsonUnit.convertStrToArray(workorder.getWORKORDERID())[0], GlobalConfig.TRAVELS_APPID, AccountUtils.getpersonId(CgWorkorderActivity.this));
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
                            showMiddleToast(CgWorkorderActivity.this, workflow.getErrmsg());
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        showMiddleToast(CgWorkorderActivity.this, getString(R.string.spsb_text));
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
                        showMiddleToast(CgWorkorderActivity.this, workflow.getErrmsg());
                        if (workflow.getErrcode().equals(GlobalConfig.WORKFLOW_103)) {
                            setResult(ActiveTaskActivity.TASK_RESULTCODE);
                            finish();
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        showMiddleToast(CgWorkorderActivity.this, getString(R.string.spsb_text));
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
                        PostApprove(ownertable, JsonUnit.convertStrToArray(workorder.getWORKORDERID())[0], memo, result.getIspositive(), AccountUtils.getpersonId(CgWorkorderActivity.this));
                    }


                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    //根据appid,grnum,objctname获取国内出差信息
    private void getNetWorkWorkOrder() {
        String data = HttpManager.getWORKORDERUrl(appid, ownertable, ownernum, AccountUtils.getpersonId(CgWorkorderActivity.this), 1, 10);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_Workorder.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_Workorder>() {
                    @Override
                    public void accept(@NonNull R_Workorder r_workorder) throws Exception {
                    }
                })

                .map(new Function<R_Workorder, R_Workorder.ResultBean>() {
                    @Override
                    public R_Workorder.ResultBean apply(@NonNull R_Workorder r_workorder) throws Exception {

                        return r_workorder.getResult();
                    }
                })
                .map(new Function<R_Workorder.ResultBean, List<R_Workorder.Workorder>>() {
                    @Override
                    public List<R_Workorder.Workorder> apply(@NonNull R_Workorder.ResultBean resultBean) throws Exception {
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<R_Workorder.Workorder>>() {
                    @Override
                    public void accept(@NonNull List<R_Workorder.Workorder> workorders) throws Exception {
                        dismissLoadingDialog();
                        if (workorders == null || workorders.isEmpty()) {
                        } else {
                            workorder = workorders.get(0);
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
