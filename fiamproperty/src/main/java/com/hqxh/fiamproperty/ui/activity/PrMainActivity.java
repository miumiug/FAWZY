package com.hqxh.fiamproperty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.base.BaseActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PrMainActivity extends BaseActivity {

    Toolbar mToolbar;
    /**
     * 标题
     **/
    TextView titleTextView;

    /**
     * GridView
     **/
    GridView gridView;

    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = null;
    private String[] iconName = null;


    @Override
    protected int getContentViewLayoutID() {

        return R.layout.activity_home;

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTextView = (TextView) findViewById(R.id.title_text);
        gridView = (GridView) findViewById(R.id.gridview_id);

        initToolbar();

        isShowPage();

        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        getData();
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.item_grid_view, from, to);
        //配置适配器
        gridView.setAdapter(sim_adapter);
        gridView.setOnItemClickListener(gridViewOnItemClickListener);
    }


    private void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            titleTextView.setText("FiAM > " + getString(R.string.cgsq_text));
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


    //判断需要显示的页面
    private void isShowPage() {

        icon = new int[]{R.drawable.ic_jscgsq, R.drawable.ic_szcgsq,
                R.drawable.ic_wzcgsq, R.drawable.ic_fwcgsq, R.drawable.ic_wpcgsq};
        iconName = getResources().getStringArray(R.array.po_text);

    }

    //设置数据
    public List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }


    private AdapterView.OnItemClickListener gridViewOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = null;
            switch (i) {
                case 0:  //技术采购申请
                    intent = new Intent(PrMainActivity.this, PrActivity.class);
                    intent.putExtra("titleRec", R.string.jscgsq_text);
                    intent.putExtra("appid", GlobalConfig.JSPR_APPID);
                    startActivityForResult(intent, 0);
                    break;
                case 1:  //试制采购申请
                    intent = new Intent(PrMainActivity.this, PrActivity.class);
                    intent.putExtra("titleRec", R.string.szcgsq_text);
                    intent.putExtra("appid", GlobalConfig.SZPR_APPID);
                    startActivityForResult(intent, 0);
                    break;
                case 2:  //物资采购申请
                    intent = new Intent(PrMainActivity.this, PrActivity.class);
                    intent.putExtra("titleRec", R.string.wzcgsq_text);
                    intent.putExtra("appid", GlobalConfig.PR_APPID);
                    startActivityForResult(intent, 0);
                    break;
                case 3:  //服务采购申请
                    intent = new Intent(PrMainActivity.this, PrActivity.class);
                    intent.putExtra("titleRec", R.string.fwcgsq_text);
                    intent.putExtra("appid", GlobalConfig.FWPR_APPID);
                    startActivityForResult(intent, 0);
                    break;
                case 4:  //外培采购申请
                    intent = new Intent(PrMainActivity.this, PrActivity.class);
                    intent.putExtra("titleRec", R.string.wpcgsq_text);
                    intent.putExtra("appid", GlobalConfig.WPPR_APPID);
                    startActivityForResult(intent, 0);
                    break;

            }

        }
    };
}
