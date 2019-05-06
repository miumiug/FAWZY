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
import com.hqxh.fiamproperty.model.R_Workorder;
import com.hqxh.fiamproperty.model.R_Workorder.ResultBean;
import com.hqxh.fiamproperty.model.R_Workorder.Workorder;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.WorkOrderAdapter;
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
 * 任务单的Activity
 **/
public class RwdActivity extends BaseListActivity {
    private static final String TAG = "RwdActivity";

    public static final int RWD_SY = 1000;//试验任务单
    public static final int RWD_SZ = 1001;//试制任务单
    public static final int RWD_WZ = 1003;//物资领料单
    public static final int RWD_DJ = 1004;//调件任务单
    public static final int RWD_RY = 1005;//燃油申请单
    public static final int RWD_QT = 1006;//其它任务单

    private WorkOrderAdapter workOrderAdapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

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
        String data = HttpManager.getRWDUrl(appid, AccountUtils.getpersonId(this), curpage, showcount);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_Workorder.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_Workorder>() {
                    @Override
                    public void accept(@NonNull R_Workorder r_workorder) throws Exception {
                    }
                })

                .map(new Function<R_Workorder, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_Workorder r_workorder) throws Exception {

                        return r_workorder.getResult();
                    }
                })
                .map(new Function<ResultBean, List<Workorder>>() {
                    @Override
                    public List<Workorder> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Workorder>>() {
                    @Override
                    public void accept(@NonNull List<Workorder> workorders) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (workorders == null || workorders.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(workorders);


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
        workOrderAdapter.removeAll(workOrderAdapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(RwdActivity.this, getResources().getString(R.string.all_data_hint));
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


        initAdapter(new ArrayList<Workorder>());
        getData();

    }

    @Override
    protected void setOnClick() {
        searchText.setOnClickListener(searchTextOnClickListener);
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<Workorder> list) {
        workOrderAdapter = new WorkOrderAdapter(RwdActivity.this, R.layout.list_item_travel, list);
        mRecyclerView.setAdapter(workOrderAdapter);
        workOrderAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = null;
                Log.e(TAG, "appid=" + appid);
                if (appid.equals(GlobalConfig.TOSY_APPID)) {
                    intent = new Intent(RwdActivity.this, SyrwdWorkorderActivity.class);

                } else if (appid.equals(GlobalConfig.TOSZ_APPID)) {
                    intent = new Intent(RwdActivity.this, SzrwdWorkorderActivity.class);

                } else if (appid.equals(GlobalConfig.TOLL_APPID)) {
                    intent = new Intent(RwdActivity.this, WzlldWorkorderActivity.class);

                } else if (appid.equals(GlobalConfig.TODJ_APPID)) {
                    intent = new Intent(RwdActivity.this, DjrwdWorkorderActivity.class);

                } else if (appid.equals(GlobalConfig.TOOIL_APPID)) {
                    intent = new Intent(RwdActivity.this, RyrwdWorkorderActivity.class);

                } else if (appid.equals(GlobalConfig.TOQT_APPID)) {
                    intent = new Intent(RwdActivity.this, QtrwdWorkorderActivity.class);

                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("workorder", (Serializable) workOrderAdapter.getData().get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);

            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<Workorder> list) {
        workOrderAdapter.addData(list);
    }

    /**
     * 跳转事件
     **/
    private View.OnClickListener searchTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(RwdActivity.this, Rwd_SearchActivity.class);
            startActivityForResult(intent, 0);
        }
    };
}
