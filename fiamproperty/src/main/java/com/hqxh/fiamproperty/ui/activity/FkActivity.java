package com.hqxh.fiamproperty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_PAYCHECK;
import com.hqxh.fiamproperty.model.R_PAYCHECK.PAYCHECK;
import com.hqxh.fiamproperty.model.R_PAYCHECK.ResultBean;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.FkAdapter;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 付款验收的Activity
 **/
public class FkActivity extends BaseListActivity {
    private static final String TAG = "FkActivity";


    private FkAdapter fkAdapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;


    @Override
    protected String getSubTitle() {

        return "FiAM > " + getString(R.string.fkys_text);
    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getPAYCHECKUrl(GlobalConfig.PAYCHECK_APPID, "", AccountUtils.getpersonId(this), curpage, showcount);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_PAYCHECK.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_PAYCHECK>() {
                    @Override
                    public void accept(@NonNull R_PAYCHECK r_paycheck) throws Exception {
                    }
                })

                .map(new Function<R_PAYCHECK, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_PAYCHECK r_paycheck) throws Exception {

                        return r_paycheck.getResult();
                    }
                })
                .map(new Function<ResultBean, List<PAYCHECK>>() {
                    @Override
                    public List<PAYCHECK> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PAYCHECK>>() {
                    @Override
                    public void accept(@NonNull List<PAYCHECK> paycheck) throws Exception {
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
        fkAdapter.removeAll(fkAdapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(FkActivity.this, getResources().getString(R.string.all_data_hint));
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
        initAdapter(new ArrayList<PAYCHECK>());
        getData();

    }

    @Override
    protected void setOnClick() {
        searchText.setOnClickListener(searchTextOnClickListener);
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<PAYCHECK> list) {
        fkAdapter = new FkAdapter(FkActivity.this, R.layout.list_item_travel, list);
        mRecyclerView.setAdapter(fkAdapter);
        fkAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(FkActivity.this, PaycheckActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("paycheck", (Serializable) fkAdapter.getData().get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<PAYCHECK> list) {
        fkAdapter.addData(list);
    }


    /**
     * 跳转事件
     **/
    private View.OnClickListener searchTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(FkActivity.this, Fkys_SearchActivity.class);
            intent.putExtra("appid", GlobalConfig.PAYCHECK_APPID);
            startActivityForResult(intent, 0);
        }
    };

}
