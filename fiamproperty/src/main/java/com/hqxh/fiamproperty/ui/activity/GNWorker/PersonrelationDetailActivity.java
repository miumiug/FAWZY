package com.hqxh.fiamproperty.ui.activity.GNWorker;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.rxbus.RxBus;
import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.RxBus.RefreshCode;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseTitleActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.LoginResults;
import com.hqxh.fiamproperty.model.R_PERSONRELATION;
import com.hqxh.fiamproperty.model.R_ZXPERSON;
import com.hqxh.fiamproperty.ui.activity.PersonActivity;
import com.hqxh.fiamproperty.ui.widget.DateSelect;
import com.hqxh.fiamproperty.ui.widget.DateTimePickerDialog;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.hqxh.fiamproperty.unit.JsonUnit;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class PersonrelationDetailActivity extends BaseTitleActivity {

    private static final String TAG = "PersonrelationDetailActivity";

    private ScrollView scrollView;

    private String type;

    private String wonum;
    private String mainid;

    private R_PERSONRELATION.PERSONRELATION personrelation = new R_PERSONRELATION.PERSONRELATION();
    ArrayList<String> array1 = new ArrayList<String>();

    //保存按钮
    private ImageView save_imageView_id;

    //人员
    private TextView PERSONID;
    //姓名
    private TextView DISPLAYNAME;
//    //人员类别
//    private TextView UDPERSONTYPE;
    //部门
    private TextView CUDEPT;
    //出发地
    private TextView PLACEA;
    //目的地
    private TextView PLACEZ;
    //出发日期
    private TextView STARTDATE;
    //到达日期
    private TextView ENDDATE;
    //交通工具
    private TextView TRANSPORT;
    //交通费
    private TextView TRVCOST3;
    //住宿费
    private TextView TRVCOST4;
    //其它费用
    private TextView TRVCOST9;
    //补助费用
    private TextView TRVCOST2;
    //预计总费用
    private TextView LINECOST;
    //已发差旅平台？
    private CheckBox ISONLINE;
    //接口消息
//    private TextView IFMESSAGE;

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("PERSONRELATION")) {
            personrelation = (R_PERSONRELATION.PERSONRELATION) getIntent().getSerializableExtra("PERSONRELATION");
        }
        if (getIntent().hasExtra("type")) {
            type = getIntent().getExtras().getString("type");
        }
        if (getIntent().hasExtra("wonum")) {
            wonum = getIntent().getExtras().getString("wonum");
        }
        if (getIntent().hasExtra("mainid")) {
            mainid = getIntent().getExtras().getString("mainid");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_personlation_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        scrollView = (ScrollView) findViewById(R.id.scrollView_id);
        PERSONID = (TextView) findViewById(R.id.PERSONID);
        DISPLAYNAME = (TextView) findViewById(R.id.DISPLAYNAME);
//        UDPERSONTYPE = (TextView) findViewById(R.id.UDPERSONTYPE);
        CUDEPT = (TextView) findViewById(R.id.CUDEPT);
        PLACEA = (TextView) findViewById(R.id.PLACEA);
        PLACEZ = (TextView) findViewById(R.id.PLACEZ);
        STARTDATE = (TextView) findViewById(R.id.STARTDATE);
        ENDDATE = (TextView) findViewById(R.id.ENDDATE);
        TRANSPORT = (TextView) findViewById(R.id.TRANSPORT);
        TRVCOST3 = (TextView) findViewById(R.id.TRVCOST3);
        TRVCOST4 = (TextView) findViewById(R.id.TRVCOST4);
        TRVCOST9 = (TextView) findViewById(R.id.TRVCOST9);
        TRVCOST2 = (TextView) findViewById(R.id.TRVCOST2);
        LINECOST = (TextView) findViewById(R.id.LINECOST);
        ISONLINE = (CheckBox) findViewById(R.id.ISONLINE);
//        IFMESSAGE = (TextView) findViewById(R.id.IFMESSAGE);
        save_imageView_id = (ImageView) findViewById(R.id.save_imageView_id);

        showData(personrelation);
    }


    //展示界面数据
    private void showData(R_PERSONRELATION.PERSONRELATION personrelation) {
        array1.add("汽车");
        array1.add("火车");
        array1.add("自带交通工具");
        array1.add("轮船");
        array1.add("飞机");

        save_imageView_id.setVisibility(View.VISIBLE);

        if(!type.equals("insert")) {
            PERSONID.setText(JsonUnit.convertStrToArray(personrelation.getPERSONID())[0]);
            DISPLAYNAME.setText(JsonUnit.convertStrToArray(personrelation.getDISPLAYNAME())[0]);
//        UDPERSONTYPE.setText(JsonUnit.convertStrToArray(personrelation.getUDPERSONTYPE())[0]);
            CUDEPT.setText(JsonUnit.convertStrToArray(personrelation.getCUDEPT())[0]);
            PLACEA.setText(JsonUnit.convertStrToArray(personrelation.getPLACEA())[0]);
            PLACEZ.setText(JsonUnit.convertStrToArray(personrelation.getPLACEZ())[0]);
            STARTDATE.setText(JsonUnit.convertStrToArray(personrelation.getSTARTDATE())[0]);
            ENDDATE.setText(JsonUnit.convertStrToArray(personrelation.getENDDATE())[0]);
            TRANSPORT.setText(JsonUnit.convertStrToArray(personrelation.getTRANSPORT())[0]);
            TRVCOST3.setText(JsonUnit.convertStrToArray(personrelation.getTRVCOST3())[0]);
            TRVCOST4.setText(JsonUnit.convertStrToArray(personrelation.getTRVCOST4())[0]);
            TRVCOST9.setText(JsonUnit.convertStrToArray(personrelation.getTRVCOST9())[0]);
            TRVCOST2.setText(JsonUnit.convertStrToArray(personrelation.getTRVCOST2())[0]);
            LINECOST.setText(JsonUnit.convertStrToArray(personrelation.getLINECOST())[0]);
//            ISONLINE.setText(JsonUnit.convertStrToArray(personrelation.getISONLINE())[0]);
            if (JsonUnit.convertStrToArray(personrelation.getISONLINE())[0].equals("1")) {
                ISONLINE.setChecked(true);
            } else {
                ISONLINE.setChecked(false);
            }
//            IFMESSAGE.setText(JsonUnit.convertStrToArray(personrelation.getIFMESSAGE())[0]);
        }
        setOnClickListener();


    }

    private void setOnClickListener() {
        Drawable nav_up1 = getResources().getDrawable(R.drawable.ic_person_add);
        nav_up1.setBounds(0, 0, nav_up1.getMinimumWidth(), nav_up1.getMinimumHeight());
        PERSONID.setCompoundDrawablesWithIntrinsicBounds(null, null, nav_up1, null);
        PERSONID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonrelationDetailActivity.this, PersonActivity.class);
                intent.putExtra("appid", GlobalConfig.USER_APPID);
                startActivityForResult(intent, GlobalConfig.PERSON_REQUESTCODE);
            }
        });

        Drawable nav_up2 = getResources().getDrawable(R.drawable.ic_data);
        nav_up2.setBounds(0, 0, nav_up2.getMinimumWidth(), nav_up2.getMinimumHeight());
        STARTDATE.setCompoundDrawablesWithIntrinsicBounds(null, null, nav_up2, null);
        STARTDATE.setOnClickListener(STARTDATEOnClickListener);

        ENDDATE.setCompoundDrawablesWithIntrinsicBounds(null, null, nav_up2, null);
        ENDDATE.setOnClickListener(ENDDATEOnClickListener);

        Drawable nav_up3 = getResources().getDrawable(R.drawable.ic_select);
        nav_up3.setBounds(0, 0, nav_up3.getMinimumWidth(), nav_up3.getMinimumHeight());
        TRANSPORT.setCompoundDrawablesWithIntrinsicBounds(null, null, nav_up3, null);
        TRANSPORT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerDialog dialog = new DateTimePickerDialog(PersonrelationDetailActivity.this, array1, "值选择");
                dialog.setOnSelectTimeSetListener(new DateTimePickerDialog.OnSelectSetListener<String>() {
                    @Override
                    public void OnSelectSet(String select) {
                        TRANSPORT.setText(select);
                    }
                });
            }
        });

        STARTDATE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                if(null != s && !"".equals(s.toString())) {
                    String str = s.toString();
                    if(null != ENDDATE.getText() && !"".equals(ENDDATE.getText().toString())) {
                        Date dt1 = null;
                        Date dt2 = null;
                        try {
                            dt1 = df.parse(str);
                            dt2 = df.parse(ENDDATE.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(dt2.getTime() < dt1.getTime()) {
                            STARTDATE.setText("");
                            showMiddleToast(PersonrelationDetailActivity.this, getString(R.string.check_data));
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ENDDATE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                if(null != s && !"".equals(s.toString())) {
                    String str = s.toString();
                    if(null != STARTDATE.getText() && !"".equals(STARTDATE.getText().toString())) {
                        Date dt1 = null;
                        Date dt2 = null;
                        try {
                            dt1 = df.parse(STARTDATE.getText().toString());
                            dt2 = df.parse(str);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(dt2.getTime() < dt1.getTime()) {
                            ENDDATE.setText("");
                            showMiddleToast(PersonrelationDetailActivity.this, getString(R.string.check_data));
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        save_imageView_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeSave(personrelation);
            }
        });
    }

    public void beforeSave(R_PERSONRELATION.PERSONRELATION personrelation) {
        R_PERSONRELATION.PERSONRELATION personrelationSave = null;
        HashMap<String, Object> savemap = new HashMap<>();
        //人员检验
        if(null == PERSONID.getText().toString() || "".equals(PERSONID.getText().toString())) {
            showMiddleToast(PersonrelationDetailActivity.this, getString(R.string.text_PERSONID));
            return;
        }
        //出发地检验
        if(null == PLACEA.getText().toString() || "".equals(PLACEA.getText().toString())) {
            showMiddleToast(PersonrelationDetailActivity.this, getString(R.string.text_PLACEA));
            return;
        }
        //目的地检验
        if(null == PLACEZ.getText().toString() || "".equals(PLACEZ.getText().toString())) {
            showMiddleToast(PersonrelationDetailActivity.this, getString(R.string.text_PLACEZ));
            return;
        }
        //出发时间检验
        if(null == STARTDATE.getText().toString() || "".equals(STARTDATE.getText().toString())) {
            showMiddleToast(PersonrelationDetailActivity.this, getString(R.string.text_STARTDATE));
            return;
        }
        //到达时间检验
        if(null == ENDDATE.getText().toString() || "".equals(ENDDATE.getText().toString())) {
            showMiddleToast(PersonrelationDetailActivity.this, getString(R.string.text_ENDDATE));
            return;
        }
        //交通工具检验
        if(null == TRANSPORT.getText().toString() || "".equals(TRANSPORT.getText().toString())) {
            showMiddleToast(PersonrelationDetailActivity.this, getString(R.string.text_TRANSPORT));
            return;
        }

        try {
            personrelationSave = (R_PERSONRELATION.PERSONRELATION) personrelation.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        if(type.equals("insert")) {

        }else {
            savemap.put("PERSONRELATIONID", JsonUnit.convertStrToArray(personrelationSave.getPERSONRELATIONID())[0]);

        }
        savemap.put("WONUM", wonum);
        savemap.put("PERSONID", PERSONID.getText().toString());
        savemap.put("DISPLAYNAME", DISPLAYNAME.getText().toString());
        savemap.put("CUDEPT", CUDEPT.getText().toString());
        savemap.put("PLACEA", PLACEA.getText().toString());
        savemap.put("PLACEZ", PLACEZ.getText().toString());
        savemap.put("STARTDATE", STARTDATE.getText().toString());
        savemap.put("ENDDATE", ENDDATE.getText().toString());
        savemap.put("TRANSPORT", TRANSPORT.getText().toString());
        savemap.put("TRVCOST3", TRVCOST3.getText().toString());
        savemap.put("TRVCOST4", TRVCOST4.getText().toString());
        savemap.put("TRVCOST9", TRVCOST9.getText().toString());
        savemap.put("TRVCOST2", TRVCOST2.getText().toString());
        savemap.put("LINECOST", LINECOST.getText().toString());
        savemap.put("ISONLINE", ISONLINE.isChecked() == true ? "1" : "0");
        save(savemap);
    }

    public void save(HashMap<String, Object> savemap) {
        showLoadingDialog(getResources().getString(R.string.loading_hint));
        String jsonStr = null;
        try {
            jsonStr = HttpManager.savePersonrelation(AccountUtils.getpersonId(PersonrelationDetailActivity.this), mainid, JSON.toJSONString(savemap));
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
//                        if(workorders.getErrcode().equals("GLOBAL-S-0")) {
                        showMiddleToast(PersonrelationDetailActivity.this, workorders.getErrmsg());
//                        if(type.equals("insert")) {
//                            String id = result.substring(11, result.length());
//                            getNetWorkWorkOrder(id);
//                            type = "update";
//                        }
                        finish();
                        // 发送 String 类型事件
                        RxBus.getDefault().post(RefreshCode.Refresh1);
//                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadingDialog();
                    }
                });
    }

    private void getNetWorkWorkOrder(String id) {
        String data = HttpManager.getR_PERSONRELATIONByIDUrl(GlobalConfig.TRAVEL_APPID, AccountUtils.getpersonId(this), id, 1, 1);
        Log.e(TAG, "data" + data);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_PERSONRELATION.class) // 发起获取数据列表的请求，并解析到R_PERSONRELATION
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_PERSONRELATION>() {
                    @Override
                    public void accept(@NonNull R_PERSONRELATION r_personrelation) throws Exception {
                    }
                })

                .map(new Function<R_PERSONRELATION, R_PERSONRELATION.ResultBean>() {
                    @Override
                    public R_PERSONRELATION.ResultBean apply(@NonNull R_PERSONRELATION r_personrelation) throws Exception {

                        return r_personrelation.getResult();
                    }
                })
                .map(new Function<R_PERSONRELATION.ResultBean, List<R_PERSONRELATION.PERSONRELATION>>() {
                    @Override
                    public List<R_PERSONRELATION.PERSONRELATION> apply(@NonNull R_PERSONRELATION.ResultBean resultBean) throws Exception {
                        int totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<R_PERSONRELATION.PERSONRELATION>>() {
                    @Override
                    public void accept(@NonNull List<R_PERSONRELATION.PERSONRELATION> personrelationa) throws Exception {
                        showData(personrelationa.get(0));
                        personrelation = personrelationa.get(0);
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }

    //日期选择
    private View.OnClickListener STARTDATEOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new DateSelect(PersonrelationDetailActivity.this, STARTDATE).showDialog();
        }
    };

    //日期选择
    private View.OnClickListener ENDDATEOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new DateSelect(PersonrelationDetailActivity.this, ENDDATE).showDialog();
        }
    };

    @Override
    protected String getSubTitle() {
        return getString(R.string.gncc_detail_text);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GlobalConfig.PERSON_REQUESTCODE: //执行人
                if (resultCode == GlobalConfig.CUDEPT_REQUESTCODE) {
                    R_ZXPERSON.PERSON persion = (R_ZXPERSON.PERSON) data.getSerializableExtra("person");
                    String PERSONIDstr = JsonUnit.convertStrToArray(persion.getPERSONID())[0];
                    PERSONID.setText(JsonUnit.convertStrToArray(persion.getPERSONID())[0]);
                    DISPLAYNAME.setText(JsonUnit.convertStrToArray(persion.getDISPLAYNAME())[0]);
//                    UDPERSONTYPE.setText(JsonUnit.convertStrToArray(persion.getUDPERSONTYPE())[0]);
                    CUDEPT.setText(JsonUnit.convertStrToArray(persion.getCUDEPT())[0]);
                }
                break;
        }
    }
}
