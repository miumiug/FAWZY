package com.hqxh.fiamproperty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_PR;
import com.hqxh.fiamproperty.model.R_PR.PR;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.PrAdapter;
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
 * 采购申请的Activity
 **/
public class PrActivity extends BaseListActivity {
    private static final String TAG = "PrActivity";

    public static final int PR_JSCGSQ = 1000; // 技术采购申请
    public static final int PR_SZCGSQ = 1001; //试制采购申请
    public static final int PR_WZCGSQ = 1002; //物资采购申请
    public static final int PR_FWCGSQ = 1003; //服务采购申请
    public static final int PR_WPCGSQ = 1004; //外培采购申请

    private PrAdapter prAdapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private int mark; //跳转标识

    private int titleRec; //标题
    private String appid; //Appid

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("titleRec")) {
            titleRec = getIntent().getExtras().getInt("titleRec");
        }
        if (getIntent().hasExtra("appid")) {
            appid = getIntent().getExtras().getString("appid");
        }


    }

    @Override
    protected String getSubTitle() {
        return getString(titleRec);


    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getPRUrl(appid, "", AccountUtils.getpersonId(this), curpage, showcount);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_PR.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_PR>() {
                    @Override
                    public void accept(@NonNull R_PR r_pr) throws Exception {
                    }
                })

                .map(new Function<R_PR, R_PR.ResultBean>() {
                    @Override
                    public R_PR.ResultBean apply(@NonNull R_PR r_pr) throws Exception {

                        return r_pr.getResult();
                    }
                })
                .map(new Function<R_PR.ResultBean, List<PR>>() {
                    @Override
                    public List<PR> apply(@NonNull R_PR.ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PR>>() {
                    @Override
                    public void accept(@NonNull List<PR> pr) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (pr == null || pr.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {
                            addData(pr);


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
        prAdapter.removeAll(prAdapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(PrActivity.this, getResources().getString(R.string.all_data_hint));
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


        initAdapter(new ArrayList<PR>());
        getData();

    }

    @Override
    protected void setOnClick() {
        searchText.setOnClickListener(searchTextOnClickListener);
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<PR> list) {
        prAdapter = new PrAdapter(PrActivity.this, R.layout.list_item_travel, list);
        prAdapter.setAppid(appid);
        mRecyclerView.setAdapter(prAdapter);

        prAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = null;
                if (appid.equals(GlobalConfig.JSPR_APPID)) {//技术
                    intent = new Intent(PrActivity.this, JsPrActivity.class);
                } else if (appid.equals(GlobalConfig.SZPR_APPID)) {//试制
                    intent = new Intent(PrActivity.this, SzPrActivity.class);
                } else if (appid.equals(GlobalConfig.PR_APPID)) {//物资
                    intent = new Intent(PrActivity.this, WzPrActivity.class);
                } else if (appid.equals(GlobalConfig.WPPR_APPID)) {//外培
                    intent = new Intent(PrActivity.this, WppaydetailsActivity.class);
                } else if (appid.equals(GlobalConfig.FWPR_APPID)) {//服务
                    intent = new Intent(PrActivity.this, FwpaydetailsActivity.class);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("pr", (Serializable) prAdapter.getData().get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);

            }


        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<PR> list) {
        prAdapter.addData(list);
    }

    /**
     * 跳转事件
     **/
    private View.OnClickListener searchTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(PrActivity.this, Pr_SearchActivity.class);
            intent.putExtra("appid", appid);
            startActivityForResult(intent, 0);
        }
    };
}
