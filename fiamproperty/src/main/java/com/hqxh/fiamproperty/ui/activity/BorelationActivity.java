package com.hqxh.fiamproperty.ui.activity;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_BORELATION;
import com.hqxh.fiamproperty.model.R_BORELATION.BORELATION;
import com.hqxh.fiamproperty.model.R_BORELATION.ResultBean;
import com.hqxh.fiamproperty.ui.adapter.BorelationAdapter;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *差旅报销单-借款单的Activity
 **/
public class BorelationActivity extends BaseListActivity {
    private static final String TAG = "BorelationActivity";


    private BorelationAdapter borelationadapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private String expensenum;
    private String appid;

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("expensenum")) {
            expensenum = getIntent().getExtras().getString("expensenum");
        }
        if (getIntent().hasExtra("appid")) {
            appid = getIntent().getExtras().getString("appid");
        }
    }

    @Override
    protected String getSubTitle() {
        return "FiAM > " + getString(R.string.jkd_text);
    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getCLMXUrl(appid, GlobalConfig.BORELATION_NAME,AccountUtils.getpersonId(this), expensenum, curpage, showcount);
        Log.i(TAG, "data=" + data);
        Log.i(TAG, "url=" + GlobalConfig.HTTP_URL_SEARCH);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_BORELATION.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_BORELATION>() {
                    @Override
                    public void accept(@NonNull R_BORELATION r_borelation) throws Exception {
                    }
                })

                .map(new Function<R_BORELATION, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_BORELATION r_expenseline) throws Exception {

                        return r_expenseline.getResult();
                    }
                })
                .map(new Function<ResultBean, List<BORELATION>>() {
                    @Override
                    public List<BORELATION> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        Log.e(TAG, "Totalresult=" + resultBean.getTotalresult());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BORELATION>>() {
                    @Override
                    public void accept(@NonNull List<BORELATION> borelation) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (borelation == null || borelation.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(borelation);


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
        borelationadapter.removeAll(borelationadapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(BorelationActivity.this, getResources().getString(R.string.all_data_hint));
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
        searchText.setVisibility(View.GONE);
        initAdapter(new ArrayList<BORELATION>());
        getData();

    }

    @Override
    protected void setOnClick() {

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<BORELATION> list) {
        borelationadapter = new BorelationAdapter(BorelationActivity.this, R.layout.list_item_cl_jkd, list);
        mRecyclerView.setAdapter(borelationadapter);
    }

    /**
     * 添加数据*
     */
    private void addData(final List<BORELATION> list) {
        borelationadapter.addData(list);
    }


}
