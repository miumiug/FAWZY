package com.hqxh.fiamproperty.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hqxh.fiamproperty.R;


/**
 * Activity标题基类
 * <p>
 * Author: tc
 * Email: 278233503@qq.com
 * Date: 2017-07-24  14:21
 */

public abstract class BaseTitleActivity extends BaseActivity {
    private static final String TAG = "BaseTitleActivity";
    Toolbar mToolbar;
    private TextView mTitleName;

    public boolean isShowBack = true;
    public ImageView save_imageView_id;
    /**
     * 设置标题文本
     */
    protected abstract String getSubTitle();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleName = (TextView) findViewById(R.id.title_text);
        save_imageView_id = (ImageView) findViewById(R.id.save_imageView_id);

        if (getContentViewLayoutID() != 0) {
            initToolbar();
        }


    }


    private void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            mTitleName.setText(getSubTitle());
            if (isShowBack(isShowBack)) {
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


    protected boolean isShowBack(boolean showback) {
        return showback;
    }


    /**
     * 获取View的高度
     **/
    public static int getHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();

        return height;
    }

}
