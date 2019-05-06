package com.hqxh.fiamproperty.ui.activity;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_GRLINE;
import com.hqxh.fiamproperty.model.R_GRLINE.GRLINE;
import com.hqxh.fiamproperty.model.R_GRLINE.ResultBean;
import com.hqxh.fiamproperty.ui.adapter.WzmxAdapter;
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
 * 物资明细/整车明细
 **/
public class WzmxListActivity extends BaseListActivity {
    private static final String TAG = "WzmxListActivity";


    private WzmxAdapter wzmxadapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private String appid;//appid
    private String grnum;//grnum
    private String title;//title


    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("appid")) {
            appid = getIntent().getExtras().getString("appid");
        }
        if (getIntent().hasExtra("grnum")) {
            grnum = getIntent().getExtras().getString("grnum");
        }
        if (getIntent().hasExtra("title")) {
            title = getIntent().getExtras().getString("title");
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
        String data = HttpManager.getGRLINEUrl(appid, AccountUtils.getpersonId(this), grnum, curpage, showcount);
        Log.e(TAG, "data=" + data);

        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_GRLINE.class) // 发起获取数据列表的请求，并解析到R_Wfassignemt
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_GRLINE>() {
                    @Override
                    public void accept(@NonNull R_GRLINE r_grline) throws Exception {
                    }
                })

                .map(new Function<R_GRLINE, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_GRLINE r_grline) throws Exception {

                        return r_grline.getResult();
                    }
                })
                .map(new Function<ResultBean, List<GRLINE>>() {
                    @Override
                    public List<GRLINE> apply(@NonNull ResultBean resultBean) throws Exception {
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GRLINE>>() {
                    @Override
                    public void accept(@NonNull List<GRLINE> grline) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (grline == null || grline.isEmpty()) {

                        } else {

                            addData(grline);


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
        wzmxadapter.removeAll(wzmxadapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(WzmxListActivity.this, getResources().getString(R.string.all_data_hint));
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
        initAdapter(new ArrayList<GRLINE>());
        getData();

    }

    @Override
    protected void setOnClick() {
        searchText.setVisibility(View.GONE);

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<GRLINE> list) {
        wzmxadapter = new WzmxAdapter(WzmxListActivity.this, R.layout.list_item_wzmx, list);
        wzmxadapter.setAppid(appid);
        mRecyclerView.setAdapter(wzmxadapter);
    }

    /**
     * 添加数据*
     */
    private void addData(final List<GRLINE> list) {
        wzmxadapter.addData(list);
    }


}
