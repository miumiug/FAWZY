package com.hqxh.fiamproperty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.base.BaseActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_PERSONS;
import com.hqxh.fiamproperty.model.R_PERSONS.PERSION;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.hqxh.fiamproperty.unit.JsonUnit;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class TransitionActivity extends AppCompatActivity {

    private static final String TAG = "TransitionActivity";

    private String identity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
//        identity = JsonUnit.getIdentity(AccountUtils.getPerson(TransitionActivity.this));
        identity = "1000kkk";
        Log.e(TAG, "identity=" + identity);
        if (null == identity) {
            Toast.makeText(this, "无法识别身份", Toast.LENGTH_SHORT).show();
            finish();
        }
        Login();
        super.onCreate(savedInstanceState);



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }


    //连接系统测试
    private void Login() {

        String imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                .getDeviceId();

        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_LOGIN)
                .addQueryParameter("username", identity)
                .addQueryParameter("imei", imei)
                .build()
                .getObjectObservable(R_PERSONS.class) // 发起获取数据列表的请求，并解析到R_Person.class
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_PERSONS>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull R_PERSONS r_PERSON) throws Exception {


                    }
                })

                .map(new Function<R_PERSONS, String>() {
                    @Override
                    public String apply(@io.reactivex.annotations.NonNull R_PERSONS r_PERSON) throws Exception {
                        Log.e(TAG, "Errcode=" + r_PERSON.getErrcode() + "message" + r_PERSON.getErrmsg());
                        if (r_PERSON.getErrcode().equals(GlobalConfig.LOGINSUCCESS)) {//登录成功
                        } else if (r_PERSON.getErrcode().equals(GlobalConfig.CHANGEIMEI)) {//登录成功,检测到用户更换手机登录
                        } else if (r_PERSON.getErrcode().equals(GlobalConfig.USERNAMEERROR)) {//用户名密码错误
                        } else {
                        }
                        return r_PERSON.getResult();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull String persion) throws Exception {
                        Log.e(TAG, "s" + persion);
                        if (null != persion) {
                            PERSION persion1 = new Gson().fromJson(persion, PERSION.class);
                            AccountUtils.setLoginDetails(TransitionActivity.this, persion1);
                            Log.e(TAG, "username=" + persion1.getPERSONID());
                            Log.e(TAG, "usernames=" + AccountUtils.getpersonId(TransitionActivity.this));
                            Intent intent = new Intent(TransitionActivity.this, HomeActivity.class);
                            startActivityForResult(intent, 0);
                        } else {
                            finish();
                        }


                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        Toast.makeText(TransitionActivity.this, getString(R.string.unable_to_connect_to_server_login_failed), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });


    }
}
