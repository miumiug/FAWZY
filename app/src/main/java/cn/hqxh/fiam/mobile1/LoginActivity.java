package cn.hqxh.fiam.mobile1;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_PERSONS;
import com.hqxh.fiamproperty.ui.activity.HomeActivity;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;


import cn.hqxh.fiam.mobile1.utils.CommomDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0; // 请求码

    private AutoCompleteTextView mEmailView;

    private EditText mPasswordView;
    private static String data;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    CheckBox checkBox;

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private PermissionsChecker mPermissionsChecker; // 权限检测器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermissionsChecker = new PermissionsChecker(this);
        setContentView(R.layout.activity_login);

//        //接收到的单点信息
//        data = QMAppGroup.getInfoWhitIntent(this, getIntent());
//        TextView info_text = (TextView) findViewById(R.id.email);
//        if (data == null) {
//            info_text.setText("未接收到单点登录的信息");
//        }else {
//            info_text.setText(data);
//        }
        initView();
    }


    private void initView() {
        PgyCrashManager.register(this);
        PgyUpdateManager.setIsForced(false); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
//        PgyUpdateManager.register(this);
        PgyUpdateManager.register(LoginActivity.this,
                new UpdateManagerListener() {

                    @Override
                    public void onUpdateAvailable(final String result) {
                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        new CommomDialog(LoginActivity.this, R.style.dialog, appBean.getReleaseNote(), new CommomDialog.OnCloseListener() {

                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if(confirm) {
                                    startDownloadTask(
                                            LoginActivity.this,
                                            appBean.getDownloadURL());
                                }else {
                                }
                            }
                        });
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                    }
                });

        sharedPreferences = getSharedPreferences("appSetting", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String isFirstView = sharedPreferences.getString("username", "");
        String passWord = sharedPreferences.getString("password", "");


        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        checkBox = (CheckBox) findViewById(R.id.check);

        mEmailView.setText(isFirstView);
        mPasswordView.setText(passWord);
        if (null == passWord || "".equals(passWord.trim())) {
            checkBox.setChecked(false);
        } else {
            checkBox.setChecked(true);
        }

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Login();
               /* Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("username", mEmailView.getText().toString());
                startActivityForResult(intent, 0);*/
            }
        });

    }



    private void Login() {
        String imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();

        //----XIA  20191106  for Testing
        if (imei == null || imei.equals(""))
            imei = "00000";

        Log.e("TAG", "imei=" + imei + ",usename=" + mEmailView.getText().toString());
        Log.e("TAG", "HTTP_URL_LOGIN=" + GlobalConfig.HTTP_URL_LOGIN1);
        if(null == mEmailView.getText() || mEmailView.getText().toString().trim().equals("")) {
            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        if(null == mPasswordView.getText() || mPasswordView.getText().toString().trim().equals("")) {
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_LOGIN1)
                .addQueryParameter("username", mEmailView.getText().toString())
                .addQueryParameter("password", mPasswordView.getText().toString())
                .addQueryParameter("imei", imei)
                .build()
                .getObjectObservable(R_PERSONS.class) // 发起获取数据列表的请求，并解析到R_Person.class
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_PERSONS>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull R_PERSONS r_PERSON) throws Exception {
                        Toast.makeText(LoginActivity.this, r_PERSON.getErrmsg(), Toast.LENGTH_SHORT).show();
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
                    public void accept(@io.reactivex.annotations.NonNull String person) throws Exception {
                        Log.e("TAG", "person" + person);
                        if (null != person) {
                            editor.putString("password", "");
                            if (checkBox.isChecked()) {
//                           记忆密码
                                editor.putString("password", mPasswordView.getText().toString());
                            }
                            editor.putString("username", mEmailView.getText().toString());
                            editor.commit();

                            JSONObject object = new JSONObject(person);
                            R_PERSONS.PERSION persion1 = new R_PERSONS.PERSION();
                            persion1.setDEFSITE(object.getString("DEFSITE"));
                            persion1.setDISPLAYNAME(object.getString("DISPLAYNAME"));
                            persion1.setEMAILADDRESS(object.getString("EMAILADDRESS"));
                            persion1.setMYAPPS(object.getString("MYAPPS"));
                            persion1.setPERSONID(object.getString("PERSONID"));
                            AccountUtils.setLoginDetails(LoginActivity.this, persion1);
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("username", mEmailView.getText().toString());
                            startActivityForResult(intent, 0);
                            finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        Log.e("TAG", "throwable =   " + throwable.getMessage());
//                        Toast.makeText(LoginActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();

        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        } else {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Handler x = new Handler();
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }






}
