package com.hqxh.fiamproperty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.base.BaseActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_Workorder;
import com.hqxh.fiamproperty.ui.fragment.TravelFragment;
import com.hqxh.fiamproperty.ui.fragment.TravelsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 出差申请
 **/
public class WorkorderActivity extends BaseActivity {
    private static final String TAG = "WorkorderActivity";
    Toolbar mToolbar;
    /**
     * 标题
     **/
    TextView titleTextView;

    ImageView searchText;
    ImageView add_imageView_id;

    TabLayout tabLayout;
    ViewPager viewPager;


    private TravelFragment travelFragment;
    private TravelsFragment travelsFragment;

    private int current;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_workorder;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTextView = (TextView) findViewById(R.id.title_text);
        searchText = (ImageView) findViewById(R.id.search_imageView_id);
        searchText.setVisibility(View.VISIBLE);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        add_imageView_id = (ImageView)findViewById(R.id.add_imageView_id);
        initToolbar();

        //设置新增按钮可见
        add_imageView_id.setVisibility(View.VISIBLE);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        // 设置ViewPager的数据等
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//适合很多tab
        tabLayout.setTabMode(TabLayout.MODE_FIXED);//tab均分,适合少的tab

        searchText.setOnClickListener(searchTextOnClickListener);

        add_imageView_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(WorkorderActivity.this, GnWorkorderCopyActivity.class);
                Bundle bundle = new Bundle();
                R_Workorder.Workorder workorder = new R_Workorder.Workorder();
                bundle.putSerializable("workorder", workorder);
                bundle.putString("type", "insert");
                bundle.putString("ownertable", GlobalConfig.WORKORDER_NAME);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }


    private void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            titleTextView.setText("FiAM > " + getString(R.string.ccsq_text));
            if (isShowBack()) {
                showBack();
            }
        }
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    private void showBack() {
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    protected boolean isShowBack() {
        return true;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        travelFragment = new TravelFragment();
        Bundle data = new Bundle();
        data.putInt("id", 0);
        travelFragment.setArguments(data);
        adapter.addFrag(travelFragment, getString(R.string.gncc_text));

        travelsFragment = new TravelsFragment();
        data = new Bundle();
        data.putInt("id", 1);
        travelsFragment.setArguments(data);
        adapter.addFrag(travelsFragment, getString(R.string.gwlx_text));


        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                current = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 跳转事件
     **/
    private View.OnClickListener searchTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(WorkorderActivity.this, Ccsq_SearchActivity.class);
            Log.e(TAG, "current" + current);
            if (current == 0) {
                intent.putExtra("appid", GlobalConfig.TRAVEL_APPID);
            } else {
                intent.putExtra("appid", GlobalConfig.TRAVELS_APPID);
            }

            startActivityForResult(intent, 0);
        }
    };
}
