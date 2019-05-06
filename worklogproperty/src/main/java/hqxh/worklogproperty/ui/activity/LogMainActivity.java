package hqxh.worklogproperty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hqxh.worklogproperty.R;
import hqxh.worklogproperty.base.BaseActivity;
import hqxh.worklogproperty.constant.GlobalConfig;
import hqxh.worklogproperty.model.R_PERSONS.PERSON;
import hqxh.worklogproperty.ui.fragment.MyWorkdailyFragment;
import hqxh.worklogproperty.until.AccountUtils;
import hqxh.worklogproperty.until.JsonUnit;

public class LogMainActivity extends BaseActivity {
    private final String TAG = "LogMainActivity";

    Toolbar mToolbar;

    private TextView displaynemeText; //登录人

    private TextView qrrText; //工作确认人

    private String confirmby;


    TabLayout tabLayout;
    ViewPager viewPager;

    private MyWorkdailyFragment myWorkdailyFragment1;
    private MyWorkdailyFragment myWorkdailyFragment2;
    private MyWorkdailyFragment myWorkdailyFragment3;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.log_activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar();
        displaynemeText = (TextView) findViewById(R.id.displayname_text_id);
        qrrText = (TextView) findViewById(R.id.qrr_text_id);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        // 设置ViewPager的数据等
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//适合很多tab
        tabLayout.setTabMode(TabLayout.MODE_FIXED);//tab均分,适合少的tab

        displaynemeText.setText(AccountUtils.getdisplayName(LogMainActivity.this));
//        qrrText.setText("付东红");
        qrrText.setOnClickListener(qrrTextOnClickListener);
    }


    private void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);

        }
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


        myWorkdailyFragment1 = new MyWorkdailyFragment();
        Bundle data = new Bundle();
        data.putInt("id", 0);
        data.getString("confirmby", "付东红");
        myWorkdailyFragment1.setArguments(data);
        adapter.addFrag(myWorkdailyFragment1, "我的日志");

        myWorkdailyFragment2 = new MyWorkdailyFragment();
        data = new Bundle();
        data.putInt("id", 1);
        myWorkdailyFragment2.setArguments(data);
        adapter.addFrag(myWorkdailyFragment2, "待确认日志");

        myWorkdailyFragment3 = new MyWorkdailyFragment();
        data = new Bundle();
        data.putInt("id", 2);
        myWorkdailyFragment3.setArguments(data);
        adapter.addFrag(myWorkdailyFragment3, "工时统计");


        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(3);
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


    /**
     * 选择工作确认人
     **/
    private View.OnClickListener qrrTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LogMainActivity.this, PersonActivity.class);
            startActivityForResult(intent, GlobalConfig.PERSON_REQUESTCODE);
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GlobalConfig.PERSON_REQUESTCODE:
                if (resultCode == GlobalConfig.PERSON_RESULTCODE) { //人员选择
                    PERSON person = (PERSON) data.getSerializableExtra("person");
                    qrrText.setText(JsonUnit.convertStrToArray(person.getDISPLAYNAME())[0]);
                    confirmby = JsonUnit.convertStrToArray(person.getPERSONID())[0];

                }
        }

    }
}
