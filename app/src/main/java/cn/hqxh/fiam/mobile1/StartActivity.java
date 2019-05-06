package cn.hqxh.fiam.mobile1;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import com.hqxh.fiamproperty.ui.activity.HomeActivity;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import cn.hqxh.fiam.mobile1.utils.CommomDialog;

public class StartActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 100; // 请求码
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private String paramFromPortal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermissionsChecker = new PermissionsChecker(this);
        setContentView(R.layout.activity_start);

        //接收到的单点信息 from Portal  20181108
        Bundle bundle= getIntent().getExtras();
        if(bundle!=null){
            paramFromPortal= bundle.getString("invokeParameter");
        }

        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
            update();
        } else {
            update();
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
        }else {
            Intent intent=new Intent(this, HomeActivity.class);
            intent.putExtra("invokeParameter", paramFromPortal);
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
            finish();
        }
    }

    private void update() {
        PgyCrashManager.register(this);
        PgyUpdateManager.setIsForced(false); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.register(this,
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        new CommomDialog(StartActivity.this, R.style.dialog, appBean.getReleaseNote(), new CommomDialog.OnCloseListener() {

                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if(confirm) {
                                    startDownloadTask(
                                            StartActivity.this,
                                            appBean.getDownloadURL());
                                }else {
                                }
                                //start HomeActivity
                                Intent intent=new Intent(StartActivity.this, HomeActivity.class);
                                intent.putExtra("invokeParameter", paramFromPortal);
                                Bundle bundle = intent.getExtras();
                                if (bundle != null) {
                                    intent.putExtras(bundle);
                                }
                                startActivity(intent);
                                finish();
                            }
                        }).show();
                    }
                    @Override
                    public void onNoUpdateAvailable() {
                        //start HomeActivity
                        Intent intent=new Intent(StartActivity.this, HomeActivity.class);
                        intent.putExtra("invokeParameter", paramFromPortal);
                        Bundle bundle = intent.getExtras();
                        if (bundle != null) {
                            intent.putExtras(bundle);
                        }
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        PgyUpdateManager.unregister();
    }
}
