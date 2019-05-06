package com.hqxh.fiamproperty.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseFragment;
import com.hqxh.fiamproperty.bean.R_APPROVE;
import com.hqxh.fiamproperty.bean.R_APPROVES;
import com.hqxh.fiamproperty.bean.R_WORKFLOW;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.APPROVES;
import com.hqxh.fiamproperty.model.R_WORKREPORT;
import com.hqxh.fiamproperty.ui.activity.MyReallyActivity;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.MyProjectAdapter;
import com.hqxh.fiamproperty.ui.adapter.WaitProjectAdapter;
import com.hqxh.fiamproperty.ui.widget.ConfirmDialog;
import com.hqxh.fiamproperty.ui.widget.PullLoadMoreRecyclerView;
import com.hqxh.fiamproperty.ui.widget.SelectDateDialog;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.hqxh.fiamproperty.unit.JsonUnit;
import com.jeek.calendar.widget.calendar.CalendarUtils;
import com.jeek.calendar.widget.calendar.OnCalendarClickListener;
import com.jeek.calendar.widget.calendar.schedule.ScheduleLayout;
import com.jeek.calendar.widget.calendar.schedule.ScheduleRecyclerView;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.hqxh.fiamproperty.base.BaseActivity.showMiddleToast;

/**
 * Created by Administrator on 2018\6\15 0015.
 */

public class WaitReallyFragment extends BaseFragment implements OnCalendarClickListener, SelectDateDialog.OnSelectDateListener {
    private TextView yearTextView; //显示日期
    /**
     * 日历
     **/
    private ScheduleLayout slSchedule;
    /**
     * 显示数据
     **/
    private ScheduleRecyclerView rvScheduleList;

    /**
     * 没有数据
     **/
    private RelativeLayout rLNoTask;

    SmartRefreshLayout refreshLayout;

    /**
     * 审批通过按钮
     **/
    private TextView passTextView;

    /**
     * 取消按钮
     **/
    private TextView cancleTextView;

    private LinearLayout new_btn;

    private WaitProjectAdapter waitProjectAdapter;

    private int mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay;

    private long mTime;

    private String dailylogmonth; //日志日期
    private String dailylogdate; //日志日期

    private int curpage = 1;
    private int showcount = 15;
    private int totalpage;
    private int totalpages;

    ArrayList<Integer> dots = new ArrayList<>();

    private static boolean isFirstEnter = true;

    private boolean isGetData = false;

    @Nullable
    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        isGetData = true;
        return inflater.inflate(R.layout.fragment_wait_logs, container, false);
    }

    @Override
    protected void bindView() {
        refreshLayout = (SmartRefreshLayout) searchViewById(R.id.refreshLayout);
//        refreshLayout.setEnableRefresh(false);
        new_btn  = searchViewById(R.id.text_new);
        new_btn.setVisibility(View.GONE);
        yearTextView = searchViewById(R.id.year_text_id);
        rLNoTask = searchViewById(R.id.rlNoTask);
        passTextView = searchViewById(R.id.tab_text_left);
        cancleTextView = searchViewById(R.id.tab_text_right);
        slSchedule = searchViewById(R.id.slSchedule);
        slSchedule.setOnCalendarClickListener(this);
        passTextView.setOnClickListener(passTextOnClickListener);
        cancleTextView.setOnClickListener(cancleTextViewOnClickListener);
        initUi();
        initScheduleList();
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(false));
        refreshLayout.setHeaderHeight(60);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        waitProjectAdapter.removeAll(waitProjectAdapter.getData());
                        if (isFirstEnter){
                            getData();
                        }else {
                            getData_month();
                        }
                        refreshLayout.finishRefresh();
                    }
                }, 2000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (totalpage == curpage) {
                            refreshLayout.finishLoadMore();
                            showMiddleToast(getActivity(), getResources().getString(R.string.all_data_hint));
                        } else if (totalpages == curpage){
                            refreshLayout.finishLoadMore();
                            showMiddleToast(getActivity(), getResources().getString(R.string.all_data_hint));
                        }else if (curpage < totalpage){
                            curpage++;
                            getData();
                            refreshLayout.finishLoadMore();
                        }else if (curpage < totalpages){
                            curpage++;
                            getData_month();
                            refreshLayout.finishLoadMore();
                        }else {
                            refreshLayout.finishLoadMore();
                        }
                    }
                }, 2000);
            }
        });
    }
    private void initUi() {
        String time = Calendar.getInstance().get(Calendar.YEAR) + "年" + (Calendar.getInstance().get(Calendar.MONTH)) + "月";
        yearTextView.setText(time);
    }

    /**
     * 审批事件
     *
     **/
    private View.OnClickListener passTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (waitProjectAdapter.getChooseWorkOrderList()!= null || waitProjectAdapter.getChooseWorkOrderList().size()!=0){
                String data = JSON.toJSONString(waitProjectAdapter.getChooseWorkOrderList());
                Rx2AndroidNetworking
                        .post(GlobalConfig.HTTP_URL_APPROVES_WORKFLOW)
                        .addBodyParameter("data", data)
                        .build()
                        .getStringObservable()
                        .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                        .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                        .doOnNext(new Consumer<String>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull String string) throws Exception {

                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull String s) throws Exception {

                                R_APPROVES workflow = new Gson().fromJson(s, R_APPROVES.class);

                                showMiddleToast(getActivity(), workflow.getErrmsg());

                            }


                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                                showMiddleToast(getActivity(), "审批失败");
                            }
                        });
            }
        }
    };

    /**
     * 取消事件
     **/
    private View.OnClickListener cancleTextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            waitProjectAdapter.getChooseWorkOrderList().clear();
            waitProjectAdapter.setAll(false);
            waitProjectAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void initData() {
        super.initData();
        initDate();
        resetMainTitleDate(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);
//        getmonth();
    }


    public void resetMainTitleDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        if (year == calendar.get(Calendar.YEAR) &&
                month == calendar.get(Calendar.MONTH) &&
                day == calendar.get(Calendar.DAY_OF_MONTH)) {
            yearTextView.setText(year + "年" + month + "月");
        } else {
            if (year == calendar.get(Calendar.YEAR)) {
                yearTextView.setText(year + "年" + month + "月");
            } else {

                yearTextView.setText(year + "年" + month + "月");
            }
        }
        if (mCurrentSelectMonth < 10) {
            dailylogmonth = String.valueOf(year) + "0" + String.valueOf(month) ;
        }else {
            dailylogmonth = String.valueOf(year) + String.valueOf(month) ;
        }
//        setCurrentSelectDate(year, month, day);
    }


    @Override
    protected void bindData() {
        super.bindData();
        resetScheduleList();
    }

    public void resetScheduleList() {
    }

    private void initDate() {
        Calendar calendar = Calendar.getInstance();
        setCurrentSelectDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClickDate(int year, int month, int day) {
        setCurrentSelectDate(year, month, day);
        resetScheduleList();
    }

    @Override
    public void onPageChange(int year, int month, int day) {

    }

    /**
     * 初始化数据
     **/
    private void initScheduleList() {
        rvScheduleList = slSchedule.getSchedulerRecyclerView();
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvScheduleList.setLayoutManager(manager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        rvScheduleList.setItemAnimator(itemAnimator);
        initAdapter(new ArrayList<R_WORKREPORT.WORKREPORT>());

    }


    private void setCurrentSelectDate(int year, int month, int day) {
        mCurrentSelectYear = year;
        mCurrentSelectMonth = month + 1; //下标加1表示月份
        mCurrentSelectDay = day;
        resetMainTitleDate(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);
        if (mCurrentSelectMonth < 10) {
            if (mCurrentSelectDay<10){
                dailylogdate = mCurrentSelectYear + "0" + mCurrentSelectMonth + "0" + mCurrentSelectDay;
            }else {
                dailylogdate = mCurrentSelectYear + "0" + mCurrentSelectMonth + "" + mCurrentSelectDay;
            }
        } else {
            if (mCurrentSelectDay<10){
                dailylogdate = mCurrentSelectYear + "" + mCurrentSelectMonth + "0" + mCurrentSelectDay;
            }else {
                dailylogdate = mCurrentSelectYear + "" + mCurrentSelectMonth + "" + mCurrentSelectDay;
            }
        }
        SharedPreferences userInfo = getActivity().getSharedPreferences("user_info", 0);
        int type = userInfo.getInt("type", 1);
        rLNoTask.setVisibility(View.GONE);
        waitProjectAdapter.removeAll(waitProjectAdapter.getData());
        showLoadingDialog(getResources().getString(R.string.load_more_text));
        if (type == 0){
            getmonth();
            getData_month();
            isFirstEnter = false;
        }else if (type == 1){
            getData();
            isFirstEnter = true;
        }
    }


    @Override
    public void onSelectDate(final int year, final int month, final int day, long time, int position) {


        slSchedule.getMonthCalendar().setCurrentItem(position);
        slSchedule.postDelayed(new Runnable() {
            @Override
            public void run() {
                slSchedule.getMonthCalendar().getCurrentMonthView().clickThisMonth(year, month, day);
            }
        }, 100);
        mTime = time;
    }
    /**
     * 获取数据*
     */
    private void initAdapter(final List<R_WORKREPORT.WORKREPORT> list) {
        waitProjectAdapter = new WaitProjectAdapter(getActivity(), R.layout.list_item_wait_project, list);
        rvScheduleList.setAdapter(waitProjectAdapter);
        waitProjectAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MyReallyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("workreport", (Serializable) waitProjectAdapter.getData().get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);

            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<R_WORKREPORT.WORKREPORT> list) {
        waitProjectAdapter.addData(list);
    }


    private void getData() {
        String data = HttpManager.getWait(AccountUtils.getpersonId(getActivity()), dailylogdate, curpage, showcount);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_WORKREPORT.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_WORKREPORT>() {
                    @Override
                    public void accept(@NonNull R_WORKREPORT r_workdailylog) throws Exception {
                    }
                })

                .map(new Function<R_WORKREPORT, R_WORKREPORT.ResultBean>() {
                    @Override
                    public R_WORKREPORT.ResultBean apply(@NonNull R_WORKREPORT r_workdailylog) throws Exception {

                        return r_workdailylog.getResult();
                    }
                })
                .map(new Function<R_WORKREPORT.ResultBean, List<R_WORKREPORT.WORKREPORT>>() {
                    @Override
                    public List<R_WORKREPORT.WORKREPORT> apply(@NonNull R_WORKREPORT.ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<R_WORKREPORT.WORKREPORT>>() {
                    @Override
                    public void accept(@NonNull List<R_WORKREPORT.WORKREPORT> workorder) throws Exception {
                        dismissLoadingDialog();
                        if (workorder == null || workorder.isEmpty()) {
                            rLNoTask.setVisibility(View.VISIBLE);
                        } else {

                            addData(workorder);

                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadingDialog();
                        rLNoTask.setVisibility(View.VISIBLE);
                    }
                });

    }

    private void getData_month() {
        String data = HttpManager.getWait_month(AccountUtils.getpersonId(getActivity()), dailylogmonth, curpage, showcount);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_WORKREPORT.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_WORKREPORT>() {
                    @Override
                    public void accept(@NonNull R_WORKREPORT r_workdailylog) throws Exception {
                    }
                })

                .map(new Function<R_WORKREPORT, R_WORKREPORT.ResultBean>() {
                    @Override
                    public R_WORKREPORT.ResultBean apply(@NonNull R_WORKREPORT r_workdailylog) throws Exception {

                        return r_workdailylog.getResult();
                    }
                })
                .map(new Function<R_WORKREPORT.ResultBean, List<R_WORKREPORT.WORKREPORT>>() {
                    @Override
                    public List<R_WORKREPORT.WORKREPORT> apply(@NonNull R_WORKREPORT.ResultBean resultBean) throws Exception {
                        totalpages = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<R_WORKREPORT.WORKREPORT>>() {
                    @Override
                    public void accept(@NonNull List<R_WORKREPORT.WORKREPORT> workorder) throws Exception {
                        dismissLoadingDialog();
                        if (workorder == null || workorder.isEmpty()) {
                            rLNoTask.setVisibility(View.VISIBLE);
                        } else {
                            addData(workorder);
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissLoadingDialog();
                        rLNoTask.setVisibility(View.VISIBLE);
                    }
                });

    }
    private void getmonth() {
        String data = HttpManager.getWait_month(AccountUtils.getpersonId(getActivity()), dailylogmonth, curpage, 9999999);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_WORKREPORT.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_WORKREPORT>() {
                    @Override
                    public void accept(@NonNull R_WORKREPORT r_workdailylog) throws Exception {
                    }
                })

                .map(new Function<R_WORKREPORT, R_WORKREPORT.ResultBean>() {
                    @Override
                    public R_WORKREPORT.ResultBean apply(@NonNull R_WORKREPORT r_workdailylog) throws Exception {

                        return r_workdailylog.getResult();
                    }
                })
                .map(new Function<R_WORKREPORT.ResultBean, List<R_WORKREPORT.WORKREPORT>>() {
                    @Override
                    public List<R_WORKREPORT.WORKREPORT> apply(@NonNull R_WORKREPORT.ResultBean resultBean) throws Exception {
//                        totalpages = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<R_WORKREPORT.WORKREPORT>>() {
                    @Override
                    public void accept(@NonNull List<R_WORKREPORT.WORKREPORT> workorder) throws Exception {
                        dismissLoadingDialog();
                        if (workorder == null || workorder.isEmpty()) {
                            int date = CalendarUtils.getMonthDays(mCurrentSelectYear, mCurrentSelectMonth);
                            for (int i=0;i<date;i++){
                                dots.add(i+1);
                            }
                            slSchedule.removeTaskHints(dots);
                        } else {
                            int date = CalendarUtils.getMonthDays(mCurrentSelectYear, mCurrentSelectMonth);
                            for (int i=0;i<date;i++){
                                dots.add(i+1);
                            }
                            slSchedule.removeTaskHints(dots);
                            dots.clear();
                            waitProjectAdapter.notifyDataSetChanged();
                            for (int i=0;i<workorder.size();i++){

                                String b=JsonUnit.convertStrToArray(workorder.get(i).getREPORTDATE())[0];
                                String c= b.substring(8,10);
                                dots.add(Integer.valueOf(c));
                            }

                            slSchedule.addTaskHints(dots);
                            dots.clear();
                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (! isGetData ) {
            return;
        }

        if (isVisibleToUser) {
            getmonth();
        }
    }
}
