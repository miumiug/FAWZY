package com.hqxh.fiamproperty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_CUDEPT;
import com.hqxh.fiamproperty.model.R_CUDEPT.CUDEPT;
import com.hqxh.fiamproperty.model.R_CUDEPT.ResultBean;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.CudeptAdapter;
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
 * 执行部门选择项
 **/
public class CudeptActivity extends BaseListActivity {
    private static final String TAG = "CudeptActivity";


    private CudeptAdapter cudeptAdapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private String title;

    private String appid;

    private String deptnum;

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("title")) {
            title = getIntent().getExtras().getString("title");
        }
        if (getIntent().hasExtra("appid")) {
            appid = getIntent().getExtras().getString("appid");
        }

        if (getIntent().hasExtra("deptnum")) {
            deptnum = getIntent().getExtras().getString("deptnum");
        } else {
            deptnum = null;
        }
    }

    @Override
    protected String getSubTitle() {


        return title;

    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getCUDEPTUrl(appid, deptnum, AccountUtils.getpersonId(this), curpage, showcount);
        Log.e(TAG, "data=" + data);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_CUDEPT.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_CUDEPT>() {
                    @Override
                    public void accept(@NonNull R_CUDEPT r_cudept) throws Exception {
                    }
                })

                .map(new Function<R_CUDEPT, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_CUDEPT r_cudept) throws Exception {

                        return r_cudept.getResult();
                    }
                })
                .map(new Function<ResultBean, List<CUDEPT>>() {
                    @Override
                    public List<CUDEPT> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CUDEPT>>() {
                    @Override
                    public void accept(@NonNull List<CUDEPT> cudept) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (cudept == null || cudept.isEmpty()) {

                        } else {

                            addData(cudept);


                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                        mPullLoadMoreRecyclerView.setRefreshing(false);
                    }
                });
    }


    @Override
    public void onRefresh() {
        curpage = 1;
        cudeptAdapter.removeAll(cudeptAdapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(CudeptActivity.this, getResources().getString(R.string.all_data_hint));
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

        initAdapter(new ArrayList<CUDEPT>());
        getData();

    }

    @Override
    protected void setOnClick() {
        searchText.setOnClickListener(searchTextOnClickListener);

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<CUDEPT> list) {
        cudeptAdapter = new CudeptAdapter(CudeptActivity.this, R.layout.list_item_cudept, list);
        mRecyclerView.setAdapter(cudeptAdapter);
        cudeptAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("cudept", (Serializable) cudeptAdapter.getData().get(position));
                intent.putExtras(bundle);
                setResult(GlobalConfig.CUDEPT_REQUESTCODE, intent);
                finish();
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<CUDEPT> list) {
        cudeptAdapter.addData(list);
    }


    /**
     * 跳转事件
     **/
    private View.OnClickListener searchTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(CudeptActivity.this, CudeptSearchActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case GlobalConfig.CUDEPT_REQUESTCODE:
                setResult(GlobalConfig.CUDEPT_REQUESTCODE, data);
                finish();
                break;
        }
    }
}
