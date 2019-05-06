package cn.hqxh.fiam.mobile1;

import android.support.multidex.MultiDexApplication;

import com.pgyersdk.crash.PgyCrashManager;

/**
 * Created by wxs on 2017/11/10.
 */
public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this);
    }
}
