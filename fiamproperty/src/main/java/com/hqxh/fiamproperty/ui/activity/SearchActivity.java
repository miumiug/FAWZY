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
import com.hqxh.fiamproperty.model.R_Wfassignemt;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.WfassignmentAdapter;
import com.hqxh.fiamproperty.ui.widget.PullLoadMoreRecyclerView;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.hqxh.fiamproperty.unit.JsonUnit;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 待办任务的Activity
 **/
public class SearchActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    private static final String TAG = "SearchActivity";

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

    private WfassignmentAdapter wfassignmentAdapter;
    private String search;

    private String assignstatus; //状态

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("assignstatus")) {
            assignstatus = getIntent().getExtras().getString("assignstatus");
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

        initAdapter(new ArrayList<R_Wfassignemt.Wfassignment>());
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
            wfassignmentAdapter.removeAll(wfassignmentAdapter.getData());
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
        wfassignmentAdapter.removeAll(wfassignmentAdapter.getData());
        getData(search);

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(SearchActivity.this, getResources().getString(R.string.all_data_hint));
        } else {
            curpage++;
            getData(search);
        }
    }


    /**
     * 获取数据
     **/
    private void getData(String search) {
        String data = HttpManager.getWFASSIGNMENTUrl(search, AccountUtils.getpersonId(this), assignstatus, curpage, showcount);

        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_Wfassignemt.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_Wfassignemt>() {
                    @Override
                    public void accept(@NonNull R_Wfassignemt RWfassignemt) throws Exception {
                    }
                })

                .map(new Function<R_Wfassignemt, R_Wfassignemt.ResultBean>() {
                    @Override
                    public R_Wfassignemt.ResultBean apply(@NonNull R_Wfassignemt RWfassignemt) throws Exception {

                        return RWfassignemt.getResult();
                    }
                })
                .map(new Function<R_Wfassignemt.ResultBean, List<R_Wfassignemt.Wfassignment>>() {
                    @Override
                    public List<R_Wfassignemt.Wfassignment> apply(@NonNull R_Wfassignemt.ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<R_Wfassignemt.Wfassignment>>() {
                    @Override
                    public void accept(@NonNull List<R_Wfassignemt.Wfassignment> wfassignments) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (wfassignments == null || wfassignments.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(wfassignments);


                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        notLinearLayout.setVisibility(View.VISIBLE);
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
    private void initAdapter(final List<R_Wfassignemt.Wfassignment> list) {
        wfassignmentAdapter = new WfassignmentAdapter(SearchActivity.this, R.layout.list_item_task, list);
        mRecyclerView.setAdapter(wfassignmentAdapter);
        wfassignmentAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                R_Wfassignemt.Wfassignment wfassignment = (R_Wfassignemt.Wfassignment) wfassignmentAdapter.getData().get(position);
                String app = JsonUnit.convertStrToArray(wfassignment.getAPP())[0];
                Log.e(TAG, "app=" + app);
                Intent intent = getIntent();
                if (app.equals(GlobalConfig.GRWZ_APPID)) { //出门管理
                    intent.setClass(SearchActivity.this, GrDetailsActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.TRAVEL_APPID)) {//国内出差
                    intent.setClass(SearchActivity.this, GnWorkorderCopyActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("type", "update");
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.TRAVELS_APPID)) {//国外出差
                    intent.setClass(SearchActivity.this, CgWorkorderActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.JSPR_APPID)) {//技术采购申请
                    intent.setClass(SearchActivity.this, JsPrActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.SZPR_APPID)) {//试制采购申请
                    intent.setClass(SearchActivity.this, SzPrActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.PR_APPID)) {//物资采购申请
                    intent.setClass(SearchActivity.this, WzPrActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.FWPR_APPID)) {//服务采购申请
                    intent.setClass(SearchActivity.this, FwpaydetailsActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.WPPR_APPID)) {//外培采购申请
                    intent.setClass(SearchActivity.this, WppaydetailsActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.TOSY_APPID)) {//试用任务单
                    intent.setClass(SearchActivity.this, SyrwdWorkorderActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.TOSZ_APPID)) {//试制任务单
                    intent.setClass(SearchActivity.this, SzrwdWorkorderActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.TOLL_APPID)) {//物资领料单
                    intent.setClass(SearchActivity.this, WzlldWorkorderActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.TODJ_APPID)) {//调件任务单
                    intent.setClass(SearchActivity.this, DjrwdWorkorderActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.TOOIL_APPID)) {//燃油申请单
                    intent.setClass(SearchActivity.this, RyrwdWorkorderActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.TOQT_APPID)) {//其它任务单
                    intent.setClass(SearchActivity.this, QtrwdWorkorderActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.CONTPURCH_APPID)) {//合同
                    intent.setClass(SearchActivity.this, PurchviewActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.PAYCHECK_APPID)) {//付款验收
                    intent.setClass(SearchActivity.this, PaycheckActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.PP_APPID)) {//需款计划
                    intent.setClass(SearchActivity.this, XkplandetailActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.EXPENSES_APPID)) {//差旅报销单
                    intent.setClass(SearchActivity.this, ExpenseActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.EXPENSE_APPID)) { //备用金报销单
                    intent.setClass(SearchActivity.this, ByExpenseActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else if (app.equals(GlobalConfig.BO_APPID)) { //报销
                    intent.setClass(SearchActivity.this, BoActivity.class);
                    intent.putExtra("appid", app);
                    intent.putExtra("ownernum", JsonUnit.convertStrToArray(wfassignment.getOWNERNUM())[0]);
                    intent.putExtra("ownertable", JsonUnit.convertStrToArray(wfassignment.getOWNERTABLE())[0]);
                    startActivityForResult(intent, 0);
                } else {
                    showMiddleToast(SearchActivity.this, getString(R.string.have_not_appove_hint));
                }
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<R_Wfassignemt.Wfassignment> list) {
        wfassignmentAdapter.addData(list);
    }
}
