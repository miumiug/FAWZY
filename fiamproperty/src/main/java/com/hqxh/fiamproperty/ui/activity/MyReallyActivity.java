package com.hqxh.fiamproperty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.base.BaseTitleActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_EXPENSE;
import com.hqxh.fiamproperty.model.R_PAYCHECKLINE;
import com.hqxh.fiamproperty.model.R_WORKREPORT;
import com.hqxh.fiamproperty.model.R_WORKREPORTLINE;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.PaychecklineAdapter;
import com.hqxh.fiamproperty.ui.adapter.WorkReportLineAdapter;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.hqxh.fiamproperty.unit.JsonUnit;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018\6\21 0021.
 */

public class MyReallyActivity extends BaseListActivity {
    private R_WORKREPORT.WORKREPORT workreport;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private WorkReportLineAdapter workReportLineAdapter;
    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("workreport")) {
            workreport = (R_WORKREPORT.WORKREPORT) getIntent().getSerializableExtra("workreport");
        }
    }
    @Override
    protected String getSubTitle() {
        return "写实详情";
    }


    @Override
    protected void fillData() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        initAdapter(new ArrayList<R_WORKREPORTLINE.WORKREPORTLINE>());
        getData(JsonUnit.convertStrToArray(workreport.getWRNUM())[0]);
    }

    @Override
    protected void setOnClick() {
        searchText.setImageResource(R.drawable.ic_docu);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyReallyActivity.this, DocuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("wrnum",JsonUnit.convertStrToArray(workreport.getWRNUM())[0]);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void getData(String wrnum) {
        String data = HttpManager.getMyProjectDetail(AccountUtils.getpersonId(this), wrnum, curpage, showcount);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_WORKREPORTLINE.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_WORKREPORTLINE>() {
                    @Override
                    public void accept(@NonNull R_WORKREPORTLINE r_workdailylog) throws Exception {
                    }
                })

                .map(new Function<R_WORKREPORTLINE, R_WORKREPORTLINE.ResultBean>() {
                    @Override
                    public R_WORKREPORTLINE.ResultBean apply(@NonNull R_WORKREPORTLINE r_workdailylog) throws Exception {

                        return r_workdailylog.getResult();
                    }
                })
                .map(new Function<R_WORKREPORTLINE.ResultBean, List<R_WORKREPORTLINE.WORKREPORTLINE>>() {
                    @Override
                    public List<R_WORKREPORTLINE.WORKREPORTLINE> apply(@NonNull R_WORKREPORTLINE.ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<R_WORKREPORTLINE.WORKREPORTLINE>>() {
                    @Override
                    public void accept(@NonNull List<R_WORKREPORTLINE.WORKREPORTLINE> workorder) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                        if (workorder == null || workorder.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {
                            addData(workorder);
                            workReportLineAdapter.setWorkreport(workreport);
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
    /**
     * 获取数据*
     */
    private void initAdapter(final List<R_WORKREPORTLINE.WORKREPORTLINE> list) {
        workReportLineAdapter = new WorkReportLineAdapter(MyReallyActivity.this, R.layout.activity_myproject_detail, list);
        mRecyclerView.setAdapter(workReportLineAdapter);
    }
    /**
     * 添加数据*
     */
    private void addData(final List<R_WORKREPORTLINE.WORKREPORTLINE> list) {
        workReportLineAdapter.addData(list);
    }

    @Override
    public void onRefresh() {
        curpage = 1;
        workReportLineAdapter.removeAll(workReportLineAdapter.getData());
        getData(JsonUnit.convertStrToArray(workreport.getWRNUM())[0]);

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(MyReallyActivity.this, getResources().getString(R.string.all_data_hint));
        } else {
            curpage++;
            getData(JsonUnit.convertStrToArray(workreport.getWRNUM())[0]);
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
}
