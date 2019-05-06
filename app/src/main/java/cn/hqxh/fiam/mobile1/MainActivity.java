package cn.hqxh.fiam.mobile1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_PERSONS;
import com.hqxh.fiamproperty.ui.activity.HomeActivity;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import org.json.JSONObject;
import cn.hqxh.fiam.mobile1.model.Login;
import hqxh.worklogproperty.model.LOG_PERSONS;
import hqxh.worklogproperty.ui.activity.LogMainActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Toolbar mToolbar;
    /**
     * 标题
     **/
    TextView titleTextView;

    /**
     * FIAM
     **/
    private TextView FiamText;
    /**
     * 工作日志
     **/
    private TextView gzrzText;
    /**
     * 任务清版
     **/
    private TextView rwqbText;

    private ImageView backTextView; //标题

    private String username;
    Bundle ming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        username = getIntent().getExtras().getString("username");
        mToolbar = (Toolbar) findViewById(com.hqxh.fiamproperty.R.id.toolbar);
        titleTextView = (TextView) findViewById(com.hqxh.fiamproperty.R.id.title_text);
        initToolbar();

        FiamText = (TextView) findViewById(R.id.fiam_text_id);
        gzrzText = (TextView) findViewById(R.id.zzrz_text_id);
        rwqbText = (TextView) findViewById(R.id.rwqb_text_id);
        backTextView = (ImageView) findViewById(R.id.title_back_id);
        FiamText.setOnClickListener(onClickListener);
        gzrzText.setOnClickListener(onClickListener);
        backTextView.setOnClickListener(onBackClickListener);

        //接收到的单点信息
//        Bundle bundle= getIntent().getExtras();
//        if(bundle!=null){
//            String invokeParameter= bundle.getString("invokeParameter");
//            Login  user = JSON.parseObject(invokeParameter,Login.class);
//            if(!(user==null||user.equals(""))){
//                username=user.getName();
//            }
//        }
    }

    private void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            titleTextView.setText("FiAM");
            setSupportActionBar(mToolbar);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;
            int i = view.getId();
            if (i == R.id.fiam_text_id) { //跳转至FIAM系统
//                LoginFIMA();
                intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivityForResult(intent, 0);
            } else if (i == R.id.zzrz_text_id) {//跳转至工作日志系统
//                LoginLog();
                intent = new Intent(MainActivity.this, LogMainActivity.class);
                startActivityForResult(intent, 0);
            }
        }
    };

    //登录FIAM
    private void LoginFIMA() {
        String imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                .getDeviceId();
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_LOGIN)
                .addBodyParameter("username", username)
                .addBodyParameter("imei", imei)
                .build()
                .getObjectObservable(R_PERSONS.class) // 发起获取数据列表的请求，并解析到R_Person.class
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_PERSONS>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull R_PERSONS r_PERSON) throws Exception {
                        if (r_PERSON.getErrcode().equals(GlobalConfig.LOGINSUCCESS)) {//登录成功

                        } else if (r_PERSON.getErrcode().equals(GlobalConfig.CHANGEIMEI)) {//登录成功,检测到用户更换手机登录

                        } else if (r_PERSON.getErrcode().equals(GlobalConfig.USERNAMEERROR)) {//用户名密码错误
                            return;
                        } else {
                            return;
                        }
                    }
                })

                .map(new Function<R_PERSONS, String>() {
                    @Override
                    public String apply(@io.reactivex.annotations.NonNull R_PERSONS r_PERSON) throws Exception {
                        return r_PERSON.getResult();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull String persion) throws Exception {
                        Log.e("TAG", "persion" + persion);
                        if (null != persion) {
                            JSONObject object = new JSONObject(persion);
                            R_PERSONS.PERSION persion1 = new R_PERSONS.PERSION();
                            persion1.setDEFSITE(object.getString("DEFSITE"));
                            persion1.setDISPLAYNAME(object.getString("DISPLAYNAME"));
                            persion1.setEMAILADDRESS(object.getString("EMAILADDRESS"));
                            persion1.setMYAPPS(object.getString("MYAPPS"));
                            persion1.setPERSONID(object.getString("PERSONID"));
                            AccountUtils.setLoginDetails(MainActivity.this, persion1);
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivityForResult(intent, 0);
                        }


                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
//                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }
                });


    }


    //登录工作日志
    private void LoginLog() {
        Log.e(TAG, "1111");
        String imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                .getDeviceId();
        Log.e(TAG, "url=" + hqxh.worklogproperty.constant.GlobalConfig.HTTP_URL_LOGIN);
        Log.e(TAG, "username=" + username);
        Rx2AndroidNetworking.post(hqxh.worklogproperty.constant.GlobalConfig.HTTP_URL_LOGIN)
                .addBodyParameter("username", username)
                .addBodyParameter("imei", imei)
                .build()
                .getObjectObservable(LOG_PERSONS.class) // 发起获取数据列表的请求，并解析到R_Person.class
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<LOG_PERSONS>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull LOG_PERSONS log_persons) throws Exception {
                        if (log_persons.getErrcode().equals(GlobalConfig.LOGINSUCCESS)) {//登录成功

                        } else if (log_persons.getErrcode().equals(GlobalConfig.CHANGEIMEI)) {//登录成功,检测到用户更换手机登录

                        } else if (log_persons.getErrcode().equals(GlobalConfig.USERNAMEERROR)) {//用户名密码错误
                            return;
                        } else {
                            return;
                        }
                    }
                })

                .map(new Function<LOG_PERSONS, String>() {
                    @Override
                    public String apply(@io.reactivex.annotations.NonNull LOG_PERSONS r_PERSON) throws Exception {
                        return r_PERSON.getResult();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull String persion) throws Exception {
                        Log.e("TAG", "persion" + persion);
                        if (null != persion) {
                            JSONObject object = new JSONObject(persion);
                            LOG_PERSONS.PERSION persion1 = new LOG_PERSONS.PERSION();
                            persion1.setDEFSITE(object.getString("DEFSITE"));
                            persion1.setDISPLAYNAME(object.getString("DISPLAYNAME"));
                            persion1.setEMAILADDRESS(object.getString("EMAILADDRESS"));
                            persion1.setMYAPPS(object.getString("MYAPPS"));
                            persion1.setPERSONID(object.getString("PERSONID"));
                            hqxh.worklogproperty.until.AccountUtils.setLoginDetails(MainActivity.this, persion1);
                            Intent intent = new Intent(MainActivity.this, LogMainActivity.class);
                            startActivityForResult(intent, 0);
                        }

                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //跳转至工作日志界面
    private void loginWorkLog() {
        Intent intent = new Intent(MainActivity.this, LogMainActivity.class);
        startActivityForResult(intent, 0);
    }

    private View.OnClickListener onBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}
