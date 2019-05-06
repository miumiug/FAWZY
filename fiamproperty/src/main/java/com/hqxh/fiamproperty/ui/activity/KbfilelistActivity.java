package com.hqxh.fiamproperty.ui.activity;

import android.os.Handler;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_KBFILELIST;
import com.hqxh.fiamproperty.model.R_KBFILELIST.KBFILELIST;
import com.hqxh.fiamproperty.model.R_KBFILELIST.ResultBean;
import com.hqxh.fiamproperty.ui.adapter.KbfilelistAdapter;
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
 * 出国立项申请-出国人员知识积累拟交付资料清单的Activity
 **/
public class KbfilelistActivity extends BaseListActivity {
    private static final String TAG = "KbfilelistActivity";


    private KbfilelistAdapter kbfilelistAdapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private String appid; //appid
    private String wonum; //appid
    private String title; //title

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("appid")) {
            appid = getIntent().getExtras().getString("appid");
        }
        if (getIntent().hasExtra("title")) {
            title = getIntent().getExtras().getString("title");
        }
        if (getIntent().hasExtra("wonum")) {
            wonum = getIntent().getExtras().getString("wonum");
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
        String data = HttpManager.getKBFILELISTUrl(appid, wonum, AccountUtils.getpersonId(this), curpage, showcount);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_KBFILELIST.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_KBFILELIST>() {
                    @Override
                    public void accept(@NonNull R_KBFILELIST r_kbfilelist) throws Exception {
                    }
                })

                .map(new Function<R_KBFILELIST, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_KBFILELIST r_paycheck) throws Exception {

                        return r_paycheck.getResult();
                    }
                })
                .map(new Function<ResultBean, List<KBFILELIST>>() {
                    @Override
                    public List<KBFILELIST> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<KBFILELIST>>() {
                    @Override
                    public void accept(@NonNull List<KBFILELIST> kbfilelist) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (kbfilelist == null || kbfilelist.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(kbfilelist);


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
        kbfilelistAdapter.removeAll(kbfilelistAdapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(KbfilelistActivity.this, getResources().getString(R.string.all_data_hint));
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
        initAdapter(new ArrayList<KBFILELIST>());
        getData();

    }

    @Override
    protected void setOnClick() {
        searchText.setVisibility(View.GONE);
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<KBFILELIST> list) {
        kbfilelistAdapter = new KbfilelistAdapter(KbfilelistActivity.this, R.layout.list_item_kbfilelist, list);
        mRecyclerView.setAdapter(kbfilelistAdapter);
    }

    /**
     * 添加数据*
     */
    private void addData(final List<KBFILELIST> list) {
        kbfilelistAdapter.addData(list);
    }


}
