package com.hqxh.fiamproperty.ui.activity.GNWorker;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_Workorder;
import com.hqxh.fiamproperty.model.R_Workorder.ResultBean;
import com.hqxh.fiamproperty.ui.activity.GNWorker.adapter.TaskSelectAdapter;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
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
 * Created by wxs on 2019/3/14.
 */

public class TaskSelectActivity extends BaseListActivity {
    private static final String TAG = "HtActivity";
    private TaskSelectAdapter taskSelectAdapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    @Override
    protected void fillData() {
        initAdapter(new ArrayList<R_Workorder.Workorder>());
        getData();
    }

    /**
     * 获取数据*
     */
    private void initAdapter(final List<R_Workorder.Workorder> list) {
        taskSelectAdapter = new TaskSelectAdapter(TaskSelectActivity.this, R.layout.list_item_taskselect, list);
        mRecyclerView.setAdapter(taskSelectAdapter);
        taskSelectAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("workorder", (Serializable) taskSelectAdapter.getData().get(position));
                setResult(28, intent);
                finish();
            }
        });
    }

    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getMainTask(GlobalConfig.TOTALWK_APPID,  AccountUtils.getpersonId(this), curpage, showcount);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_Workorder.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_Workorder>() {
                    @Override
                    public void accept(@NonNull R_Workorder r_purchview) throws Exception {
                    }
                })

                .map(new Function<R_Workorder, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_Workorder r_purchview) throws Exception {

                        return r_purchview.getResult();
                    }
                })
                .map(new Function<ResultBean, List<R_Workorder.Workorder>>() {
                    @Override
                    public List<R_Workorder.Workorder> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        Log.e(TAG, "Totalresult=" + resultBean.getTotalresult());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<R_Workorder.Workorder>>() {
                    @Override
                    public void accept(@NonNull List<R_Workorder.Workorder> purchview) throws Exception {
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

    /**
     * 添加数据*
     */
    private void addData(final List<R_Workorder.Workorder> list) {
        taskSelectAdapter.addData(list);
    }

    @Override
    protected void setOnClick() {
        searchText.setOnClickListener(searchTextOnClickListener);
    }

    @Override
    protected String getSubTitle() {
        return getString(R.string.time24);
    }

    @Override
    public void onRefresh() {
        curpage = 1;
        taskSelectAdapter.removeAll(taskSelectAdapter.getData());
        getData();
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
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(TaskSelectActivity.this, getResources().getString(R.string.all_data_hint));
        } else {
            curpage++;
            getData();
        }
    }

    /**
     * 跳转事件
     **/
    private View.OnClickListener searchTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(TaskSelectActivity.this, TS_SearchActivity.class);
            intent.putExtra("appid", GlobalConfig.TOTALWK_APPID);
            startActivityForResult(intent, 28);
        }
    };

    /**
     * 接受返回的结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!(null == data || "".equals(data))) {
            if (requestCode == 28) {
                Intent intent = new Intent();
                intent.putExtra("workorder", (R_Workorder.Workorder) data.getSerializableExtra("workorder"));
                setResult(28, intent);
                finish();
            }
        }
    }
}
