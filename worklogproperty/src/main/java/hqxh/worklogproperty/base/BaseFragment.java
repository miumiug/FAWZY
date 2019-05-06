package hqxh.worklogproperty.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import hqxh.worklogproperty.R;
import hqxh.worklogproperty.widget.LoadingDialog;

/**
 * Fragment基类
 **/

public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    protected Activity mActivity;
    protected View mView;

    private LoadingDialog loadingDialog;

    public String confirmby; //工作确认人

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mActivity = getActivity();
        mView = initContentView(inflater, container);
        if (mView == null)
            throw new NullPointerException("Fragment content view is null.");
        bindView();
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Log.e(TAG, "confirmby=" + savedInstanceState.getString("confirmby"));
//        confirmby = savedInstanceState.getString("confirmby");
        initData();
    }

    @Nullable
    protected abstract View initContentView(LayoutInflater inflater, @Nullable ViewGroup container);

    @Override
    public void onResume() {
        super.onResume();
        bindData();
    }

    protected abstract void bindView();

    /**
     * 请求动态数据
     */
    protected void initData() {

    }

    /**
     * 绑定静态数据
     */
    protected void bindData() {

    }

    protected <VT extends View> VT searchViewById(int id) {
        if (mView == null)
            throw new NullPointerException("Fragment content view is null.");
        VT view = (VT) mView.findViewById(id);
        if (view == null)
            throw new NullPointerException("This resource id is invalid.");
        return view;
    }


    //
    public void showLoadingDialog(String msg) {
        loadingDialog = new LoadingDialog(getActivity(), msg, R.style.loading_dialog);
        loadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
    }


    public static void showMiddleToast(Context cxt, String msg) {
        Toast toast = Toast.makeText(cxt, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
