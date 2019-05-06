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
import com.hqxh.fiamproperty.model.R_PAYCHECKLINE;
import com.hqxh.fiamproperty.model.R_PAYCHECKLINE.PAYCHECKLINE;
import com.hqxh.fiamproperty.model.R_PAYCHECKLINE.ResultBean;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.PaychecklineAdapter;
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
 * 付款验收－验收明细
 **/
public class PaychecklineActivity extends BaseListActivity {
    private static final String TAG = "PaychecklineActivity";


    private PaychecklineAdapter paychecklineAdapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private String appid; //appid
    private String paychecknum; //paychecknum


    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("appid")) {
            appid = getIntent().getExtras().getString("appid");
        }
        if (getIntent().hasExtra("paychecknum")) {
            paychecknum = getIntent().getExtras().getString("paychecknum");
        }


    }

    @Override
    protected String getSubTitle() {


        return getResources().getString(R.string.ysmx_text);

    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getPAYCHECKLINEUrl(appid, paychecknum, AccountUtils.getpersonId(this), curpage, showcount);
        Log.e(TAG, "data=" + data);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_PAYCHECKLINE.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_PAYCHECKLINE>() {
                    @Override
                    public void accept(@NonNull R_PAYCHECKLINE r_paycheckline) throws Exception {
                    }
                })

                .map(new Function<R_PAYCHECKLINE, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_PAYCHECKLINE r_paycheckline) throws Exception {

                        return r_paycheckline.getResult();
                    }
                })
                .map(new Function<ResultBean, List<PAYCHECKLINE>>() {
                    @Override
                    public List<PAYCHECKLINE> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PAYCHECKLINE>>() {
                    @Override
                    public void accept(@NonNull List<PAYCHECKLINE> prline) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                        if (prline == null || prline.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(prline);


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
        paychecklineAdapter.removeAll(paychecklineAdapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(PaychecklineActivity.this, getResources().getString(R.string.all_data_hint));
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

        initAdapter(new ArrayList<PAYCHECKLINE>());
        getData();

    }

    @Override
    protected void setOnClick() {
        searchText.setVisibility(View.GONE);

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<PAYCHECKLINE> list) {
        paychecklineAdapter = new PaychecklineAdapter(PaychecklineActivity.this, R.layout.list_item_ysmx, list);
        mRecyclerView.setAdapter(paychecklineAdapter);
        paychecklineAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(PaychecklineActivity.this, PaychecklineDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("paycheckline", (Serializable) paychecklineAdapter.getData().get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);

            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<PAYCHECKLINE> list) {
        paychecklineAdapter.addData(list);
    }


}
