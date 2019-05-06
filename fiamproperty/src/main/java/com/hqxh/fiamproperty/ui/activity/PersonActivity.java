package com.hqxh.fiamproperty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_ZXPERSON;
import com.hqxh.fiamproperty.model.R_ZXPERSON.PERSON;
import com.hqxh.fiamproperty.model.R_ZXPERSON.ResultBean;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.PersonAdapter;
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
 * 执行人选择项
 **/
public class PersonActivity extends BaseListActivity {
    private static final String TAG = "PersonActivity";


    private PersonAdapter personAdapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private String cudept; //部门
    private String cucrew; //科室
    private String appid; //appid
    private String title;//title


    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("cudept")) {
            cudept = getIntent().getExtras().getString("cudept");
        }
        if (getIntent().hasExtra("cucrew")) {
            cucrew = getIntent().getExtras().getString("cucrew");
        }
        if (getIntent().hasExtra("appid")) {
            appid = getIntent().getExtras().getString("appid");
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
        String data = HttpManager.getPERSONUrl(appid, cudept, cucrew, AccountUtils.getpersonId(this), curpage, showcount);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_ZXPERSON.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_ZXPERSON>() {
                    @Override
                    public void accept(@NonNull R_ZXPERSON r_zxperson) throws Exception {
                    }
                })

                .map(new Function<R_ZXPERSON, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_ZXPERSON r_zxperson) throws Exception {

                        return r_zxperson.getResult();
                    }
                })
                .map(new Function<ResultBean, List<PERSON>>() {
                    @Override
                    public List<PERSON> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PERSON>>() {
                    @Override
                    public void accept(@NonNull List<PERSON> cudept) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (cudept == null || cudept.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(cudept);


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
        personAdapter.removeAll(personAdapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(PersonActivity.this, getResources().getString(R.string.all_data_hint));
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

        initAdapter(new ArrayList<PERSON>());
        getData();

    }

    @Override
    protected void setOnClick() {
        searchText.setOnClickListener(searchTextOnClickListener);

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<PERSON> list) {
        personAdapter = new PersonAdapter(PersonActivity.this, R.layout.list_item_ccr, list);
        mRecyclerView.setAdapter(personAdapter);
        personAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("person", (Serializable) personAdapter.getData().get(position));
                intent.putExtras(bundle);
                setResult(GlobalConfig.CUDEPT_REQUESTCODE, intent);
                finish();
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<PERSON> list) {
        personAdapter.addData(list);
    }


    /**
     * 跳转事件
     **/
    private View.OnClickListener searchTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(PersonActivity.this, PersonSearchActivity.class);
            startActivityForResult(intent, 0);
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case GlobalConfig.CUDEPT_REQUESTCODE:
                setResult(GlobalConfig.CUDEPT_REQUESTCODE, data);
                finish();
                break;
        }
    }
}
