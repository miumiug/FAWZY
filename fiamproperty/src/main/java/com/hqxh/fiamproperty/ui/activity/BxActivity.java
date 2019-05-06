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
import com.hqxh.fiamproperty.ui.fragment.BoFragment;
import com.hqxh.fiamproperty.ui.fragment.ExpenseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 报销
 **/
public class BxActivity extends BaseActivity {
    Toolbar mToolbar;
    /**
     * 标题
     **/
    TextView titleTextView;
    ImageView searchText;

    TabLayout tabLayout;
    ViewPager viewPager;

    private ExpenseFragment expenseFragment;
    private ExpenseFragment expenseFragment1;
    private BoFragment boFragment;


    private int current;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_workorder;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTextView = (TextView) findViewById(R.id.title_text);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        searchText = (ImageView) findViewById(R.id.search_imageView_id);
        searchText.setVisibility(View.VISIBLE);
        initToolbar();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        // 设置ViewPager的数据等
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//适合很多tab
        tabLayout.setTabMode(TabLayout.MODE_FIXED);//tab均分,适合少的tab
        searchText.setOnClickListener(searchTextOnClickListener);
    }


    private void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            titleTextView.setText("FiAM > " + getString(R.string.bx_text));
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


        expenseFragment = new ExpenseFragment();
        Bundle data = new Bundle();
        data.putInt("id", 0);
        data.putString("appid", GlobalConfig.EXPENSES_APPID);
        expenseFragment.setArguments(data);
        adapter.addFrag(expenseFragment, getString(R.string.expense_text));

        expenseFragment1 = new ExpenseFragment();
        data = new Bundle();
        data.putInt("id", 1);
        data.putString("appid", GlobalConfig.EXPENSE_APPID);
        expenseFragment1.setArguments(data);
        adapter.addFrag(expenseFragment1, getString(R.string.byjbx_text));

        boFragment = new BoFragment();
        data = new Bundle();
        data.putInt("id", 2);
        boFragment.setArguments(data);
        adapter.addFrag(boFragment, getString(R.string.jkd_text));


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
            if (current == 0) { //差旅报销单
                intent.setClass(BxActivity.this, Expense_SearchActivity.class);
                intent.putExtra("appid", GlobalConfig.EXPENSES_APPID);
            } else if (current == 1) { //备用金报销
                intent.setClass(BxActivity.this, Expense_SearchActivity.class);
                intent.putExtra("appid", GlobalConfig.EXPENSE_APPID);
            } else if (current == 2) { //借款单
                intent.setClass(BxActivity.this, Bo_SearchActivity.class);
                intent.putExtra("appid", GlobalConfig.BO_APPID);
            }


            startActivityForResult(intent, 0);
        }
    };
}
