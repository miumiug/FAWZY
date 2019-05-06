package com.hqxh.fiamproperty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_WPITEM;
import com.hqxh.fiamproperty.model.R_WPITEM.ResultBean;
import com.hqxh.fiamproperty.model.R_WPITEM.WPITEM;
import com.hqxh.fiamproperty.model.R_ZXPERSON;
import com.hqxh.fiamproperty.ui.adapter.DjWpitemAdapter;
import com.hqxh.fiamproperty.ui.adapter.WpitemAdapter;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.hqxh.fiamproperty.unit.JsonUnit;
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
 * 明细信息的Activity
 **/
public class WpitemActivity extends BaseListActivity {
    private static final String TAG = "WpitemActivity";
    private Toolbar itemToolbar;
    private WpitemAdapter wpitemadapter;

    private DjWpitemAdapter djwpitemadapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;


    private String appid; // appid
    private String wonum; // wonum
    private int mark;

    private int mpostion; //选择执行项

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("appid")) {
            appid = getIntent().getExtras().getString("appid");
        }
        if (getIntent().hasExtra("wonum")) {
            wonum = getIntent().getExtras().getString("wonum");
        }
        if (appid.equals(GlobalConfig.TODJ_APPID)) {
            isShowBack = false;
        }

        if (getIntent().hasExtra("mark")) {
            mark = getIntent().getExtras().getInt("mark");
        }

    }

    @Override
    protected String getSubTitle() {

        return getString(R.string.mxxx_text);
    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getWPITEMUrl(appid, wonum, AccountUtils.getpersonId(this), curpage, showcount);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addBodyParameter("data", data)
                .build()
                .getObjectObservable(R_WPITEM.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_WPITEM>() {
                    @Override
                    public void accept(@NonNull R_WPITEM r_wpitem) throws Exception {
                    }
                })

                .map(new Function<R_WPITEM, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_WPITEM r_wpitem) throws Exception {

                        return r_wpitem.getResult();
                    }
                })
                .map(new Function<ResultBean, List<WPITEM>>() {
                    @Override
                    public List<WPITEM> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<WPITEM>>() {
                    @Override
                    public void accept(@NonNull List<WPITEM> wpitem) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (wpitem == null || wpitem.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {
                            if (appid.equals(GlobalConfig.TOLL_APPID)) {
                                addData(wpitem);
                            } else if (appid.equals(GlobalConfig.TODJ_APPID)) {
                                addDjData(wpitem);
                            }


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
        if (appid.equals(GlobalConfig.TOLL_APPID)) {//物资
            wpitemadapter.removeAll(wpitemadapter.getData());
        } else if (appid.equals(GlobalConfig.TODJ_APPID)) {//调件
            djwpitemadapter.removeAll(djwpitemadapter.getData());
        }
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(WpitemActivity.this, getResources().getString(R.string.all_data_hint));
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
        itemToolbar = (Toolbar) findViewById(R.id.toolbar);
        searchText.setVisibility(View.GONE);
        if (appid.equals(GlobalConfig.TOLL_APPID)) {//物资
            initAdapter(new ArrayList<WPITEM>());
        } else if (appid.equals(GlobalConfig.TODJ_APPID)) {//调件
            initDjAdapter(new ArrayList<WPITEM>());
        }
        getData();

    }

    @Override
    protected void setOnClick() {
        backOnClickListener();
    }

    private void backOnClickListener() {
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        itemToolbar.setTitle("");
        setSupportActionBar(itemToolbar);
        itemToolbar.setNavigationIcon(R.mipmap.ic_back);
        itemToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("wpitem", (Serializable) djwpitemadapter.getData());
                intent.putExtras(bundle);
                setResult(GlobalConfig.DJRWD_RESULTCODE, intent);
                onBackPressed();

            }
        });

    }

    /**
     * 获取数据-物料单明细*
     */
    private void initAdapter(final List<WPITEM> list) {
        wpitemadapter = new WpitemAdapter(WpitemActivity.this, R.layout.list_item_wzmx, list);
        mRecyclerView.setAdapter(wpitemadapter);
    }

    /**
     * 获取数据-调件任务单明细*
     */
    private void initDjAdapter(final List<WPITEM> list) {
        djwpitemadapter = new DjWpitemAdapter(WpitemActivity.this, R.layout.list_item_jdmx, list);
        djwpitemadapter.setMark(mark);
        mRecyclerView.setAdapter(djwpitemadapter);
        djwpitemadapter.setmOnClickListener(new DjWpitemAdapter.cOnClickListener() {
            @Override
            public void cOnClickListener(int postion) {
                mpostion = postion;
                chooseOwner();
            }
        });
    }

    /**
     * 添加数据-物料单明细*
     */
    private void addData(final List<WPITEM> list) {
        wpitemadapter.addData(list);
    }

    /**
     * 添加数据-调件任务单明细*
     */
    private void addDjData(final List<WPITEM> list) {
        djwpitemadapter.addData(list);
    }

    //选择执行人
    private void chooseOwner() {
        Intent intent = new Intent(WpitemActivity.this, PersonActivity.class);
        intent.putExtra("appid", GlobalConfig.TODJ_APPID);
        intent.putExtra("title", getResources().getString(R.string.ownername_text));
        startActivityForResult(intent, GlobalConfig.PERSON_REQUESTCODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GlobalConfig.PERSON_REQUESTCODE:
                if (resultCode == GlobalConfig.CUDEPT_REQUESTCODE) {
                    R_ZXPERSON.PERSON persion = (R_ZXPERSON.PERSON) data.getSerializableExtra("person");
                    String personnum = JsonUnit.convertStrToArray(persion.getPERSONID())[0];
                    String personname = JsonUnit.convertStrToArray(persion.getDISPLAYNAME())[0];
                    WPITEM wpitem = (WPITEM) djwpitemadapter.getData().get(mpostion);
                    String owenr = personnum + "," + JsonUnit.convertStrToArray(wpitem.getOWNER())[1];
                    wpitem.setOWNER(owenr);
                    wpitem.setOWNERNAME(personname);
                    djwpitemadapter.remove(mpostion);
                    djwpitemadapter.add(mpostion, wpitem);
                    djwpitemadapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
