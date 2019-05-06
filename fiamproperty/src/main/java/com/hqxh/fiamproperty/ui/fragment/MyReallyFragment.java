package com.hqxh.fiamproperty.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseFragment;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_WORKREPORT;
import com.hqxh.fiamproperty.ui.activity.MyReallyActivity;
import com.hqxh.fiamproperty.ui.adapter.BaseQuickAdapter;
import com.hqxh.fiamproperty.ui.adapter.MyProjectAdapter;
import com.hqxh.fiamproperty.ui.widget.SelectDateDialog;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.hqxh.fiamproperty.unit.JsonUnit;
import com.jeek.calendar.widget.calendar.CalendarUtils;
import com.jeek.calendar.widget.calendar.OnCalendarClickListener;
import com.jeek.calendar.widget.calendar.month.MonthAdapter;
import com.jeek.calendar.widget.calendar.month.MonthCalendarView;
import com.jeek.calendar.widget.calendar.month.MonthView;
import com.jeek.calendar.widget.calendar.schedule.ScheduleLayout;
import com.jeek.calendar.widget.calendar.schedule.ScheduleRecyclerView;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
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

/**
 * Created by Administrator on 2018\6\15 0015.
 */

public class MyReallyFragment extends BaseFragment implements OnCalendarClickListener, SelectDateDialog.OnSelectDateListener {
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

    /**
     * 添加按钮
     **/
    private TextView addImageView;

    private LinearLayout sp_btn;

    SmartRefreshLayout refreshLayout;

    private MyProjectAdapter myProjectAdapter;

    private int mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay;

    private long mTime;

    private String dailylogdate; //日志日期

    private String dailylogmonth; //日志月

    private int curpage = 1;
    private int showcount = 15;
    private int totalpage;
    private int totalpages;
    Dialog mCameraDialog;
    RecyclerView recyclerView;
    private LinearLayout not_data;

    ArrayList<Integer> dots = new ArrayList<>();

    private static boolean isFirstEnter = true;

    private boolean isGetData = false;

    @Nullable
    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        isGetData = true;
        return inflater.inflate(R.layout.fragment_logs, container, false);
    }

    @Override
    protected void bindView() {

        refreshLayout = (SmartRefreshLayout) searchViewById(R.id.refreshLayout);
//        refreshLayout.setEnableRefresh(false);
        sp_btn  = searchViewById(R.id.sp_btn);
        sp_btn.setVisibility(View.GONE);
        yearTextView = searchViewById(R.id.year_text_id);
        rLNoTask = searchViewById(R.id.rlNoTask);
        addImageView = searchViewById(R.id.add_text);
        slSchedule = searchViewById(R.id.slSchedule);
        slSchedule.setOnCalendarClickListener(this);
        addImageView.setOnClickListener(addViewOnClickListener);
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
                        myProjectAdapter.removeAll(myProjectAdapter.getData());
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
     * 添加事件
     **/
    private View.OnClickListener addViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mCameraDialog = new Dialog(getActivity(), R.style.my_dialog);
            LinearLayout root = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                    R.layout.tranlate_dialog_out, null);
            root.findViewById(R.id.text_cancle).setOnClickListener(btnlistener);
            root.findViewById(R.id.text_sure).setOnClickListener(btnlistener);
            not_data = (LinearLayout) root.findViewById(R.id.have_not_data_id);
            recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
            mCameraDialog.setContentView(root);
            Window dialogWindow = mCameraDialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
            mCameraDialog.setCanceledOnTouchOutside(true);
            dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
            WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            lp.x = 0; // 新位置X坐标
            lp.y = -20; // 新位置Y坐标
            lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度

            root.measure(0, 0);
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.alpha = 9f; // 透明度
            dialogWindow.setAttributes(lp);
            mCameraDialog.show();
        }
    };

    private View.OnClickListener btnlistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.text_sure) {


            } else if (i == R.id.text_cancle) {
                if (mCameraDialog != null) {
                    mCameraDialog.dismiss();
                }

            }
        }
    };

    @Override
    protected void initData() {
        super.initData();
        initDate();
        resetMainTitleDate(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);
        getmonth();
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
        myProjectAdapter.removeAll(myProjectAdapter.getData());
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
        myProjectAdapter = new MyProjectAdapter(getActivity(), R.layout.list_item_my_project, list);
        rvScheduleList.setAdapter(myProjectAdapter);
        myProjectAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MyReallyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("workreport", (Serializable) myProjectAdapter.getData().get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);

            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<R_WORKREPORT.WORKREPORT> list) {
        myProjectAdapter.addData(list);
    }


    private void getData() {
        String data = HttpManager.getMyProject(AccountUtils.getpersonId(getActivity()), dailylogdate, curpage, showcount);
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
        String data = HttpManager.getMyProject_month(AccountUtils.getpersonId(getActivity()), dailylogmonth, curpage, showcount);
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
        String data = HttpManager.getMyProject_month(AccountUtils.getpersonId(getActivity()), dailylogmonth, curpage, 9999999);
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
                            myProjectAdapter.notifyDataSetChanged();
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
