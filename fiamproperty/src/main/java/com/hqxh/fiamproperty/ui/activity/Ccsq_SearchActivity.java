package com.hqxh.fiamproperty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_Workorder;
import com.hqxh.fiamproperty.model.R_Workorder.ResultBean;
import com.hqxh.fiamproperty.model.R_Workorder.Workorder;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.TravelAdapter;
import com.hqxh.fiamproperty.ui.widget.PullLoadMoreRecyclerView;
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
 * 出差申请搜索Activity
 **/
public class Ccsq_SearchActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    private static final String TAG = "Ccsq_SearchActivity";

    //返回按钮
    private ImageView backImage;
    private SearchView mSearchView;

    /**
     * RecyclerView
     **/
    protected PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    protected RecyclerView mRecyclerView;

    private LinearLayout notLinearLayout;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private TravelAdapter traveladapter;
    private String search;

    private String appid; //appid

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("appid")) {
            appid = getIntent().getExtras().getString("appid");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        backImage = (ImageView) findViewById(R.id.back_text_id);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        backImage.setOnClickListener(backImageOnClickListener);
        mSearchView.setIconifiedByDefault(false);
        if (mSearchView == null) {
            return;
        } else {
            //获取到TextView的ID
            int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            //获取到TextView的控件
            TextView textView = (TextView) mSearchView.findViewById(id);
            //设置字体大小为14sp
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);//14sp
            //设置字体颜色
            textView.setTextColor(getResources().getColor(R.color.white));
            //设置提示文字颜色
            textView.setHintTextColor(getResources().getColor(R.color.white));
        }


        mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) findViewById(R.id.pullLoadMoreRecyclerView);
        notLinearLayout = (LinearLayout) findViewById(R.id.have_not_data_id);
        //获取mRecyclerView对象
        mRecyclerView = mPullLoadMoreRecyclerView.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        //mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置是否可以下拉刷新
        //mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
        //设置是否可以上拉刷新
        //mPullLoadMoreRecyclerView.setPushRefreshEnable(false);
        //显示下拉刷新
        mPullLoadMoreRecyclerView.setRefreshing(false);
        //设置上拉刷新文字
//        mPullLoadMoreRecyclerView.setFooterViewText("loading");
        //设置上拉刷新文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        mPullLoadMoreRecyclerView.setLinearLayout();

        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);

        mSearchView.setOnQueryTextListener(mSearchViewOnQueryTextListener);

        initAdapter(new ArrayList<Workorder>());
    }


    private View.OnClickListener backImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };


    private SearchView.OnQueryTextListener mSearchViewOnQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            search = s;
            //显示下拉刷新
            mPullLoadMoreRecyclerView.setRefreshing(true);
            traveladapter.removeAll(traveladapter.getData());
            if (notLinearLayout.isShown()) {
                notLinearLayout.setVisibility(View.GONE);
            }
            getData(s);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    };


    @Override
    public void onRefresh() {
        curpage = 1;
        traveladapter.removeAll(traveladapter.getData());
        getData(search);

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(Ccsq_SearchActivity.this, getResources().getString(R.string.all_data_hint));
        } else {
            curpage++;
            getData(search);
        }
    }


    /**
     * 获取数据
     **/
    private void getData(String search) {
        String data = HttpManager.getSearchWORKORDERUrl(appid, GlobalConfig.WORKORDER_NAME, search, AccountUtils.getpersonId(Ccsq_SearchActivity.this), curpage, showcount);
        Log.e(TAG, "data=" + data);
        Log.e(TAG, "url=" + GlobalConfig.HTTP_URL_SEARCH);
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

                .map(new Function<R_Workorder, R_Workorder.ResultBean>() {
                    @Override
                    public R_Workorder.ResultBean apply(@NonNull R_Workorder r_workorder) throws Exception {

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
                    public void accept(@NonNull List<Workorder> workorder) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (workorder == null || workorder.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(workorder);


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


    /**
     * 获取数据*
     */
    private void initAdapter(final List<Workorder> list) {
        traveladapter = new TravelAdapter(Ccsq_SearchActivity.this, R.layout.list_item_travel, list);
        mRecyclerView.setAdapter(traveladapter);
        traveladapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = null;
                if (appid.equals(GlobalConfig.TRAVEL_APPID)) {//国内立项
                    intent = new Intent(Ccsq_SearchActivity.this, GnWorkorderCopyActivity.class);
                } else if (appid.equals(GlobalConfig.TRAVELS_APPID)) {//国外立项
                    intent = new Intent(Ccsq_SearchActivity.this, CgWorkorderActivity.class);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("workorder", (Serializable) traveladapter.getData().get(position));
                bundle.putString("type", "update");
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<Workorder> list) {
        traveladapter.addData(list);
    }
}
