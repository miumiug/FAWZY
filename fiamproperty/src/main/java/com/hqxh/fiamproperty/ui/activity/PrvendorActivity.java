package com.hqxh.fiamproperty.ui.activity;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_PRVENDOR;
import com.hqxh.fiamproperty.model.R_PRVENDOR.PRVENDOR;
import com.hqxh.fiamproperty.model.R_PRVENDOR.ResultBean;
import com.hqxh.fiamproperty.ui.adapter.PrvendorAdapter;
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
 * 潜在供应商
 **/
public class PrvendorActivity extends BaseListActivity {
    private static final String TAG = "PrvendorActivity";


    private PrvendorAdapter prvendoradapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private String appid; //appid
    private String prnum; //prnum


    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("appid")) {
            appid = getIntent().getExtras().getString("appid");
        }
        if (getIntent().hasExtra("prnum")) {
            prnum = getIntent().getExtras().getString("prnum");
        }


    }

    @Override
    protected String getSubTitle() {


        return getResources().getString(R.string.qzgys_text);

    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getPRVENDORUrl(appid, prnum, AccountUtils.getpersonId(this), curpage, showcount);
        Log.e(TAG, "data=" + data);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_PRVENDOR.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_PRVENDOR>() {
                    @Override
                    public void accept(@NonNull R_PRVENDOR r_prvendor) throws Exception {
                    }
                })

                .map(new Function<R_PRVENDOR, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_PRVENDOR r_zxperson) throws Exception {
                        Log.e(TAG, "" + r_zxperson.getErrcode() + "," + r_zxperson.getErrmsg());

                        return r_zxperson.getResult();
                    }
                })
                .map(new Function<ResultBean, List<PRVENDOR>>() {
                    @Override
                    public List<PRVENDOR> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        Log.e(TAG, "" + resultBean.getTotalpage() + "," + resultBean.getTotalresult());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PRVENDOR>>() {
                    @Override
                    public void accept(@NonNull List<PRVENDOR> prvendor) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                        if (prvendor == null || prvendor.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(prvendor);


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
        prvendoradapter.removeAll(prvendoradapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(PrvendorActivity.this, getResources().getString(R.string.all_data_hint));
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

        initAdapter(new ArrayList<PRVENDOR>());
        getData();

    }

    @Override
    protected void setOnClick() {
        searchText.setVisibility(View.GONE);

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<PRVENDOR> list) {
        prvendoradapter = new PrvendorAdapter(PrvendorActivity.this, R.layout.list_item_qzgys, list);
        mRecyclerView.setAdapter(prvendoradapter);
    }

    /**
     * 添加数据*
     */
    private void addData(final List<PRVENDOR> list) {
        prvendoradapter.addData(list);
    }


}
