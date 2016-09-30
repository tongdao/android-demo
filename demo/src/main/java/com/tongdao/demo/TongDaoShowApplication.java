package com.tongdao.demo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.tongdao.sdk.TongDao;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

import static com.tongdao.demo.DataTool.APP_ID;
import static com.tongdao.demo.DataTool.PUSH_APP_KEY;

public class TongDaoShowApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TongDao.registerApplication(this);
        // 注册push服务，注册成功后会向DemoMessageReceiver发送广播
        // 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, PUSH_APP_KEY);
        }
    }

    /**
     * From Xiaomi Push example: check whether Xiaomi push should init by checking whether this
     * application is currently running
     * @return
     */
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

}
