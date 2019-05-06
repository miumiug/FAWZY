package com.hqxh.fiamproperty.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.base.BaseActivity;
import com.hqxh.fiamproperty.ui.fragment.CofirmReallyFragment;
import com.hqxh.fiamproperty.ui.fragment.MyReallyFragment;
import com.hqxh.fiamproperty.ui.fragment.TimeFragment;
import com.hqxh.fiamproperty.ui.fragment.WaitReallyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\6\15 0015.
 */

public class XmxsActivity extends BaseActivity {
    Toolbar mToolbar;
    /**
     * 标题
     **/
    TextView titleTextView;
    TabLayout tabLayout;
    ViewPager viewPager;

    private MyReallyFragment myReallyFragment;
    private WaitReallyFragment waitReallyFragment;
    private CofirmReallyFragment cofirmReallyFragment;
    private TimeFragment timeFragment;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_xmxs;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTextView = (TextView) findViewById(R.id.title_text);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        initToolbar();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        // 设置ViewPager的数据等
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//适合很多tab
//        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);//tab均分,适合少的tab
    }
    private void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            titleTextView.setText("FiAM > " + getString(R.string.xmxs_text));
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
                SharedPreferences userInfo = getSharedPreferences("user_info", 0);
                userInfo.edit().clear().commit();
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


        myReallyFragment = new MyReallyFragment();
        Bundle data = new Bundle();
        data.putInt("id", 0);
        myReallyFragment.setArguments(data);
        adapter.addFrag(myReallyFragment, "我的写实");

        waitReallyFragment = new WaitReallyFragment();
        data = new Bundle();
        data.putInt("id", 1);
        waitReallyFragment.setArguments(data);
        adapter.addFrag(waitReallyFragment, "待确认写实");

        cofirmReallyFragment = new CofirmReallyFragment();
        data = new Bundle();
        data.putInt("id", 2);
        cofirmReallyFragment.setArguments(data);
        adapter.addFrag(cofirmReallyFragment, "已确认写实");

//        timeFragment = new TimeFragment();
//        data = new Bundle();
//        data.putInt("id", 3);
//        timeFragment.setArguments(data);
//        adapter.addFrag(timeFragment, "工时统计　");


        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        SharedPreferences userInfo = getSharedPreferences("user_info", 0);
        userInfo.edit().clear().commit();
        finish();
        return super.onKeyDown(keyCode, event);

    }
}
