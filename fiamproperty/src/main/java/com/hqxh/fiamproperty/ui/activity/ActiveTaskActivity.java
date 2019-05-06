package com.hqxh.fiamproperty.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_Wfassignemt;
import com.hqxh.fiamproperty.model.R_Wfassignemt.ResultBean;
import com.hqxh.fiamproperty.model.R_Wfassignemt.Wfassignment;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.WfassignmentAdapter;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.hqxh.fiamproperty.unit.JsonUnit;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 待办任务的Activity
 **/
public class ActiveTaskActivity extends BaseListActivity {
    private static final String TAG = "ActiveTaskActivity";
    public static final int TASK_REQUESTCODE = 1000; //请求码
    public static final int TASK_RESULTCODE = 1001; //返回码

    private WfassignmentAdapter wfassignmentAdapter;
    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;
    private String assignstatus;
    private String title;

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("assignstatus")) {
            assignstatus = getIntent().getExtras().getString("assignstatus");
        }
        if (getIntent().hasExtra("title")) {
            title = getIntent().getExtras().getString("title");
        }
    }

    @Override
    protected String getSubTitle() {
        return "FiAM > " + title;
    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getWFASSIGNMENTUrl("", AccountUtils.getpersonId(this), assignstatus, curpage, showcount);
        Log.e(TAG, "data=" + data);
        Log.e(TAG, "url=" + GlobalConfig.HTTP_URL_SEARCH);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_Wfassignemt.class) // 发起获取数据列表的请求，并解析到R_Wfassignemt
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_Wfassignemt>() {
                    @Override
                    public void accept(@NonNull R_Wfassignemt RWfassignemt) throws Exception {
                    }
                })

                .map(new Function<R_Wfassignemt, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_Wfassignemt RWfassignemt) throws Exception {

                        return RWfassignemt.getResult();
                    }
                })
                .map(new Function<ResultBean, List<Wfassignment>>() {
                    @Override
                    public List<Wfassignment> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Wfassignment>>() {
                    @Override
                    public void accept(@NonNull List<Wfassignment> wfassignments) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (wfassignments == null || wfassignments.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(wfassignments);


                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        notLinearLayout.setVisibility(View.VISIBLE);
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                    }
                });
    }


    @Override
    public void onRefresh() {
        curpage = 1;
        wfassignmentAdapter.removeAll(wfassignmentAdapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(ActiveTaskActivity.this, getResources().getString(R.string.all_data_hint));
        } else {
            curpage++;
            getData();
        }
    }

    private void getLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    }
                });
            }
        }, 1000);
    }

    @Override
    protected void fillData() {
        initAdapter(new ArrayList<Wfassignment>());
        getData();
    }

    @Override
    protected void setOnClick() {
        searchText.setOnClickListener(searchTextOnClickListener);
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<Wfassignment> list) {
        wfassignmentAdapter = new WfassignmentAdapter(ActiveTaskActivity.this, R.layout.list_item_task, list);
        mRecyclerView.setAdapter(wfassignmentAdapter);
        wfassignmentAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Wfassignment wfassignment = (Wfassignment) wfassignmentAdapter.getData().get(position);
                String app = JsonUnit.convertStrToArray(wfassignment.getAPP())[0];
                Log.e(TAG, "app=" + app);
                Intent intent = getIntent();
                if (app.equals(GlobalConfig.GRWZ_APPID)) { //出门管理
                    intent.setClass(ActiveTaskActivity.this, GrDetailsActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.TRAVEL_APPID)) {//国内出差
                    intent.setClass(ActiveTaskActivity.this, GnWorkorderCopyActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.TRAVELS_APPID)) {//国外出差
                    intent.setClass(ActiveTaskActivity.this, CgWorkorderActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.JSPR_APPID)) {//技术采购申请
                    intent.setClass(ActiveTaskActivity.this, JsPrActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.SZPR_APPID)) {//试制采购申请
                    intent.setClass(ActiveTaskActivity.this, SzPrActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.PR_APPID)) {//物资采购申请
                    intent.setClass(ActiveTaskActivity.this, WzPrActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.FWPR_APPID)) {//服务采购申请
                    intent.setClass(ActiveTaskActivity.this, FwpaydetailsActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.WPPR_APPID)) {//外培采购申请
                    intent.setClass(ActiveTaskActivity.this, WppaydetailsActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.TOSY_APPID)) {//试验任务单
                    intent.setClass(ActiveTaskActivity.this, SyrwdWorkorderActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.TOSZ_APPID)) {//试制任务单
                    intent.setClass(ActiveTaskActivity.this, SzrwdWorkorderActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.TOLL_APPID)) {//物资领料单
                    intent.setClass(ActiveTaskActivity.this, WzlldWorkorderActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.TODJ_APPID)) {//调件任务单
                    intent.setClass(ActiveTaskActivity.this, DjrwdWorkorderActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.TOOIL_APPID)) {//燃油申请单
                    intent.setClass(ActiveTaskActivity.this, RyrwdWorkorderActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.TOQT_APPID)) {//其它任务单
                    intent.setClass(ActiveTaskActivity.this, QtrwdWorkorderActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.CONTPURCH_APPID)) {//合同
                    intent.setClass(ActiveTaskActivity.this, PurchviewActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.PAYCHECK_APPID)) {//付款验收
                    intent.setClass(ActiveTaskActivity.this, PaycheckActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.PP_APPID)) {//需款计划
                    intent.setClass(ActiveTaskActivity.this, XkplandetailActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.EXPENSES_APPID)) {//差旅报销单
                    intent.setClass(ActiveTaskActivity.this, ExpenseActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.EXPENSE_APPID)) { //备用金报销单
                    intent.setClass(ActiveTaskActivity.this, ByExpenseActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else if (app.equals(GlobalConfig.BO_APPID)) { //报销
                    intent.setClass(ActiveTaskActivity.this, BoActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, TASK_REQUESTCODE);
                } else {
                    String msgkey = "";
                    if (app.equals("ASSETYC")) { msgkey = "样车";
                    } else if (app.equals("ASSETYJ")) { msgkey = "样机";
                    } else if (app.equals("BGCHANGE")) { msgkey = "预算变更";
                    } else if (app.equals("BKCHANGE")) { msgkey = "拨款项目变更";
                    } else if (app.equals("BKFINCNTRL")) { msgkey = "拨款项目";
                    } else if (app.equals("BORETURN")) { msgkey = "退款单";
                    } else if (app.equals("BUDGET")) { msgkey = "预算管理";
                    } else if (app.equals("BUSICHANGE")) { msgkey = "业务变更申请";
                    } else if (app.equals("CHECKASSET")) { msgkey = "投资项目验收";
                    } else if (app.equals("CHECKBK")) { msgkey = "拨款合同验收";
                    } else if (app.equals("CUSINGTZPR")) { msgkey = "投资项目采购计划";
                    } else if (app.equals("EXPENSE")) { msgkey = "备用金报销单";
                    } else if (app.equals("EXPENSEB")) { msgkey = "道路试验补助";
                    } else if (app.equals("EXPENSEG")) { msgkey = "公出误餐报销单";
                    } else if (app.equals("EXPENSERTN")) { msgkey = "银行退款单";
                    } else if (app.equals("EXPENSES")) { msgkey = "差旅报销单";
                    } else if (app.equals("EXPENSEZ")) { msgkey = "记账单";
                    } else if (app.equals("FINCNTRL")) { msgkey = "项目费用管理";
                    } else if (app.equals("FIRERECTIF")) { msgkey = "隐患整改通知单";
                    } else if (app.equals("FUNDAPPLY")) { msgkey = "资金领拨单";
                    } else if (app.equals("GHMR")) { msgkey = "IT设备归还登记";
                    } else if (app.equals("GRIDWO")) { msgkey = "设备维保单";
                    } else if (app.equals("GRTPAY")) { msgkey = "质保金付款审批单";
                    } else if (app.equals("GZKSR")) { msgkey = "工资卡号变更";
                    } else if (app.equals("ITAUTHORIT")) { msgkey = "IT系统使用权限申请";
                    } else if (app.equals("ITDBREQ")) { msgkey = "工作补救单";
                    } else if (app.equals("ITNEWMR")) { msgkey = "IT设备发放单";
                    } else if (app.equals("ITPART")) { msgkey = "IT设备备件申请";
                    } else if (app.equals("ITPARTIN")) { msgkey = "IT设备备件入库申请";
                    } else if (app.equals("ITSR")) { msgkey = "服务申请单";
                    } else if (app.equals("ITTECH")) { msgkey = "IT技术规格书";
                    } else if (app.equals("ITTS")) { msgkey = "IT技术服务管理";
                    } else if (app.equals("ITUSELESS")) { msgkey = "IT设备报废申请";
                    } else if (app.equals("LYMR")) { msgkey = "IT设备借领用申请";
                    } else if (app.equals("PAYCHECK")) { msgkey = "付款验收";
                    } else if (app.equals("PCHKHY")) { msgkey = "车辆租赁验收单";
                    } else if (app.equals("PPCHANGE")) { msgkey = "需款变更";
                    } else if (app.equals("PTPRICE")) { msgkey = "IT备件价格申请";
                    } else if (app.equals("PURCONTRA")) { msgkey = "投资项目采购合同";
                    } else if (app.equals("PZPR")) { msgkey = "保险牌照申请";
                    } else if (app.equals("QTPR")) { msgkey = "其它采购申请";
                    } else if (app.equals("REALPAY")) { msgkey = "付款审批单";
                    } else if (app.equals("REQCHANGE")) { msgkey = "预算变更汇总";
                    } else if (app.equals("REQPAY")) { msgkey = "需款申请单";
                    } else if (app.equals("REQSUM")) { msgkey = "需款申请汇总";
                    } else if (app.equals("SOSZ")) { msgkey = "试制计划单";
                    } else if (app.equals("SZAMB")) { msgkey = "专用件预算";
                    } else if (app.equals("SZCHANGE")) { msgkey = "试制预算变更";
                    } else if (app.equals("SZDZP")) { msgkey = "呆滞品出库单";
                    } else if (app.equals("SZPR")) { msgkey = "试制采购申请";
                    } else if (app.equals("TECHSPEC")) { msgkey = "技术规格书";
                    } else if (app.equals("TRAVELT")) { msgkey = "试验出差申请";
                    } else if (app.equals("TZTRAVEL")) { msgkey = "国内出差申请(投资)";
                    } else if (app.equals("TZTRAVELS")) { msgkey = "出国立项申请(投资)";
                    } else if (app.equals("UDERPMGM")) { msgkey = "设备资源计划";
                    } else if (app.equals("UDERPREQ")) { msgkey = "设备资源计划申请";
                    } else if (app.equals("UDERPRFC")) { msgkey = "设备资源计划变更";
                    } else if (app.equals("UDJDREAL")) { msgkey = "检定确认";
                    } else if (app.equals("UDJDREQ")) { msgkey = "计划外检定";
                    } else if (app.equals("UDZC")) { msgkey = "资产转出";
                    } else if (app.equals("UDZG")) { msgkey = "资产转固";
                    } else if (app.equals("UDZZ")) { msgkey = "资产增值";
                    } else if (app.equals("VEHIPASS")) { msgkey = "车辆通行证";
                    } else if (app.equals("VISITORS")) { msgkey = "访客单";
                    } else if (app.equals("WOTRACK")) { msgkey = "工单跟踪";
                    } else if (app.equals("WXPR")) { msgkey = "车辆维修申请";
                    } else if (app.equals("YBGH")) { msgkey = "仪表设备归还";
                    } else if (app.equals("YBJD")) { msgkey = "检定计划与确认";
                    } else if (app.equals("YBJHW")) { msgkey = "计划外与延期检定";
                    } else if (app.equals("YBKP")) { msgkey = "员工卡申请";
                    } else if (app.equals("YBLY")) { msgkey = "仪表设备借领用";
                    } else if (app.equals("YCISSUE")) { msgkey = "样车借用和归还";
                    } else if (app.equals("YCREISSUE")) { msgkey = "样车续借";
                    } else if (app.equals("YCUSELESS")) { msgkey = "样车报废";
                    } else if (app.equals("YJISSUE")) { msgkey = "样机借领用和归还";
                    } else if (app.equals("YJUSELESS")) { msgkey = "样机报废";
                    }
                    if (msgkey.equals("")) {
                        msgkey = getResources().getString(R.string.have_not_appove_hint);
                    } else {
                        msgkey += getResources().getString(R.string.have_not_appove_hint2);
                    }
//                    showMiddleToast1(ActiveTaskActivity.this, getString(R.string.have_not_appove_hint));
                    AlertDialog alertDialog1 = new AlertDialog.Builder(ActiveTaskActivity.this)
                            .setTitle(R.string.dialog_info)  //标题
                            .setMessage(msgkey)  //内容
                            .setIcon(R.mipmap.blueicon)//图标
                            .create();
                    alertDialog1.show();
                }
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<Wfassignment> list) {
        wfassignmentAdapter.addData(list);
    }


    /**
     * 跳转事件
     **/
    private View.OnClickListener searchTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(ActiveTaskActivity.this, SearchActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TASK_REQUESTCODE:
                if (resultCode == TASK_RESULTCODE) {//刷新界面
                    //显示下拉刷新
                    mPullLoadMoreRecyclerView.setRefreshing(true);
                    wfassignmentAdapter.removeAll(wfassignmentAdapter.getData());
                    if (notLinearLayout.isShown()) {
                        notLinearLayout.setVisibility(View.GONE);
                    }
                    getData();
                }
        }
    }
}
