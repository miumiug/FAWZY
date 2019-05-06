package com.hqxh.fiamproperty.ui.activity;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_WFTRANSACTION;
import com.hqxh.fiamproperty.model.R_WFTRANSACTION.WFTRANSACTION;
import com.hqxh.fiamproperty.model.R_WFTRANSACTION.ResultBean;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.CcrAdapter;
import com.hqxh.fiamproperty.ui.adapter.WftransactionAdapter;
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
 * 审批记录的Activity
 **/
public class WftransactionActivity extends BaseListActivity {
    private static final String TAG = "WftransactionActivity";


    private WftransactionAdapter wftransactionadapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;


    private String ownertable; // 表名
    private String ownerid; // 表ID

    @Override
    protected void beforeInit() {
        super.beforeInit();
        ownertable = getIntent().getExtras().getString("ownertable");
        ownerid = getIntent().getExtras().getString("ownerid");

    }

    @Override
    protected String getSubTitle() {

        return getString(R.string.spjl_text);
    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getWFTRANSACTIONUrl(AccountUtils.getpersonId(this), ownertable, ownerid, curpage, showcount);
        Log.i(TAG, "data=" + data);
        Log.i(TAG, "url=" + GlobalConfig.HTTP_URL_SEARCH);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_WFTRANSACTION.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_WFTRANSACTION>() {
                    @Override
                    public void accept(@NonNull R_WFTRANSACTION r_wftransaction) throws Exception {
                    }
                })

                .map(new Function<R_WFTRANSACTION, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_WFTRANSACTION r_wftransaction) throws Exception {

                        return r_wftransaction.getResult();
                    }
                })
                .map(new Function<ResultBean, List<WFTRANSACTION>>() {
                    @Override
                    public List<WFTRANSACTION> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        Log.e(TAG, "Totalresult=" + resultBean.getTotalresult());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<WFTRANSACTION>>() {
                    @Override
                    public void accept(@NonNull List<WFTRANSACTION> wftransaction) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (wftransaction == null || wftransaction.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(wftransaction);


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
        wftransactionadapter.removeAll(wftransactionadapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(WftransactionActivity.this, getResources().getString(R.string.all_data_hint));
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
        initAdapter(new ArrayList<WFTRANSACTION>());
        getData();

    }

    @Override
    protected void setOnClick() {

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<WFTRANSACTION> list) {
        wftransactionadapter = new WftransactionAdapter(WftransactionActivity.this, R.layout.list_item_spjl, list);
        mRecyclerView.setAdapter(wftransactionadapter);
        wftransactionadapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<WFTRANSACTION> list) {
        wftransactionadapter.addData(list);
    }


}
