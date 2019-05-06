package com.hqxh.fiamproperty.ui.activity;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_PAYPLAN;
import com.hqxh.fiamproperty.model.R_PAYPLAN.PAYPLAN;
import com.hqxh.fiamproperty.ui.adapter.ZxkjhAdapter;
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
 * 子需款计划
 */

public class ZxkjhActivity extends BaseListActivity {
    private static final String TAG = "ZxkjhActivity";

    private ZxkjhAdapter zxkjhAdapter;
    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private String payplannum;
    ;//payplannum
    private String appid;
    ;//appid
    private String title;
    ;//title


    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("payplannum")) {
            payplannum = getIntent().getExtras().getString("payplannum");

        }
        if (getIntent().hasExtra("appid")) {
            appid = getIntent().getExtras().getString("appid");

        }
        if (getIntent().hasExtra("title")) {
            title = getIntent().getExtras().getString("title");

        }
    }

    @Override
    protected void fillData() {
        initAdapter(new ArrayList<PAYPLAN>());
        getData();

    }

    @Override
    protected void setOnClick() {
        searchText.setVisibility(View.GONE);
    }

    @Override
    protected String getSubTitle() {
        return title;
    }

    @Override
    public void onRefresh() {
        curpage = 1;
        zxkjhAdapter.removeAll(zxkjhAdapter.getData());
        getData();


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
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(this, getResources().getString(R.string.all_data_hint));
        } else {
            curpage++;
            getData();
        }


    }

    /**
     * 展示数据
     */
    private void initAdapter(final List<PAYPLAN> list) {
        zxkjhAdapter = new ZxkjhAdapter(ZxkjhActivity.this, R.layout.list_zxkjh, list);
        mRecyclerView.setAdapter(zxkjhAdapter);
    }

    /*获取数据*/
    private void getData() {
        String data = HttpManager.getPAYPLANUrl(appid, payplannum, AccountUtils.getpersonId(this), curpage, showcount);
        Log.e(TAG, "data=" + data);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_PAYPLAN.class) // 发起获取数据列表的请求，并解析到R_Wfassignemt
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_PAYPLAN>() {
                    @Override
                    public void accept(@NonNull R_PAYPLAN r_payplan) throws Exception {
                    }
                })

                .map(new Function<R_PAYPLAN, R_PAYPLAN.ResultBean>() {
                    @Override
                    public R_PAYPLAN.ResultBean apply(@NonNull R_PAYPLAN r_payplan) throws Exception {

                        return r_payplan.getResult();
                    }
                })
                .map(new Function<R_PAYPLAN.ResultBean, List<PAYPLAN>>() {
                    @Override
                    public List<PAYPLAN> apply(@NonNull R_PAYPLAN.ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PAYPLAN>>() {
                    @Override
                    public void accept(@NonNull List<PAYPLAN> payplan) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (payplan == null || payplan.isEmpty()) {

                        } else {

                            addData(payplan);


                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                        mPullLoadMoreRecyclerView.setRefreshing(false);
                    }
                });
    }

    /*  添加数据  */
    private void addData(final List<PAYPLAN> list) {
        zxkjhAdapter.addData(list);

    }
}
