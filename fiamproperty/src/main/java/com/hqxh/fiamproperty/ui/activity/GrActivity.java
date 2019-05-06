package com.hqxh.fiamproperty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_GR;
import com.hqxh.fiamproperty.model.R_GR.GR;
import com.hqxh.fiamproperty.model.R_GR.ResultBean;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.GrAdapter;
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
 * 出门的Activity
 **/
public class GrActivity extends BaseListActivity {
    private static final String TAG = "GrActivity";


    private GrAdapter grAdapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;


    @Override
    protected String getSubTitle() {

        return "FiAM > " + getString(R.string.cmgl_text);
    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getGRUrl(GlobalConfig.GRWZ_APPID, GlobalConfig.GR_NAME, "", AccountUtils.getpersonId(this), curpage, showcount);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_GR.class) // 发起获取数据列表的请求，并解析到R_GR.class
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_GR>() {
                    @Override
                    public void accept(@NonNull R_GR r_gr) throws Exception {
                    }
                })

                .map(new Function<R_GR, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_GR r_gr) throws Exception {

                        return r_gr.getResult();
                    }
                })
                .map(new Function<ResultBean, List<GR>>() {
                    @Override
                    public List<GR> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GR>>() {
                    @Override
                    public void accept(@NonNull List<GR> gr) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (gr == null || gr.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(gr);


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
        grAdapter.removeAll(grAdapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(GrActivity.this, getResources().getString(R.string.all_data_hint));
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
        initAdapter(new ArrayList<GR>());
        getData();

    }

    @Override
    protected void setOnClick() {
        searchText.setOnClickListener(searchTextOnClickListener);

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<GR> list) {
        grAdapter = new GrAdapter(GrActivity.this, R.layout.list_item_travel, list);
        mRecyclerView.setAdapter(grAdapter);
        grAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(GrActivity.this, GrDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("gr", (Serializable) grAdapter.getData().get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<GR> list) {
        grAdapter.addData(list);
    }


    /**
     * 跳转事件
     **/
    private View.OnClickListener searchTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(GrActivity.this, Cmgl_SearchActivity.class);
            intent.putExtra("appid", GlobalConfig.GRWZ_APPID);
            startActivityForResult(intent, 0);
        }
    };


}
