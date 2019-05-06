package hqxh.worklogproperty.ui.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeek.calendar.widget.calendar.OnCalendarClickListener;
import com.jeek.calendar.widget.calendar.schedule.ScheduleLayout;
import com.jeek.calendar.widget.calendar.schedule.ScheduleRecyclerView;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hqxh.worklogproperty.R;
import hqxh.worklogproperty.api.HttpManager;
import hqxh.worklogproperty.base.BaseFragment;
import hqxh.worklogproperty.constant.GlobalConfig;
import hqxh.worklogproperty.dialog.SelectDateDialog;
import hqxh.worklogproperty.model.R_WORKDAILYLOG;
import hqxh.worklogproperty.model.R_WORKDAILYLOG.ResultBean;
import hqxh.worklogproperty.model.R_WORKDAILYLOG.WORKDAILYLOG;
import hqxh.worklogproperty.ui.adapter.WorkLogAdapter;
import hqxh.worklogproperty.until.AccountUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的日志
 */
public class MyWorkdailyFragment extends BaseFragment implements OnCalendarClickListener, SelectDateDialog.OnSelectDateListener {

    private static final String TAG = "MyWorkdailyFragment";

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
    private ImageView addImageView;


    private WorkLogAdapter workLogAdapter;

    private int mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay;

    private long mTime;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private String dailylogdate; //日志日期


    private String confirmby; //确认人

    @Nullable
    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_log, container, false);
    }

    @Override
    protected void bindView() {
        yearTextView = searchViewById(R.id.year_text_id);
        slSchedule = searchViewById(R.id.slSchedule);
        rLNoTask = searchViewById(R.id.rlNoTask);
        addImageView = searchViewById(R.id.add_imageView);
        slSchedule.setOnCalendarClickListener(this);
        addImageView.setOnClickListener(addImageViewOnClickListener);
        initUi();
        initScheduleList();
    }

    private void initUi() {
        String time = Calendar.getInstance().get(Calendar.YEAR) + "年" + (Calendar.getInstance().get(Calendar.MONTH)) + "月";
        yearTextView.setText(time);
    }


    /**
     * 添加事件
     **/
    private View.OnClickListener addImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.e(TAG, "confirmby=" + confirmby);
            if (confirmby==null) {
                showMiddleToast(getActivity(), "请选择确认人");
            }
        }
    };


    @Override
    protected void initData() {
        super.initData();
        initDate();
        resetMainTitleDate(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);
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
        initAdapter(new ArrayList<WORKDAILYLOG>());

    }


    private void setCurrentSelectDate(int year, int month, int day) {
        mCurrentSelectYear = year;
        mCurrentSelectMonth = month + 1; //下标加1表示月份
        mCurrentSelectDay = day;
        resetMainTitleDate(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);
        if (mCurrentSelectMonth < 10) {
            dailylogdate = mCurrentSelectYear + "-0" + mCurrentSelectMonth + "-" + mCurrentSelectDay;

        } else {
            dailylogdate = mCurrentSelectYear + "-" + mCurrentSelectMonth + "-" + mCurrentSelectDay;
        }

        rLNoTask.setVisibility(View.GONE);
        workLogAdapter.removeAll(workLogAdapter.getData());
        showLoadingDialog(getResources().getString(R.string.load_more_text));
        getData();
        Log.e(TAG, "dailylogdate=" + dailylogdate);
    }


    @Override
    public void onSelectDate(final int year, final int month, final int day, long time, int position) {

        Log.e(TAG, "year=" + year + ",month=" + month + ",day=" + day);

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
    private void initAdapter(final List<WORKDAILYLOG> list) {
        workLogAdapter = new WorkLogAdapter(getActivity(), R.layout.list_item_log, list);
        rvScheduleList.setAdapter(workLogAdapter);
    }

    /**
     * 添加数据*
     */
    private void addData(final List<WORKDAILYLOG> list) {
        workLogAdapter.addData(list);
    }


    private void getData() {
        String data = HttpManager.getWORKDAILYUrl(AccountUtils.getpersonId(getActivity()), dailylogdate, curpage, showcount);
        Log.e(TAG, "url=" + GlobalConfig.HTTP_URL_SEARCH);
        Log.e(TAG, "data=" + data);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_WORKDAILYLOG.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_WORKDAILYLOG>() {
                    @Override
                    public void accept(@NonNull R_WORKDAILYLOG r_workdailylog) throws Exception {
                    }
                })

                .map(new Function<R_WORKDAILYLOG, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_WORKDAILYLOG r_workdailylog) throws Exception {

                        return r_workdailylog.getResult();
                    }
                })
                .map(new Function<ResultBean, List<WORKDAILYLOG>>() {
                    @Override
                    public List<WORKDAILYLOG> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<WORKDAILYLOG>>() {
                    @Override
                    public void accept(@NonNull List<WORKDAILYLOG> workorder) throws Exception {
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

}
