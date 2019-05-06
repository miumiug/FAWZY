package com.hqxh.fiamproperty.ui.activity;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_EXPENSELINE;
import com.hqxh.fiamproperty.model.R_EXPENSELINE.ResultBean;
import com.hqxh.fiamproperty.model.R_EXPENSELINE.EXPENSELINE;
import com.hqxh.fiamproperty.ui.adapter.ExpenselineAdapter;
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
 * 交通补助的Activity
 **/
public class ExpenselineActivity extends BaseListActivity {
    private static final String TAG = "SubsidiesActivity";


    private ExpenselineAdapter expenselineadapter;

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
        Log.e(TAG,"appid="+appid);
    }

    @Override
    protected String getSubTitle() {

        return getString(R.string.jtmx_text);
    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getCLMXUrl(appid, GlobalConfig.EXPENSELINE_NAME, AccountUtils.getpersonId(this), expensenum, curpage, showcount);
        Log.i(TAG, "data=" + data);
        Log.i(TAG, "url=" + GlobalConfig.HTTP_URL_SEARCH);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_EXPENSELINE.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_EXPENSELINE>() {
                    @Override
                    public void accept(@NonNull R_EXPENSELINE r_expenseline) throws Exception {
                    }
                })

                .map(new Function<R_EXPENSELINE, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_EXPENSELINE r_expenseline) throws Exception {

                        return r_expenseline.getResult();
                    }
                })
                .map(new Function<ResultBean, List<EXPENSELINE>>() {
                    @Override
                    public List<EXPENSELINE> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        Log.e(TAG, "Totalresult=" + resultBean.getTotalresult());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<EXPENSELINE>>() {
                    @Override
                    public void accept(@NonNull List<EXPENSELINE> expenseline) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (expenseline == null || expenseline.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(expenseline);


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
        expenselineadapter.removeAll(expenselineadapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(ExpenselineActivity.this, getResources().getString(R.string.all_data_hint));
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
        initAdapter(new ArrayList<EXPENSELINE>());
        getData();

    }

    @Override
    protected void setOnClick() {

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<EXPENSELINE> list) {
        int layoutResId = 0;
        if (appid.equals(GlobalConfig.EXPENSES_APPID)) {
            layoutResId = R.layout.list_item_cl_jtmx;
        } else if (appid.equals(GlobalConfig.EXPENSE_APPID)) {
            layoutResId = R.layout.list_item_by_bxmx;
        }

        expenselineadapter = new ExpenselineAdapter(ExpenselineActivity.this, layoutResId, list);
        expenselineadapter.setAppid(appid);
        mRecyclerView.setAdapter(expenselineadapter);
    }

    /**
     * 添加数据*
     */
    private void addData(final List<R_EXPENSELINE.EXPENSELINE> list) {
        expenselineadapter.addData(list);
    }


}
