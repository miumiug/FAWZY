package com.hqxh.fiamproperty.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_EXPENSE;
import com.hqxh.fiamproperty.model.R_EXPENSE.ResultBean;
import com.hqxh.fiamproperty.model.R_EXPENSE.EXPENSE;
import com.hqxh.fiamproperty.ui.activity.ByExpenseActivity;
import com.hqxh.fiamproperty.ui.activity.ExpenseActivity;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.ExpenseAdapter;
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
 * 差旅报销
 **/
public class ExpenseFragment extends Fragment implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    private static final String TAG = "ExpenseFragment";

    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private RecyclerView mRecyclerView;
    private LinearLayout notLinearLayout;
    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;


    private ExpenseAdapter expenseAdapter;

    private String title;
    private String appid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appid = getArguments().getString("appid");
        mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) view.findViewById(R.id.pullLoadMoreRecyclerView);
        notLinearLayout = (LinearLayout) view.findViewById(R.id.have_not_data_id);
        //获取mRecyclerView对象
        mRecyclerView = mPullLoadMoreRecyclerView.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //显示下拉刷新
        mPullLoadMoreRecyclerView.setRefreshing(true);
        mPullLoadMoreRecyclerView.setLinearLayout();

        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        initAdapter(new ArrayList<EXPENSE>());
        getData();

    }

    private void getData() {
        String data = HttpManager.getEXPENSEUrl(appid, "",AccountUtils.getpersonId(getActivity()), curpage, showcount);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_EXPENSE.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_EXPENSE>() {
                    @Override
                    public void accept(@NonNull R_EXPENSE r_expense) throws Exception {
                    }
                })

                .map(new Function<R_EXPENSE, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_EXPENSE r_expense) throws Exception {

                        return r_expense.getResult();
                    }
                })
                .map(new Function<ResultBean, List<EXPENSE>>() {
                    @Override
                    public List<EXPENSE> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<EXPENSE>>() {
                    @Override
                    public void accept(@NonNull List<EXPENSE> workorder) throws Exception {
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


    @Override
    public void onRefresh() {
        curpage = 1;
        expenseAdapter.removeAll(expenseAdapter.getData());
        getData();
    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            BaseActivity.showMiddleToast(getActivity(), getResources().getString(R.string.all_data_hint));
        } else {
            curpage++;
            getData();
        }
    }

    private void getLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
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
    private void initAdapter(final List<EXPENSE> list) {
        expenseAdapter = new ExpenseAdapter(getActivity(), R.layout.list_item_travel, list);
        mRecyclerView.setAdapter(expenseAdapter);
        expenseAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                if (appid.equals(GlobalConfig.EXPENSES_APPID)) {//差旅报销
                    intent.setClass(getActivity(), ExpenseActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("expense", (Serializable) expenseAdapter.getData().get(position));
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 0);
                }
                if (appid.equals(GlobalConfig.EXPENSE_APPID)) {//备用金报销
                    intent.setClass(getActivity(), ByExpenseActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("expense", (Serializable) expenseAdapter.getData().get(position));
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 0);
                }
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<EXPENSE> list) {
        expenseAdapter.addData(list);
    }

}
