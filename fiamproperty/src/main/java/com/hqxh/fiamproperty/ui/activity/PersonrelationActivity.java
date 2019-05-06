package com.hqxh.fiamproperty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.blankj.rxbus.RxBus;
import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.RxBus.RefreshCode;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_PERSONRELATION;
import com.hqxh.fiamproperty.model.R_PERSONRELATION.PERSONRELATION;
import com.hqxh.fiamproperty.model.R_PERSONRELATION.ResultBean;
import com.hqxh.fiamproperty.ui.activity.GNWorker.PersonrelationDetailActivity;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.CcrAdapter;
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
 * 出差人的Activity
 **/
public class PersonrelationActivity extends BaseListActivity {
    private static final String TAG = "PersonrelationActivity";


    private CcrAdapter ccrAdapter;


    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;


    private String wonum; //单号
    private String title;//标题
    private String appid;
    private String mainid;

    private String type;

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("wonum")) {
            wonum = getIntent().getExtras().getString("wonum");
        }
        if (getIntent().hasExtra("title")) {
            title = getIntent().getExtras().getString("title");
        }
        if (getIntent().hasExtra("appid")) {
            appid = getIntent().getExtras().getString("appid");
        }
        if (getIntent().hasExtra("mainid")) {
            mainid = getIntent().getExtras().getString("mainid");
        }
        if (getIntent().hasExtra("type")) {
            type = getIntent().getExtras().getString("type");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Rxbus监听返回并刷新页面
        // 注册 String 类型事件
        RxBus.getDefault().subscribe(this, new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                if (s.equals(RefreshCode.Refresh1)) {
                    onRefresh();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销
        RxBus.getDefault().unregister(this);
    }

    @Override
    protected String getSubTitle() {

        return title;
    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getR_PERSONRELATIONUrl(appid, AccountUtils.getpersonId(this), wonum, curpage, showcount);
        Log.e(TAG, "data" + data);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_PERSONRELATION.class) // 发起获取数据列表的请求，并解析到R_PERSONRELATION
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_PERSONRELATION>() {
                    @Override
                    public void accept(@NonNull R_PERSONRELATION r_personrelation) throws Exception {
                    }
                })

                .map(new Function<R_PERSONRELATION, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_PERSONRELATION r_personrelation) throws Exception {

                        return r_personrelation.getResult();
                    }
                })
                .map(new Function<ResultBean, List<PERSONRELATION>>() {
                    @Override
                    public List<PERSONRELATION> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PERSONRELATION>>() {
                    @Override
                    public void accept(@NonNull List<PERSONRELATION> personrelation) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (personrelation == null || personrelation.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(personrelation);
                            notLinearLayout.setVisibility(View.GONE);

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
        ccrAdapter.removeAll(ccrAdapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(PersonrelationActivity.this, getResources().getString(R.string.all_data_hint));
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
        if(type.equals("insert")) {
            add_imageView_id.setVisibility(View.VISIBLE);
        }
        add_imageView_id.setOnClickListener(add_imageView_idOnClickListener);
        searchText.setVisibility(View.GONE);
        initAdapter(new ArrayList<PERSONRELATION>());
        getData();

    }

    @Override
    protected void setOnClick() {
        searchText.setVisibility(View.GONE);
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<PERSONRELATION> list) {
        int layoutResId = 0;
        if (appid.equals(GlobalConfig.TRAVEL_APPID) || appid.equals(GlobalConfig.TRAVELS_APPID)) {
            layoutResId = R.layout.list_item_ccr;
        } else if (appid.equals(GlobalConfig.EXPENSES_APPID)) {
            layoutResId = R.layout.list_item_cl_ccr;
        }
        ccrAdapter = new CcrAdapter(PersonrelationActivity.this, layoutResId, list);
        ccrAdapter.setAppid(appid);

        mRecyclerView.setAdapter(ccrAdapter);
        ccrAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(PersonrelationActivity.this, PersonrelationDetailActivity.class);
                intent.putExtra("type", "update");
                intent.putExtra("mainid", mainid);
                intent.putExtra("wonum", wonum);
                intent.putExtra("PERSONRELATION", ccrAdapter.getItem(position));
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<PERSONRELATION> list) {
        ccrAdapter.addData(list);
    }

    private View.OnClickListener add_imageView_idOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PersonrelationActivity.this, PersonrelationDetailActivity.class);
            intent.putExtra("type", "insert");
            intent.putExtra("mainid", mainid);
            intent.putExtra("wonum", wonum);
            startActivityForResult(intent, 0);
        }
    };
}
