package com.hqxh.fiamproperty.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.blankj.rxbus.RxBus;
import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.RxBus.RefreshCode;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_FCTASKRELATION;
import com.hqxh.fiamproperty.model.R_FCTASKRELATION.FCTASKRELATION;
import com.hqxh.fiamproperty.model.R_FCTASKRELATION.ResultBean;
import com.hqxh.fiamproperty.ui.activity.fcaskrelation.FctaskrelationSelectActivity;
import com.hqxh.fiamproperty.ui.adapter.FctaskrelationAdapter;
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
 * 项目费用管理-任务单 出差申请采购申请的预算明细的Activity
 **/
public class FctaskrelationActivity extends BaseListActivity {
    private static final String TAG = "FctaskrelationActivity";


    private FctaskrelationAdapter fctaskrelationAdapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private String searchName;//搜索名称
    private String searchValue;//搜索值
    private String title;//title
    private String ID;
    private String type;


    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("searchName")) {
            searchName = getIntent().getExtras().getString("searchName");
        }
        if (getIntent().hasExtra("searchValue")) {
            searchValue = getIntent().getExtras().getString("searchValue");
        }
        if (getIntent().hasExtra("ID")) {
            ID = getIntent().getExtras().getString("ID");
        }
        if (getIntent().hasExtra("title")) {
            title = getIntent().getExtras().getString("title");
        }
        if (getIntent().hasExtra("type")) {
            type = getIntent().getExtras().getString("type");
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
        String data = HttpManager.getFCTASKRELATIONURL(searchName, searchValue, AccountUtils.getpersonId(this), curpage, showcount);
        Log.e(TAG, "data=" + data);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_FCTASKRELATION.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_FCTASKRELATION>() {
                    @Override
                    public void accept(@NonNull R_FCTASKRELATION r_fctaskrelation) throws Exception {
                    }
                })

                .map(new Function<R_FCTASKRELATION, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_FCTASKRELATION r_fctaskrelation) throws Exception {

                        return r_fctaskrelation.getResult();
                    }
                })
                .map(new Function<ResultBean, List<FCTASKRELATION>>() {
                    @Override
                    public List<FCTASKRELATION> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        Log.e(TAG, "Totalresult=" + resultBean.getTotalresult());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FCTASKRELATION>>() {
                    @Override
                    public void accept(@NonNull List<FCTASKRELATION> paycheck) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (paycheck == null || paycheck.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(paycheck);
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
        fctaskrelationAdapter.removeAll(fctaskrelationAdapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(FctaskrelationActivity.this, getResources().getString(R.string.all_data_hint));
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
        // 注册 String 类型事件
        RxBus.getDefault().subscribe(this, new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                if (s.equals(RefreshCode.Refresh1)) {
                    onRefresh();
                }
            }
        });
        if(type.equals("insert")) {
            //新增按钮可见
            add_imageView_id.setVisibility(View.VISIBLE);
        }
        //设置新增监听
        add_imageView_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fctaskrelationAdapter.getData().size() > 0) {
                    showMiddleToast(FctaskrelationActivity.this, "您已经有关联的预算明细，请先删除。");
//                    return;
                }
                Intent intent = new Intent(FctaskrelationActivity.this, FctaskrelationSelectActivity.class);
                intent.putExtra("ID", ID);
                startActivityForResult(intent, 0);
            }
        });

        initAdapter(new ArrayList<FCTASKRELATION>());
        getData();

    }

    @Override
    protected void setOnClick() {
        searchText.setVisibility(View.GONE);
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<FCTASKRELATION> list) {
        fctaskrelationAdapter = new FctaskrelationAdapter(FctaskrelationActivity.this, R.layout.list_item_fctaskrelation, list);
        mRecyclerView.setAdapter(fctaskrelationAdapter);
    }

    /**
     * 添加数据*
     */
    private void addData(final List<FCTASKRELATION> list) {
        fctaskrelationAdapter.addData(list);
    }


}
