package com.hqxh.fiamproperty.ui.activity;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_SUBSIDIES;
import com.hqxh.fiamproperty.model.R_SUBSIDIES.ResultBean;
import com.hqxh.fiamproperty.model.R_SUBSIDIES.SUBSIDIES;
import com.hqxh.fiamproperty.ui.adapter.SubsidiesAdapter;
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
 * 补助明细的Activity
 **/
public class SubsidiesActivity extends BaseListActivity {
    private static final String TAG = "SubsidiesActivity";


    private SubsidiesAdapter subsidiesadapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private String expensenum;

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("expensenum")) {
            expensenum = getIntent().getExtras().getString("expensenum");
        }
    }

    @Override
    protected String getSubTitle() {

        return getString(R.string.bzmx_text);
    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getSUBSIDIESUrl(GlobalConfig.EXPENSES_APPID, AccountUtils.getpersonId(this), expensenum, curpage, showcount);
        Log.i(TAG, "data=" + data);
        Log.i(TAG, "url=" + GlobalConfig.HTTP_URL_SEARCH);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_SUBSIDIES.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_SUBSIDIES>() {
                    @Override
                    public void accept(@NonNull R_SUBSIDIES r_subsidies) throws Exception {
                    }
                })

                .map(new Function<R_SUBSIDIES, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_SUBSIDIES r_subsidies) throws Exception {

                        return r_subsidies.getResult();
                    }
                })
                .map(new Function<ResultBean, List<SUBSIDIES>>() {
                    @Override
                    public List<SUBSIDIES> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        Log.e(TAG, "Totalresult=" + resultBean.getTotalresult());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SUBSIDIES>>() {
                    @Override
                    public void accept(@NonNull List<SUBSIDIES> purchview) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (purchview == null || purchview.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(purchview);


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
        subsidiesadapter.removeAll(subsidiesadapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(SubsidiesActivity.this, getResources().getString(R.string.all_data_hint));
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
        initAdapter(new ArrayList<SUBSIDIES>());
        getData();

    }

    @Override
    protected void setOnClick() {

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<SUBSIDIES> list) {
        subsidiesadapter = new SubsidiesAdapter(SubsidiesActivity.this, R.layout.list_item_cl_bzmx, list);
        mRecyclerView.setAdapter(subsidiesadapter);
    }

    /**
     * 添加数据*
     */
    private void addData(final List<SUBSIDIES> list) {
        subsidiesadapter.addData(list);
    }


}
