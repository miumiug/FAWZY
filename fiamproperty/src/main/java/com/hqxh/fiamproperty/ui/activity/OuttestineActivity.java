package com.hqxh.fiamproperty.ui.activity;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_OUTTESTLINE;
import com.hqxh.fiamproperty.model.R_OUTTESTLINE.OUTTESTLINE;
import com.hqxh.fiamproperty.model.R_OUTTESTLINE.ResultBean;
import com.hqxh.fiamproperty.ui.adapter.OuttestineAdapter;
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
 * 差旅报销单-外出试验日志(补助明细)Activity
 **/
public class OuttestineActivity extends BaseListActivity {
    private static final String TAG = "OuttestineActivity";


    private OuttestineAdapter outtestineAdapter;

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
        String data = HttpManager.getOuttestineUrl(GlobalConfig.EXPENSES_APPID, AccountUtils.getpersonId(this), expensenum, curpage, showcount);
        Log.i(TAG, "data=" + data);
        Log.i(TAG, "url=" + GlobalConfig.HTTP_URL_SEARCH);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_OUTTESTLINE.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_OUTTESTLINE>() {
                    @Override
                    public void accept(@NonNull R_OUTTESTLINE r_outtestline) throws Exception {
                    }
                })

                .map(new Function<R_OUTTESTLINE, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_OUTTESTLINE r_outtestline) throws Exception {

                        return r_outtestline.getResult();
                    }
                })
                .map(new Function<ResultBean, List<OUTTESTLINE>>() {
                    @Override
                    public List<OUTTESTLINE> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        Log.e(TAG, "Totalresult=" + resultBean.getTotalresult());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<OUTTESTLINE>>() {
                    @Override
                    public void accept(@NonNull List<OUTTESTLINE> outtestline) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (outtestline == null || outtestline.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(outtestline);


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
        outtestineAdapter.removeAll(outtestineAdapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(OuttestineActivity.this, getResources().getString(R.string.all_data_hint));
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
        initAdapter(new ArrayList<OUTTESTLINE>());
        getData();

    }

    @Override
    protected void setOnClick() {

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<OUTTESTLINE> list) {
        outtestineAdapter = new OuttestineAdapter(OuttestineActivity.this, R.layout.list_item_outtestline, list);
        mRecyclerView.setAdapter(outtestineAdapter);
    }

    /**
     * 添加数据*
     */
    private void addData(final List<OUTTESTLINE> list) {
        outtestineAdapter.addData(list);
    }


}
