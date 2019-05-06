package com.hqxh.fiamproperty.ui.activity.fcaskrelation;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.blankj.rxbus.RxBus;
import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.RxBus.RefreshCode;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.LoginResults;
import com.hqxh.fiamproperty.model.R_FCTASKRELATION;
import com.hqxh.fiamproperty.model.R_FCTASKRELATION.FCTASKRELATION;
import com.hqxh.fiamproperty.model.R_FCTASKRELATION.ResultBean;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.FctaskrelationAdapter;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.hqxh.fiamproperty.unit.JsonUnit;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 项目费用管理-任务单 出差申请采购申请的预算明细的Activity
 **/
public class FctaskrelationSelectActivity extends BaseListActivity {
    private static final String TAG = "FctaskrelationActivity";


    private FctaskrelationAdapter fctaskrelationAdapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;


    private String ID;


    @Override
    protected void beforeInit() {
        super.beforeInit();

        if (getIntent().hasExtra("ID")) {
            ID = getIntent().getExtras().getString("ID");
        }
    }

    @Override
    protected String getSubTitle() {

        return "项目预算选择";
    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getFCTASKRELATIONSELECTURL(AccountUtils.getpersonId(this), curpage, showcount);
        Log.e(TAG, "data=" + data);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_FCTASKRELATION.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_FCTASKRELATION>() {
                    @Override
                    public void accept(@NonNull R_FCTASKRELATION r_fctaskrelation) throws Exception {
                    }
                })

                .map(new Function<R_FCTASKRELATION, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_FCTASKRELATION r_fctaskrelation) throws Exception {

                        return r_fctaskrelation.getResult();
                    }
                })
                .map(new Function<ResultBean, List<FCTASKRELATION>>() {
                    @Override
                    public List<FCTASKRELATION> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        Log.e(TAG, "Totalresult=" + resultBean.getTotalresult());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FCTASKRELATION>>() {
                    @Override
                    public void accept(@NonNull List<FCTASKRELATION> paycheck) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (paycheck == null || paycheck.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(paycheck);


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
        fctaskrelationAdapter.removeAll(fctaskrelationAdapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(FctaskrelationSelectActivity.this, getResources().getString(R.string.all_data_hint));
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

        initAdapter(new ArrayList<FCTASKRELATION>());
        getData();

    }

    @Override
    protected void setOnClick() {
        searchText.setVisibility(View.GONE);
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<FCTASKRELATION> list) {
        fctaskrelationAdapter = new FctaskrelationAdapter(FctaskrelationSelectActivity.this, R.layout.list_item_fctaskrelation, list);
        mRecyclerView.setAdapter(fctaskrelationAdapter);
        fctaskrelationAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectSave((FCTASKRELATION) fctaskrelationAdapter.getData().get(position));
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<FCTASKRELATION> list) {
        fctaskrelationAdapter.addData(list);
    }

    /**
     *选择保存
     */
    private void selectSave(FCTASKRELATION fctaskrelation){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("TASKID", JsonUnit.convertStrToArray(fctaskrelation.getTASKID())[0]);
        hashMap.put("DESCRIPTION", JsonUnit.convertStrToArray(fctaskrelation.getDESCRIPTION())[0]);
        hashMap.put("TASKTYPEDESC", JsonUnit.convertStrToArray(fctaskrelation.getTASKTYPEDESC())[0]);
        hashMap.put("FCYEAR", JsonUnit.convertStrToArray(fctaskrelation.getFCYEAR())[0]);
        String jsonStr = HttpManager.getFCTASKRELATIONSELECTSAVEURL(ID, JSON.toJSONString(hashMap), AccountUtils.getpersonId(this), curpage, showcount);
        showLoadingDialog(getResources().getString(R.string.loading_hint));
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
                        showMiddleToast(FctaskrelationSelectActivity.this, workorders.getErrmsg());
                        finish();
                        // 发送 String 类型事件
                        RxBus.getDefault().post(RefreshCode.Refresh1);
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadingDialog();
                    }
                });
    }
}
