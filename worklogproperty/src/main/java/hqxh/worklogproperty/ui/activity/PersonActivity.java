package hqxh.worklogproperty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hqxh.worklogproperty.R;
import hqxh.worklogproperty.api.HttpManager;
import hqxh.worklogproperty.base.BaseActivity;
import hqxh.worklogproperty.constant.GlobalConfig;
import hqxh.worklogproperty.model.R_PERSONS;
import hqxh.worklogproperty.model.R_PERSONS.PERSON;
import hqxh.worklogproperty.model.R_PERSONS.ResultBean;
import hqxh.worklogproperty.ui.adapter.BaseQuickAdapter;
import hqxh.worklogproperty.ui.adapter.PersonAdapter;
import hqxh.worklogproperty.until.AccountUtils;
import hqxh.worklogproperty.widget.PullLoadMoreRecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 执行人选择项
 **/
public class PersonActivity extends BaseActivity {
    private static String TAG="PersonActivity";

    private ImageView backImageView; //返回

    private TextView titleText; //标题

    private ImageView searchImageView; //搜索


    //搜索布局
    private ImageView backImage;
    private SearchView mSearchView;

    private TextView mSearchViewTextView;


    private RelativeLayout searchRelativeLayout; //搜索布局1
    private LinearLayout searchLinearLayout; //搜索布局2


    /**
     * RecyclerView
     **/
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    private RecyclerView mRecyclerView;

    private LinearLayout notLinearLayout;


    /**
     * 查询条件*
     */
    private String search = "";

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private PersonAdapter personAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        backImageView = (ImageView) findViewById(R.id.back_text_id);
        titleText = (TextView) findViewById(R.id.title_text_id);
        searchImageView = (ImageView) findViewById(R.id.search_imageView_id);

        searchRelativeLayout = (RelativeLayout) findViewById(R.id.search_top_id);
        searchLinearLayout = (LinearLayout) findViewById(R.id.search_top1_id);


        //搜索布局
        backImage = (ImageView) findViewById(R.id.back_text1_id);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchRelativeLayout.setVisibility(View.VISIBLE);
                searchLinearLayout.setVisibility(View.GONE);
                mSearchViewTextView.setText("");
            }
        });
        mSearchView.setIconifiedByDefault(false);
        if (mSearchView == null) {
            return;
        } else {
            //获取到TextView的ID
            int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            //获取到TextView的控件
            mSearchViewTextView = (TextView) mSearchView.findViewById(id);
            //设置字体大小为14sp
            mSearchViewTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);//14sp
            //设置字体颜色
            mSearchViewTextView.setTextColor(getResources().getColor(R.color.white));
            //设置提示文字颜色
            mSearchViewTextView.setHintTextColor(getResources().getColor(R.color.white));
        }


        mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) findViewById(R.id.pullLoadMoreRecyclerView);
        notLinearLayout = (LinearLayout) findViewById(R.id.have_not_data_id);
        //获取mRecyclerView对象
        mRecyclerView = mPullLoadMoreRecyclerView.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //显示下拉刷新
        mPullLoadMoreRecyclerView.setRefreshing(true);

        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(pullLoadMoreListener);
        titleText.setText(getResources().getText(R.string.zzqrr_text));
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        searchImageView.setOnClickListener(searchImageViewOnClickListener);

        mSearchView.setOnQueryTextListener(mSearchViewOnQueryTextListener);

        initAdapter(new ArrayList<PERSON>());
        getData(search);
    }


    /**
     * 搜索事件
     **/
    private View.OnClickListener searchImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            searchRelativeLayout.setVisibility(View.GONE);
            searchLinearLayout.setVisibility(View.VISIBLE);
            if (searchLinearLayout.isShown()) {
                personAdapter.removeAll(personAdapter.getData());
                notLinearLayout.setVisibility(View.VISIBLE);
            }
        }
    };


    private SearchView.OnQueryTextListener mSearchViewOnQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            search = s;
            //显示下拉刷新
            mPullLoadMoreRecyclerView.setRefreshing(true);
            personAdapter.removeAll(personAdapter.getData());
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


    //刷新事件
    private PullLoadMoreRecyclerView.PullLoadMoreListener pullLoadMoreListener = new PullLoadMoreRecyclerView.PullLoadMoreListener() {
        @Override
        public void onRefresh() {
            curpage = 1;
            personAdapter.removeAll(personAdapter.getData());
            getData(search);
        }

        @Override
        public void onLoadMore() {
            if (totalpage == curpage) {
                getLoadMore();
                showMiddleToast(PersonActivity.this, getResources().getString(R.string.all_data_hint));
            } else {
                curpage++;
                getData(search);
            }
        }
    };


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
     * 获取人员数据
     **/
    private void getData(String search) {

        String data = HttpManager.getPERSONUrl(search, AccountUtils.getpersonId(this), curpage, showcount);
        Log.e(TAG,"data="+data);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_PERSONS.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_PERSONS>() {
                    @Override
                    public void accept(@NonNull R_PERSONS r_persons) throws Exception {
                    }
                })

                .map(new Function<R_PERSONS, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_PERSONS r_persons) throws Exception {

                        return r_persons.getResult();
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


    /**
     * 获取数据*
     */
    private void initAdapter(final List<PERSON> list) {
        personAdapter = new PersonAdapter(PersonActivity.this, R.layout.list_item_person, list);
        mRecyclerView.setAdapter(personAdapter);
        personAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("person", (Serializable) personAdapter.getData().get(position));
                intent.putExtras(bundle);
                setResult(GlobalConfig.PERSON_RESULTCODE, intent);
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
}
