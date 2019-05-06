package com.hqxh.fiamproperty.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.rxbus.RxBus;
import com.google.gson.Gson;
import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.RxBus.RefreshCode;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.api.PopCallBack;
import com.hqxh.fiamproperty.base.BaseTitleActivity;
import com.hqxh.fiamproperty.bean.R_APPROVE;
import com.hqxh.fiamproperty.bean.R_APPROVE.Result;
import com.hqxh.fiamproperty.bean.R_WORKFLOW;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.FINCNTRLModel;
import com.hqxh.fiamproperty.model.LoginResults;
import com.hqxh.fiamproperty.model.R_PERSONRELATION;
import com.hqxh.fiamproperty.model.R_Workorder;
import com.hqxh.fiamproperty.model.R_Workorder.Workorder;
import com.hqxh.fiamproperty.ui.activity.GNWorker.PersonrelationDetailActivity;
import com.hqxh.fiamproperty.ui.activity.GNWorker.TaskSelectActivity;
import com.hqxh.fiamproperty.ui.widget.ConfirmDialog;
import com.hqxh.fiamproperty.ui.widget.DateSelect;
import com.hqxh.fiamproperty.ui.widget.DateTimePickerDialog;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.hqxh.fiamproperty.unit.JsonUnit;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 国内出差详情
 **/
public class GnWorkorderCopyActivity extends BaseTitleActivity {

    private static final String TAG = "GnWorkorderActivity";

    private ScrollView scrollView;
    ArrayList<String> array1 = new ArrayList<String>();
    /**
     * 信息展示
     **/
    private TextView wonumText; //申请单
    private TextView descriptionText; //描述
    private EditText projectidText; //费用号
    private EditText fincntrldescText; //项目名称
    private TextView worktypeText; //类型
    private TextView PARENT; //父任务单
    private TextView PARENTDESC; //父任务单描述
    private TextView statusText; //状态

    private ImageView ccrText; //行程

    private ImageView time_position_tool; //时间、地点、交通工具
    private LinearLayout line_id1;

    private TextView UDTARGSTARTDATE; //开始时间
    private TextView UDTARGCOMPDATE; //结束时间
    private EditText UDADDRESS1; //目的地

    private ImageView UDREMARK1_relative;
    private LinearLayout line_id2;

    private EditText UDREMARK1; //出差原因

    private ImageView money;//金额
    private LinearLayout line_id3;

    private TextView UDTRVCOST1; //差旅费用
    private EditText UDTRVCOST3; //其他费用
    private EditText UDTRVCOST2; //会议费用
    private CheckBox UDHASMEETING; //有会议费？
    private EditText UDESTTOTALCOST; //出差费用预计

    private ImageView Person_in_charge;//负责人
    private LinearLayout line_id5;

    private EditText UDTRV1;//团队负责人编号
    private EditText TEAMLEADER;//团队负责人姓名
    private EditText RDCHEAD;//院分管领导编号
    private EditText RDCHEADNAME;//院分管领导姓名

    private ImageView Remarks;//备注
    private LinearLayout line_id6;

    private EditText UDREMARK2;//备注

    private ImageView applicant;//申请人
    private LinearLayout line_id7;

    private EditText REPORTEDBYNAME;//申请人姓名
    private EditText CUDEPT;//申请人部门
    private TextView REPORTDATE;//申请日期
    private EditText PHONENUM;//电话

//    private TextView udtargstartdateText; //开始时间
//    private TextView udtargcompdateText; //结束时间
//    private TextView udaddress1Text; //目的地
//    private CheckBox udtransport3Text; //是否飞机
//    private TextView udremark1Text; //出差原因
//    private TextView udtrvcost1Text; //差旅费用
//    private TextView udtrvcost2Text; //其它费用
//    private TextView udesttotalcostText; //出差费用预算


//    private LinearLayout ccrLinearLayout;
//    private ImageView jbxxImageView;  //其它信息
//    private View qtxxView;
//
//    private TextView udtrv1Text; //团队负责人
//    private TextView rdcheadText; //中心分管领导
//    private TextView reportedbyText; //申请人
//    private TextView cudeptText; //部门
////    private TextView cucrewText; //科室
//    private TextView reportdateText; //申请日期
//    private TextView phonenumText; //电话

//    private ImageView doclinksImageView; //附件
    private ImageView yxmxImageView; //预算明细
//    private ImageView sqjlImageView; //审批记录

    private Button workflowBtn; //审批
    private RelativeLayout workflowRelativeLayout;//布局

    private Workorder workorder;

    private Animation rotate;

    private int mark = 0;//跳转标识
    private String appid; //appid
    private String ownernum;//ownernum
    private String ownertable;//ownertable
    private String type;
    private String yxmxImageViewType = null;
    private String ccrTextType = null;

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
        if (getIntent().hasExtra("type")) {
            type = getIntent().getExtras().getString("type");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_gn_workorder_copy;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        scrollView = (ScrollView) findViewById(R.id.scrollView_id);
        wonumText = (TextView) findViewById(R.id.WONUM);
        descriptionText = (TextView) findViewById(R.id.DESCRIPTION);
        projectidText = (EditText) findViewById(R.id.PROJECTID);
        fincntrldescText = (EditText) findViewById(R.id.FINCNTRLDESC);
        worktypeText = (TextView) findViewById(R.id.WORKTYPE);
        PARENT = (TextView) findViewById(R.id.PARENT);
        PARENTDESC = (TextView) findViewById(R.id.PARENTDESC);
        statusText = (TextView) findViewById(R.id.STATUSDESC);
        save_imageView_id = (ImageView) findViewById(R.id.save_imageView_id);
//        udtargstartdateText = (TextView) findViewById(R.id.udtargstartdate_text_id);
//        udtargcompdateText = (TextView) findViewById(R.id.udtargcompdate_text_id);
//        udaddress1Text = (TextView) findViewById(R.id.udaddress1_text_id);
//        udtransport3Text = (CheckBox) findViewById(R.id.udtransport3_text_id);
//        udremark1Text = (TextView) findViewById(R.id.udremark1_text_id);
//        udtrvcost1Text = (TextView) findViewById(R.id.udtrvcost1_text_id);
//        udtrvcost2Text = (TextView) findViewById(R.id.udtrvcost2_text_id);
//        udesttotalcostText = (TextView) findViewById(R.id.udesttotalcost_text_id);

        ccrText = (ImageView) findViewById(R.id.ccr_imageview_id);
//        ccrLinearLayout = (LinearLayout) findViewById(R.id.linearLayout_id);
//        jbxxImageView = (ImageView) findViewById(R.id.jbxx_kz_imageview_id);
//        qtxxView = (View) findViewById(R.id.qtxx_view_id);
//
//        udtrv1Text = (TextView) findViewById(R.id.udtrv1_text_id);
//        rdcheadText = (TextView) findViewById(R.id.rdchead_text_id);
//        reportedbyText = (TextView) findViewById(R.id.reportedby_text_id);
//        cudeptText = (TextView) findViewById(R.id.cudept_text_id);
////        cucrewText = (TextView) findViewById(R.id.cucrew_text_id);
//        reportdateText = (TextView) findViewById(R.id.reportdate_text_id);
//        phonenumText = (TextView) findViewById(R.id.phonenum_text_id);

//        doclinksImageView = (ImageView) findViewById(R.id.doclinks_imageview_id);
        yxmxImageView = (ImageView) findViewById(R.id.yxmx_imageview_id);
//        sqjlImageView = (ImageView) findViewById(R.id.sqjl_imageview_id);

        time_position_tool = (ImageView) findViewById(R.id.time_position_tool);
        line_id1 = (LinearLayout) findViewById(R.id.line_id1);

        UDTARGSTARTDATE = (TextView) findViewById(R.id.UDTARGSTARTDATE);
        UDTARGCOMPDATE = (TextView) findViewById(R.id.UDTARGCOMPDATE);
        UDADDRESS1 = (EditText)findViewById(R.id.UDADDRESS1);

        UDREMARK1_relative =(ImageView) findViewById(R.id.UDREMARK1_relative);
        line_id2 = (LinearLayout) findViewById(R.id.line_id2);

        UDREMARK1  = (EditText)findViewById(R.id.UDREMARK1);

        money  =(ImageView) findViewById(R.id.money);
        line_id3 =  (LinearLayout) findViewById(R.id.line_id3);

        UDTRVCOST1 = (TextView) findViewById(R.id.UDREMARK1);
        UDTRVCOST3 = (EditText) findViewById(R.id.UDTRVCOST3);
        UDTRVCOST2 = (EditText) findViewById(R.id.UDTRVCOST2);
        UDHASMEETING = (CheckBox) findViewById(R.id.UDHASMEETING);
        UDESTTOTALCOST = (EditText) findViewById(R.id.UDESTTOTALCOST);

        Person_in_charge = (ImageView) findViewById(R.id.Person_in_charge);
        line_id5 = (LinearLayout) findViewById(R.id.line_id5);
        UDTRV1 = (EditText) findViewById(R.id.UDTRV1);
        TEAMLEADER = (EditText) findViewById(R.id.TEAMLEADER);
        RDCHEAD = (EditText) findViewById(R.id.RDCHEAD);
        RDCHEADNAME  = (EditText) findViewById(R.id.RDCHEADNAME);
        Remarks = (ImageView) findViewById(R.id.Remarks);
        line_id6  = (LinearLayout) findViewById(R.id.line_id6);
        UDREMARK2 = (EditText) findViewById(R.id.UDREMARK2);
        applicant = (ImageView) findViewById(R.id.applicant);
        line_id7 = (LinearLayout) findViewById(R.id.line_id7);
        REPORTEDBYNAME = (EditText) findViewById(R.id.REPORTEDBYNAME);
        CUDEPT = (EditText) findViewById(R.id.CUDEPT);
        REPORTDATE = (TextView) findViewById(R.id.REPORTDATE);
        PHONENUM = (EditText) findViewById(R.id.PHONENUM);

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
            showLoadingDialog(getString(R.string.loading_hint));
            getNetWorkWorkOrder(workorder.getWORKORDERID());
        }

    }


    //展示界面数据
    private void showData() {

        if(type.equals("insert")) {
            String personId = AccountUtils.getpersonId(this);
//            UDTRV1.setText(personId);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            REPORTDATE.setText(simpleDateFormat.format(date));
        }else if(type.equals("update")){
            UDTRV1.setText(JsonUnit.convertStrToArray(workorder.getUDTRV1())[0]);
            REPORTDATE.setText(JsonUnit.convertStrToArray(workorder.getREPORTDATE())[0]);
        }

        wonumText.setText(JsonUnit.convertStrToArray(workorder.getWONUM())[0]);
        descriptionText.setText(JsonUnit.convertStrToArray(workorder.getDESCRIPTION())[0]);
        projectidText.setText(JsonUnit.convertStrToArray(workorder.getPROJECTID())[0]);
        fincntrldescText.setText(JsonUnit.convertStrToArray(workorder.getFINCNTRLDESC())[0]);
        worktypeText.setText(getResources().getString(R.string.Travel_Application));
        PARENT.setText(JsonUnit.convertStrToArray(workorder.getPARENT())[0]);
        PARENTDESC.setText(JsonUnit.convertStrToArray(workorder.getPARENTDESC())[0]);
        statusText.setText(JsonUnit.convertStrToArray(workorder.getSTATUSDESC())[0]);
        UDTARGSTARTDATE.setText(JsonUnit.convertStrToArray(workorder.getUDTARGSTARTDATE())[0]);
        UDTARGCOMPDATE.setText(JsonUnit.convertStrToArray(workorder.getUDTARGCOMPDATE())[0]);
        UDADDRESS1.setText(JsonUnit.convertStrToArray(workorder.getUDADDRESS1())[0]);
        UDREMARK1.setText(JsonUnit.convertStrToArray(workorder.getUDREMARK1())[0]);
        UDTRVCOST1.setText(JsonUnit.convertStrToArray(workorder.getUDTRVCOST1())[0]);
        UDTRVCOST3.setText(JsonUnit.convertStrToArray(workorder.getUDTRVCOST3())[0]);
        UDTRVCOST2.setText(JsonUnit.convertStrToArray(workorder.getUDTRVCOST2())[0]);
        if (JsonUnit.convertStrToArray(workorder.getUDHASMEETING())[0].equals("1")) {
            UDHASMEETING.setChecked(true);
        } else {
            UDHASMEETING.setChecked(false);
        }
        UDESTTOTALCOST.setText(JsonUnit.convertStrToArray(workorder.getUDESTTOTALCOST())[0]);
        TEAMLEADER.setText(JsonUnit.convertStrToArray(workorder.getTEAMLEADER())[0]);
        RDCHEAD.setText(JsonUnit.convertStrToArray(workorder.getRDCHEAD())[0]);
        RDCHEADNAME.setText(JsonUnit.convertStrToArray(workorder.getRDCHEADNAME())[0]);
        UDREMARK2.setText(JsonUnit.convertStrToArray(workorder.getUDREMARK2())[0]);
        REPORTEDBYNAME.setText(JsonUnit.convertStrToArray(workorder.getREPORTEDBYNAME())[0]);
        CUDEPT.setText(JsonUnit.convertStrToArray(workorder.getCUDEPT())[0]);

        PHONENUM.setText(JsonUnit.convertStrToArray(workorder.getPHONENUM())[0]);

//        udtargstartdateText.setText(JsonUnit.strToDateString(JsonUnit.convertStrToArray(workorder.getUDTARGSTARTDATE())[0]));
//        udtargcompdateText.setText(JsonUnit.strToDateString(JsonUnit.convertStrToArray(workorder.getUDTARGCOMPDATE())[0]));
//        udaddress1Text.setText(JsonUnit.convertStrToArray(workorder.getUDADDRESS1())[0]);
//        if (JsonUnit.convertStrToArray(workorder.getUDTRANSPORT3())[0].equals("1")) {
//            udtransport3Text.setChecked(true);
//        } else {
//            udtransport3Text.setChecked(false);
//        }
//        udremark1Text.setText(JsonUnit.convertStrToArray(workorder.getUDREMARK1())[0]);
//        udtrvcost1Text.setText(JsonUnit.convertStrToArray(workorder.getUDTRVCOST1())[0]);
//        udtrvcost2Text.setText(JsonUnit.convertStrToArray(workorder.getUDTRVCOST2())[0]);
//        udesttotalcostText.setText(JsonUnit.convertStrToArray(workorder.getUDESTTOTALCOST())[0]);
//
//        udtrv1Text.setText(JsonUnit.convertStrToArray(workorder.getTEAMLEADER())[0]);
//        rdcheadText.setText(JsonUnit.convertStrToArray(workorder.getRDCHEAD())[0]);
//        reportedbyText.setText(JsonUnit.convertStrToArray(workorder.getREPORTEDBY())[0]);
//        cudeptText.setText(JsonUnit.convertStrToArray(workorder.getCUDEPT())[0]);
////        cucrewText.setText(JsonUnit.convertStrToArray(workorder.getCUCREW())[0]);
//        reportdateText.setText(JsonUnit.convertStrToArray(workorder.getREPORTDATE())[0]);
//        phonenumText.setText(JsonUnit.convertStrToArray(workorder.getPHONENUM())[0]);

        save_imageView_id.setVisibility(View.VISIBLE);

        setOnClickListener();


    }

    private void setOnClickListener() {
        rotate = AnimationUtils.loadAnimation(this, R.anim.arrow_rotate);//创建动画

        if (mark == HomeActivity.DB_CODE) { //待办任务
            workflowRelativeLayout.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0, 0, 0, getHeight(workflowRelativeLayout));//4个参数按顺序分别是左上右下
            scrollView.setLayoutParams(layoutParams);
        }

        if((null != workorder && "WREQ".equals(JsonUnit.convertStrToArray(workorder.getSTATUS())[0])) || type.equals("insert")) {
            projectidText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                        checkProjectId(v.getText().toString(), null);
                    }
                    return true;
                }
            });

            Drawable nav_up1 = getResources().getDrawable(R.drawable.ic_select);
            nav_up1.setBounds(0, 0, nav_up1.getMinimumWidth(), nav_up1.getMinimumHeight());
            PARENT.setCompoundDrawablesWithIntrinsicBounds(null, null, nav_up1, null);
            PARENT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GnWorkorderCopyActivity.this, TaskSelectActivity.class);
                    startActivityForResult(intent, 28);
                }
            });

//        Drawable nav_up2 = getResources().getDrawable(R.drawable.ic_select);
//        nav_up2.setBounds(0, 0, nav_up2.getMinimumWidth(), nav_up2.getMinimumHeight());
//        worktypeText.setCompoundDrawablesWithIntrinsicBounds(null, null, nav_up2, null);
//        worktypeText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DateTimePickerDialog dialog = new DateTimePickerDialog(GnWorkorderCopyActivity.this, array1, "值选择");
//                dialog.setOnSelectTimeSetListener(new DateTimePickerDialog.OnSelectSetListener<String>() {
//                    @Override
//                    public void OnSelectSet(String select) {
//                        worktypeText.setText(select);
//                    }
//                });
//            }
//        });

            Drawable nav_up = getResources().getDrawable(R.drawable.ic_data);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            UDTARGSTARTDATE.setCompoundDrawablesWithIntrinsicBounds(null, null, nav_up, null);
            UDTARGSTARTDATE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DateSelect(GnWorkorderCopyActivity.this, UDTARGSTARTDATE).showDialog();
                }
            });

            UDTARGCOMPDATE.setCompoundDrawablesWithIntrinsicBounds(null, null, nav_up, null);
            UDTARGCOMPDATE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DateSelect(GnWorkorderCopyActivity.this, UDTARGCOMPDATE).showDialog();
                }
            });

//            REPORTDATE.setCompoundDrawablesWithIntrinsicBounds(null, null, nav_up, null);
//            REPORTDATE.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    new DateSelect(GnWorkorderCopyActivity.this, REPORTDATE).showDialog();
//                }
//            });

//            ccrText.setOnClickListener(ccrTextOnClickListener);
//        jbxxImageView.setOnClickListener(jbxxImageViewOnClickListener);
//        doclinksImageView.setOnClickListener(doclinksImageViewOnClickListener);
//            yxmxImageView.setOnClickListener(yxmxImageViewOnClickListener);
//        sqjlImageView.setOnClickListener(sqjlImageViewOnClickListener);
            yxmxImageViewType = "insert";
            ccrTextType = "insert";
            //保存单据
            save_imageView_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    beforeSave(workorder);
                }
            });
        }else {
            save_imageView_id.setVisibility(View.GONE);
            projectidText.setFocusable(false);
            projectidText.setFocusableInTouchMode(false);
            fincntrldescText.setFocusable(false);
            fincntrldescText.setFocusableInTouchMode(false);
            UDADDRESS1.setFocusable(false);
            UDADDRESS1.setFocusableInTouchMode(false);
            UDREMARK1.setFocusable(false);
            UDREMARK1.setFocusableInTouchMode(false);
            UDTRVCOST3.setFocusable(false);
            UDTRVCOST3.setFocusableInTouchMode(false);
            UDTRVCOST2.setFocusable(false);
            UDTRVCOST2.setFocusableInTouchMode(false);
            UDESTTOTALCOST.setFocusable(false);
            UDESTTOTALCOST.setFocusableInTouchMode(false);
            UDTRV1.setFocusable(false);
            UDTRV1.setFocusableInTouchMode(false);
            RDCHEAD.setFocusable(false);
            RDCHEAD.setFocusableInTouchMode(false);
            UDREMARK2.setFocusable(false);
            UDREMARK2.setFocusableInTouchMode(false);
            REPORTEDBYNAME.setFocusable(false);
            REPORTEDBYNAME.setFocusableInTouchMode(false);
            CUDEPT.setFocusable(false);
            CUDEPT.setFocusableInTouchMode(false);
            PHONENUM.setFocusable(false);
            PHONENUM.setFocusableInTouchMode(false);

            yxmxImageViewType = "update";
            ccrTextType = "update";
        }

        time_position_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startAnaim(time_position_tool)) {
                    line_id1.setVisibility(View.GONE);
                } else {
                    line_id1.setVisibility(View.VISIBLE);
                }
            }
        });

        UDREMARK1_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startAnaim(UDREMARK1_relative)) {
                    line_id2.setVisibility(View.GONE);
                } else {
                    line_id2.setVisibility(View.VISIBLE);
                }
            }
        });

        Person_in_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startAnaim(Person_in_charge)) {
                    line_id5.setVisibility(View.GONE);
                } else {
                    line_id5.setVisibility(View.VISIBLE);
                }
            }
        });

        Remarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startAnaim(Remarks)) {
                    line_id6.setVisibility(View.GONE);
                } else {
                    line_id6.setVisibility(View.VISIBLE);
                }
            }
        });

        applicant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startAnaim(applicant)) {
                    line_id7.setVisibility(View.GONE);
                } else {
                    line_id7.setVisibility(View.VISIBLE);
                }
            }
        });

        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startAnaim(money)) {
                    line_id3.setVisibility(View.GONE);
                } else {
                    line_id3.setVisibility(View.VISIBLE);
                }
            }
        });





        workflowBtn.setOnClickListener(workflowBtnOnClickListener);

        ccrText.setOnClickListener(ccrTextOnClickListener);
        yxmxImageView.setOnClickListener(yxmxImageViewOnClickListener);
    }

    public void beforeSave(Workorder workorder) {
        final HashMap<String, Object> savemap = new HashMap<>();
        Workorder workorderSave = null;
//        if(null == worktypeText.getText().toString() || "".equals(worktypeText.getText().toString())) {
//            showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.Workorder_worktype));
//            return;
//        }
        if(null == UDTARGSTARTDATE.getText().toString() || "".equals(UDTARGSTARTDATE.getText().toString())) {
            showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.Workorder_UDTARGSTARTDATE));
            return;
        }
        if(null == UDTARGCOMPDATE.getText().toString() || "".equals(UDTARGCOMPDATE.getText().toString())) {
            showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.Workorder_UDTARGCOMPDATE));
            return;
        }
        if(null == UDADDRESS1.getText().toString() || "".equals(UDADDRESS1.getText().toString())) {
            showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.Workorder_UDADDRESS1));
            return;
        }
        if(null == UDREMARK1.getText().toString() || "".equals(UDREMARK1.getText().toString())) {
            showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.Workorder_UDREMARK1));
            return;
        }
        if(null == UDTRV1.getText().toString() || "".equals(UDTRV1.getText().toString())) {
            showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.Workorder_UDTRV1));
            return;
        }

        if(null == TEAMLEADER.getText().toString() || "".equals(TEAMLEADER.getText().toString())) {
            showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.Workorder_TEAMLEADER));
            return;
        }

        if(null == RDCHEAD.getText().toString() || "".equals(RDCHEAD.getText().toString())) {
            showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.Workorder_RDCHEAD));
            return;
        }

        if(null == RDCHEADNAME.getText().toString() || "".equals(RDCHEADNAME.getText().toString())) {
            showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.Workorder_RDCHEADNAME));
            return;
        }

        //费用号
        if(null == projectidText.getText().toString() || "".equals(projectidText.getText().toString())) {
            showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.check_PROJECTID));
            return;
        }
        //描述
        if(null == descriptionText.getText().toString() || "".equals(descriptionText.getText().toString())) {
            showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.check_DESCRIPTION));
            return;
        }
        //电话
        if(null == PHONENUM.getText().toString() || "".equals(PHONENUM.getText().toString())) {
            showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.check_PHONENUM));
            return;
        }


        try {
            workorderSave = (Workorder) workorder.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        if(type.equals("insert")) {
            savemap.put("STATUS", "WREQ");
            savemap.put("WORKTYPE", "TRAVN");
            savemap.put("UDTRANSPORT3", "0");
            savemap.put("HISTORYFLAG", "0");

        }else {
            savemap.put("WORKORDERID", JsonUnit.convertStrToArray(workorderSave.getWORKORDERID())[0]);
            savemap.put("WONUM", JsonUnit.convertStrToArray(workorderSave.getWONUM())[0]);
            savemap.put("STATUS", JsonUnit.convertStrToArray(workorderSave.getSTATUS())[0]);
            savemap.put("WORKTYPE", JsonUnit.convertStrToArray(workorderSave.getWORKTYPE())[0]);
            savemap.put("UDTRANSPORT3", JsonUnit.convertStrToArray(workorderSave.getUDTRANSPORT3())[0]);
            savemap.put("HISTORYFLAG", JsonUnit.convertStrToArray(workorderSave.getHISTORYFLAG())[0]);
        }
        savemap.put("DESCRIPTION", descriptionText.getText().toString());
        savemap.put("PROJECTID", projectidText.getText().toString());
        savemap.put("FINCNTRLDESC", fincntrldescText.getText().toString());

        savemap.put("PARENT", PARENT.getText().toString());
        savemap.put("PARENTDESC", PARENTDESC.getText().toString());

        savemap.put("UDTARGSTARTDATE", UDTARGSTARTDATE.getText().toString());
        savemap.put("UDTARGCOMPDATE", UDTARGCOMPDATE.getText().toString());
        savemap.put("UDADDRESS1", UDADDRESS1.getText().toString());
        savemap.put("UDREMARK1", UDREMARK1.getText().toString());
        savemap.put("UDTRVCOST1", UDTRVCOST1.getText().toString());
        savemap.put("UDTRVCOST3", UDTRVCOST3.getText().toString());
        savemap.put("UDTRVCOST2", UDTRVCOST2.getText().toString());
        savemap.put("UDHASMEETING", UDHASMEETING.isChecked() == true ? "1" : "0");
        savemap.put("UDESTTOTALCOST", UDESTTOTALCOST.getText().toString());
        savemap.put("UDTRV1", UDTRV1.getText().toString());
        savemap.put("TEAMLEADER", TEAMLEADER.getText().toString());
        savemap.put("RDCHEAD", RDCHEAD.getText().toString());
        savemap.put("RDCHEADNAME", RDCHEADNAME.getText().toString());
        savemap.put("UDREMARK2", UDREMARK2.getText().toString());
        savemap.put("REPORTEDBYNAME", REPORTEDBYNAME.getText().toString());
        savemap.put("CUDEPT", CUDEPT.getText().toString());
        savemap.put("REPORTDATE", REPORTDATE.getText().toString());
        savemap.put("PHONENUM", PHONENUM.getText().toString());

        //检验费用号
        checkProjectId(projectidText.getText().toString(), new PopCallBack() {
            @Override
            public void selectedState(String lable) {
                save(savemap);
            }
        });
    }

    public void checkProjectId(String PROJECTID, final PopCallBack popCallback) {
        showLoadingDialog(getResources().getString(R.string.loading_hint));
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        String data = HttpManager.checkProjectId(AccountUtils.getpersonId(this), 1, 20, year, PROJECTID);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(FINCNTRLModel.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<FINCNTRLModel>() {
                    @Override
                    public void accept(@NonNull FINCNTRLModel r_purchview) throws Exception {
                    }
                })

                .map(new Function<FINCNTRLModel, FINCNTRLModel.ResultBean>() {
                    @Override
                    public FINCNTRLModel.ResultBean apply(@NonNull FINCNTRLModel r_purchview) throws Exception {
                        return r_purchview.getResult();
                    }
                })
                .map(new Function<FINCNTRLModel.ResultBean, List<FINCNTRLModel.FINCNTRL_Model>>() {
                    @Override
                    public List<FINCNTRLModel.FINCNTRL_Model> apply(@NonNull FINCNTRLModel.ResultBean resultBean) throws Exception {
                        Log.e(TAG, "Totalresult=" + resultBean.getTotalresult());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FINCNTRLModel.FINCNTRL_Model>>() {
                    @Override
                    public void accept(@NonNull List<FINCNTRLModel.FINCNTRL_Model> purchview) throws Exception {
                        dismissLoadingDialog();
                        if(null == purchview || purchview.size()  <=0)     {
                            showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.check_projectid));
                        }else {
                            if(null != popCallback) {
                                popCallback.selectedState("ok");
                            }
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadingDialog();
                    }
                });
    }

    public void save(HashMap<String, Object> savemap) {
        showLoadingDialog(getResources().getString(R.string.loading_hint));
        String jsonStr = null;
        try {
            jsonStr = HttpManager.saveWorkorder(AccountUtils.getpersonId(GnWorkorderCopyActivity.this), JSON.toJSONString(savemap));
        }catch (Exception e) {
            e.printStackTrace();
        }

        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", jsonStr)
                .build()
                .getObjectObservable(LoginResults.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<LoginResults>() {
                    @Override
                    public void accept(@NonNull LoginResults r_workorder) throws Exception {
                        String result = r_workorder.getResult();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResults>() {
                    @Override
                    public void accept(@NonNull LoginResults workorders) throws Exception {
                        dismissLoadingDialog();
                        String result = workorders.getResult();
                        showMiddleToast(GnWorkorderCopyActivity.this, workorders.getErrmsg());
                        if(workorders.getErrcode().equals("GLOBAL-S-0")) {
                        if(type.equals("insert")) {
                            String id = result.substring(11, result.length());
                            type = "update";
                            mark = HomeActivity.DB_CODE;
                            getNetWorkWorkOrder(id);
                        }
//                            finish();
                            // 发送 String 类型事件
                            RxBus.getDefault().post(RefreshCode.Refresh1);
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadingDialog();
                    }
                });
    }

    @Override
    protected String getSubTitle() {
        return getString(R.string.gncc_detail_text);
    }

//    //行程明细行
//    private View.OnClickListener stroke_imageview_idOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(GnWorkorderCopyActivity.this, PersonrelationActivity.class);
//            intent.putExtra("appid", GlobalConfig.TRAVEL_APPID);
//            intent.putExtra("wonum", JsonUnit.convertStrToArray(workorder.getWONUM())[0]);
//            intent.putExtra("title", getResources().getString(R.string.ccr_text));
//            startActivityForResult(intent, 0);
//        }
//    };

    //行程明细行
    private View.OnClickListener ccrTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(type.equals("insert")){
                showMiddleToast(GnWorkorderCopyActivity.this, getResources().getString(R.string.first_option));
                return;
            }
            Intent intent = new Intent(GnWorkorderCopyActivity.this, PersonrelationActivity.class);
            intent.putExtra("appid", GlobalConfig.TRAVEL_APPID);
            intent.putExtra("mainid", JsonUnit.convertStrToArray(workorder.getWORKORDERID())[0]);
            intent.putExtra("wonum", JsonUnit.convertStrToArray(workorder.getWONUM())[0]);
            intent.putExtra("title", getResources().getString(R.string.stroke_text));
            intent.putExtra("type", ccrTextType);
            startActivityForResult(intent, 0);
        }
    };

//    //基本信息
//    private View.OnClickListener jbxxImageViewOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            if (startAnaim(jbxxImageView)) {
//                ccrLinearLayout.setVisibility(View.GONE);
//                qtxxView.setVisibility(View.GONE);
//            } else {
//                ccrLinearLayout.setVisibility(View.VISIBLE);
//                qtxxView.setVisibility(View.VISIBLE);
//            }
//
//        }
//    };


    //附件
    private View.OnClickListener doclinksImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(GnWorkorderCopyActivity.this, DoclinksActivity.class);
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
            if(type.equals("insert")){
                showMiddleToast(GnWorkorderCopyActivity.this, getResources().getString(R.string.first_option));
                return;
            }
            Intent intent = new Intent(GnWorkorderCopyActivity.this, FctaskrelationActivity.class);
            intent.putExtra("searchName", "WONUM");
            intent.putExtra("searchValue", JsonUnit.convertStrToArray(workorder.getWONUM())[0]);
            intent.putExtra("title", getResources().getString(R.string.yxmx_text));
            intent.putExtra("ID", JsonUnit.convertStrToArray(workorder.getWORKORDERID())[0]);
            intent.putExtra("type", yxmxImageViewType);
            startActivityForResult(intent, 0);
        }
    };


    //启动动画
    private boolean startAnaim(ImageView imageView) {

        rotate.setInterpolator(new LinearInterpolator());//设置为线性旋转

        rotate.setFillAfter(!rotate.getFillAfter());//每次都取相反值，使得可以不恢复原位的旋转

        imageView.startAnimation(rotate);
        return rotate.getFillAfter();
    }

    //审批记录
    private View.OnClickListener sqjlImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(GnWorkorderCopyActivity.this, WftransactionActivity.class);
            intent.putExtra("ownertable", GlobalConfig.WORKORDER_NAME);
            intent.putExtra("ownerid", JsonUnit.convertStrToArray(workorder.getWORKORDERID())[0]);
            startActivityForResult(intent, 0);
        }
    };

    //审批
    private View.OnClickListener workflowBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PostStart(GlobalConfig.WORKORDER_NAME, JsonUnit.convertStrToArray(workorder.getWORKORDERID())[0], GlobalConfig.TRAVEL_APPID, AccountUtils.getpersonId(GnWorkorderCopyActivity.this));
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
                            showMiddleToast(GnWorkorderCopyActivity.this, workflow.getErrmsg());
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.spsb_text));
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
                        showMiddleToast(GnWorkorderCopyActivity.this, workflow.getErrmsg());
                        if (workflow.getErrcode().equals(GlobalConfig.WORKFLOW_103)) {
                            setResult(ActiveTaskActivity.TASK_RESULTCODE);
                            finish();
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        showMiddleToast(GnWorkorderCopyActivity.this, getString(R.string.spsb_text));
                    }
                });
    }


    //弹出对话框
    public void showDialog(List<Result> results) {//

        ConfirmDialog.Builder dialog = new ConfirmDialog.Builder(this);
        dialog.setTitle("审批")
                .setData(results)
                .setPositiveButton("确定", new ConfirmDialog.Builder.cOnClickListener() {
                    @Override
                    public void cOnClickListener(DialogInterface dialogInterface, Result result, String memo) {
                        dialogInterface.dismiss();
                        PostApprove(ownertable, JsonUnit.convertStrToArray(workorder.getWORKORDERID())[0], memo, result.getIspositive(), AccountUtils.getpersonId(GnWorkorderCopyActivity.this));
                    }


                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    //根据appid,grnum,objctname获取国内出差信息
    private void getNetWorkWorkOrder(String id) {
        String data = HttpManager.getWORKORDERbyIDUrl(GlobalConfig.TRAVEL_APPID, ownertable, id, AccountUtils.getpersonId(GnWorkorderCopyActivity.this), 1, 10);
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
                .map(new Function<R_Workorder.ResultBean, List<Workorder>>() {
                    @Override
                    public List<Workorder> apply(@NonNull R_Workorder.ResultBean resultBean) throws Exception {
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Workorder>>() {
                    @Override
                    public void accept(@NonNull List<Workorder> workorders) throws Exception {
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

    /**
     * 接受返回的结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!(null == data || "".equals(data))) {
            if (requestCode == 28) {
                Workorder workorder = (Workorder) data.getSerializableExtra("workorder");
                PARENT.setText(JsonUnit.convertStrToArray(workorder.getWONUM())[0]);
                PARENTDESC.setText(JsonUnit.convertStrToArray(workorder.getDESCRIPTION())[0]);
            }
        }
    }
}
