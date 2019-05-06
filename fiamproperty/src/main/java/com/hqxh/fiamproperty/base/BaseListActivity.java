package com.hqxh.fiamproperty.base;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.ui.widget.PullLoadMoreRecyclerView;


/**
 * Activity列表基类
 */

public abstract class BaseListActivity extends BaseTitleActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {
    private static final String TAG = "BaseListActivity";
    protected ImageView searchText;



    /**RecyclerView**/
    protected PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    protected RecyclerView mRecyclerView;

    protected LinearLayout notLinearLayout;

    protected ImageView add_imageView_id;


    protected abstract void fillData();

    protected abstract void setOnClick();



    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_active_task;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        searchText=(ImageView)findViewById(R.id.search_imageView_id);
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
        mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置上拉刷新文字
//        mPullLoadMoreRecyclerView.setFooterViewText("loading");
        //设置上拉刷新文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        mPullLoadMoreRecyclerView.setLinearLayout();

        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        searchText.setVisibility(View.VISIBLE);
        add_imageView_id = (ImageView) findViewById(R.id.add_imageView_id);
        fillData();
        setOnClick();


    }



    @Override
    protected abstract String getSubTitle();










}
